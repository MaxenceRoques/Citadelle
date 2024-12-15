package joueurs;

import personnages.*;
import carte.*;

import java.util.ArrayList;
import java.util.List;

public class Bot{
    private Joueur joueur;
    private String messageRound ="";
    private ArrayList<Integer> ciblesDispo;
    private static final int CITE_TERMINEE = 7;

    private static final int TYPE_ACTION_ACTION_UNIQUE = 0;
    private static final int TYPE_ACTION_PIECE_SUIVANT_TYPE_QUARTIER = 0;
    private static final int TYPE_ACTION_ATTAQUE_CONDOTTIERE = 1;
    private static final int TYPE_ACTION_UNE_PIECE_MARCHAND = 1;
    private static final int TYPE_ACTION_MAGICIEN_AVEC_JOUEUR = 0;
    private static final int TYPE_ACTION_MAGICIEN_AVEC_PIOCHE = 1;
    private static final String ARCHITECTE = "Architecte";
    private static final int PERSONNAGE_ASSASSIN = 0;
    private static final int PERSONNAGE_VOLEUR = 1;
    private static final int PERSONNAGE_MAGICIEN = 2;
    private static final int PERSONNAGE_ROI = 3;
    private static final int PERSONNAGE_EVEQUE = 4;
    private static final int PERSONNAGE_MARCHAND = 5;
    private static final int PERSONNAGE_ARCHITECTE = 6;
    private static final int PERSONNAGE_CONDOTTIERE = 7;

    public Bot(int argent, String nom){
        this.joueur = new Joueur(argent,nom);
    }

    public Joueur getJoueur(){return joueur;}

    public Personnage getRole(){
        return joueur.getPersonnage();
    }

    public int getTailleCite(){
        return joueur.getCiteJoueur().size();
    }

    public ArrayList<Batiment> getMain(){
        return joueur.getMainJoueur();
    }

    public List<Batiment> getCite() {
        return joueur.getCiteJoueur();
    }

    public String getMessageRound(){ return this.messageRound; }

    public int getArgent(){
        return joueur.getArgent();
    }

    public String getNom(){
        return joueur.getNom();
    }

    public void addMessage(String message) {
        this.messageRound += message + "\n";
    }

    public void setPersonnage(int numero){ // On change le personnage du joueur
        this.joueur.setPersonnage(numero);
    }

    public void setCiblesDispo(ArrayList<Integer> liste) {
        this.ciblesDispo = liste;
    }

    public boolean estPersonnage(String nom){
        return this.getJoueur().estPersonnage(nom);
    }

    // retire le personnage du joueur et le renvoie
    public Personnage retirePersonnage(){
        Personnage personnage = this.getRole();
        this.setPersonnage(0);
        return personnage;
    }

    public void recoisBatiment(Batiment batiment){
        this.joueur.piocherBatiment(batiment);
    }

    //--// ----------------------------- // --- ---------------------------------- --- // ----------------------------- //--//
    //--// ----------------------------- // --- début méthodes pour jouer son tour --- // ----------------------------- //--//
    //--// ----------------------------- // --- ---------------------------------- --- // ----------------------------- //--//

    public void jouerVol(Bot[] adversaires){
        for (Bot bot:adversaires){
            if(bot.estPersonnage("Voleur")){
                bot.getJoueur().piocherArgent(this.getArgent());
                this.joueur.retireArgent(this.getArgent());
            }
        }
        this.joueur.resetRobbed();
    }

    // action de jouer la merveille Forge
    // Si le joueur est riche, a peu de cartes, et possède la forge dans sa cité il l'utilise
    public void jouerForge(Deck deck){
        if (joueur.citeContains("Forge") && joueur.getMainJoueur().size() <= 2 && joueur.getArgent() >= 6) {
            joueur.retireArgent(2);
            for (int i=0; i<3; i++) {joueur.piocherBatiment(deck.pioche());}
            addMessage("Le joueur a pioché trois bâtiments en dépensant 2PO.");
        }
    }

    // action de jouer la merveille Laboratoire
    // Si le joueur a beaucoup de cartes et possède le laboratoire dans sa cité il le joue automatiquement
    public void jouerLaboratoire(Deck deck){
        if (joueur.citeContains("Laboratoire") && getMain().size() >= 4) {
            joueur.piocherArgent(2); // Il gagne 2 pièces d'or
            boolean carteRetiree = false;
            ArrayList<Batiment> main = joueur.getMainJoueur();//On récupère la main
            for(int indexCarte=0;indexCarte< main.size();indexCarte++){
                if(this.joueur.citeContains(main.get(indexCarte).getNom())){
                    deck.jette(joueur.getMainJoueur().get(indexCarte));
                    joueur.getMainJoueur().remove(indexCarte); // Et jette de sa main une carte qu'il a déjà construit en priorité
                    carteRetiree = true;
                    break;
                }
            }
            if(!carteRetiree){
                deck.jette(joueur.getCiteJoueur().get(0));
                joueur.getMainJoueur().remove(0); // Et jette de sa main un bâtiment random sinon
            }
            addMessage("Le joueur a détruit une carte de sa main pour récuperer 2PO.");
        }
    }

    public void jouerPiocherCarte(Deck deck){
        //Si la main possède trop peu de carte, on en pioche sauf si on est architecte ou magicien
        ArrayList<Batiment> choixCarteBatiment = new ArrayList<>();
        boolean observatoire = false;
        choixCarteBatiment.add(deck.pioche());
        choixCarteBatiment.add(deck.pioche());
        addMessage(joueur.messagePiocheBatiment());
        if(joueur.citeContains("Observatoire")){ // on pioche une troisième carte

            choixCarteBatiment.add(deck.pioche());
            observatoire = true;
            addMessage("Le joueur a utilise l'Observatoire et pioche un troisième choix.");
        }
        // Si la cité du joueur possède la bibliothèque, on garde les 2 ou 3 cartes piochées
        if (joueur.citeContains("Bibliotheque")) {
            joueur.piocherBatiment(choixCarteBatiment.get(0));
            joueur.piocherBatiment(choixCarteBatiment.get(1));
            if(observatoire){
                joueur.piocherBatiment(choixCarteBatiment.get(2));
                addMessage("Le joueur a pioché trois bâtiments grâce à la bibliothèque.");
            }
            else{
                addMessage("Le joueur a pioché deux bâtiments grâce à la bibliothèque.");
            }
        }

        else { // Choix des cartes

            int choix = 0;
            int coutChoix = 0;
            for(int i=0;i<choixCarteBatiment.size();i++){ // on prend la carte que l'on peut construire qui est la plus chère
                if(!joueur.citeContains(choixCarteBatiment.get(i).getNom()) && choixCarteBatiment.get(i).getCout()>coutChoix){
                    choix = i;
                    coutChoix = choixCarteBatiment.get(i).getCout();
                }
            }
            joueur.piocherBatiment(choixCarteBatiment.get(choix));
            choixCarteBatiment.remove(choix);
            deck.jette(choixCarteBatiment.get(0));
            if(observatoire){ // si on a l'observatoire on jette deux cartes
                deck.jette(choixCarteBatiment.get(1));
            }
        }
    }
    //--// ----------------------------- // --- -------------------------------- --- // ----------------------------- //--//
    //--// ----------------------------- // --- fin méthodes pour jouer son tour --- // ----------------------------- //--//
    //--// ----------------------------- // --- -------------------------------- --- // ----------------------------- //--//

    public void jouerSonTour(Bot[] adversaires,Deck deck){
        this.messageRound = "";
        if(joueur.getKilled()){
            addMessage(joueur.getPersonnage().getNom()+" a été tué");joueur.resetKilled();
            return;
        }
        if(joueur.getRobbed()){
            jouerVol(adversaires);
            addMessage(this.getRole() +" a été volé");
        }
        addMessage("("+this.getNom()+")"+this.joueur.affichageJoueur()); //On affiche le joueur et ses informations
        //--// ----------------------------- cartes Merveilles : forge + laboratoire ----------------------------- //--//
        jouerForge(deck);
        jouerLaboratoire(deck);

        //--// ----------------------------- Choix piocher des cartes ----------------------------- //--//
        if (joueur.getMainJoueur().size() <= 3 && !this.estPersonnage(ARCHITECTE) && !this.estPersonnage("Magicien")){
            jouerPiocherCarte(deck);
        }
        //--// ----------------------------- Choix piocher deux or ----------------------------- //--//
        else{
            joueur.piocherArgent(2);
            addMessage(joueur.messagePiocheArgent());
        }

        //--// ----------------------------- Actions de jeu ----------------------------- //--//

        Batiment aConstruire = null; // enregistrement de la carte construite pour le message de construction
        int indiceCible = -1;

        // on regarde quand jouer son action de rôle
        switch (this.getRole()+""){
            case "Assassin","Voleur" :
                indiceCible = jouerAssassinVoleur(adversaires);
                aConstruire = jouerBatimentAConstruire(null);
                break;
            case "Magicien" :
                indiceCible = jouerMagicien(adversaires, deck);
                aConstruire = jouerBatimentAConstruire(null);
                break;
            case ARCHITECTE :
                jouerArchitecte(deck);
                break;
            default: // roi ou évêque ou marchand ou condottiere

                // si on est marchand on récupère automatique sa pièce
                if(this.estPersonnage("Marchand")){
                    this.joueur.jouerAction(joueur,null,null,null,TYPE_ACTION_UNE_PIECE_MARCHAND);
                }

                // si on est condottiere on détermine la cible potentielle de l'attaque
                indiceCible = choixCibleCondottiere(adversaires);

                // si on a au moins un batîment constructible (peut importe son coût)
                aConstruire = jouerPersoQuartier(aConstruire);

                // attaque condottiere à la fin de son tour
                if(indiceCible != -1){
                    this.joueur.jouerAction(joueur,adversaires[indiceCible].getJoueur(),deck,null,TYPE_ACTION_ATTAQUE_CONDOTTIERE);
                }
                break;
        }
        jouerMessageDuTour(indiceCible,aConstruire,adversaires);
        addMessage(joueur.affichageJoueur()); //On affiche le joueur et ses informations
    }

    //--// ----------------------------- // --- ------------------------------------------ --- // ----------------------------- //--//
    //--// ----------------------------- // --- début méthodes pour le jeu suivant le rôle --- // ----------------------------- //--//
    //--// ----------------------------- // --- ------------------------------------------ --- // ----------------------------- //--//

    public int jouerAssassinVoleur(Bot[] adversaires){
        int indiceCible = -1;
        int numeroPersoCible = this.choixCible(adversaires);
        for(int indexAdversaire=0;indexAdversaire<adversaires.length;indexAdversaire++){
            if (adversaires[indexAdversaire].getRole().getNumero()==numeroPersoCible){
                this.joueur.jouerAction(joueur,adversaires[indexAdversaire].getJoueur(),null,null,TYPE_ACTION_ACTION_UNIQUE);
                indiceCible=indexAdversaire;
                break;
            }
        }
        return indiceCible;
    }

    public int jouerMagicien(Bot[] adversaires, Deck deck){
        int maxCarte = this.getMain().size();
        int indiceCible = -1 ;
        for(int indexAdversaire =0;indexAdversaire< adversaires.length;indexAdversaire++){
            if(adversaires[indexAdversaire].getMain().size() > maxCarte){
                maxCarte = adversaires[indexAdversaire].getMain().size();
                indiceCible = indexAdversaire;
            }
        }
        if(indiceCible!=-1){
            this.joueur.jouerAction(joueur,adversaires[indiceCible].getJoueur(),deck,null,TYPE_ACTION_MAGICIEN_AVEC_JOUEUR);
        }
        else {
            int[] carteEchange = new int[joueur.getMainJoueur().size()];
            for(int indexCarte=0;indexCarte<carteEchange.length;indexCarte++){
                if(joueur.citeContains(joueur.getMainJoueur().get(indexCarte).getNom())){
                    carteEchange[indexCarte] = 1;
                }
                else {
                    carteEchange[indexCarte] = 0;
                }
            }
            this.joueur.jouerAction(joueur,null,deck,carteEchange,TYPE_ACTION_MAGICIEN_AVEC_PIOCHE);
        }
        return indiceCible;
    }

    public void jouerArchitecte(Deck deck){
        this.joueur.jouerAction(joueur,null,deck,null,0);
        // si on a au moins un batîment constructible, on le construit (3 fois)
        for(int i=0;i<3;i++){
            if(this.coutMinMaxConstructible(this.getMain(),"-",joueur.getArgent())!=null) {
                Batiment aConstruire = this.coutMinMaxConstructible(this.getMain(), "-", joueur.getArgent());
                addMessage(aConstruire.messageActionCarte());
                construireBatiment(aConstruire);
            }
        }
    }

    public int choixCibleCondottiere(Bot[] adversaires){
        int indiceCible = -1;
        if(this.estPersonnage("Condottiere")){
            int maxBatiment = 0;
            for(int indexAdversaire=0;indexAdversaire<adversaires.length;indexAdversaire++){
                Bot other = adversaires[indexAdversaire];
                // si l'adversaire n'est ni condottiere ni évêque, que sa cité n'est pas finie et qu'il possède le plus de batîments, on le cible
                if(!other.estPersonnage("Condottiere") && (!other.estPersonnage("Eveque") || other.getJoueur().getKilled()) && other.getCite().size()<CITE_TERMINEE && other.getCite().size()>maxBatiment){
                    indiceCible = indexAdversaire;
                    maxBatiment = adversaires[indexAdversaire].getCite().size();
                }
            }
        }
        return indiceCible;
    }

    public Batiment jouerPersoQuartier(Batiment aConstruire){
        if(this.coutMinMaxConstructible(this.getMain(),"+",10)!=null){
            // si on peut le construire, on construit le batîment et on récupère l'argent à la fin du tour
            if(this.coutMinMaxConstructible(this.getMain(),"+",joueur.getArgent())!=null){
                aConstruire = this.coutMinMaxConstructible(this.getMain(), "+", joueur.getArgent());
                construireBatiment(aConstruire);
                this.joueur.jouerAction(joueur,null,null,null,TYPE_ACTION_PIECE_SUIVANT_TYPE_QUARTIER);
            }
            else {
                this.joueur.jouerAction(joueur,null,null,null,TYPE_ACTION_PIECE_SUIVANT_TYPE_QUARTIER);
                if(this.coutMinMaxConstructible(this.getMain(),"+",joueur.getArgent())!=null){
                    aConstruire = this.coutMinMaxConstructible(this.getMain(), "+", joueur.getArgent());
                    construireBatiment(aConstruire);
                }
            }
        }
        return aConstruire;
    }

    public Batiment jouerBatimentAConstruire(Batiment aConstruire){
        if(this.coutMinMaxConstructible(this.getMain(),"+",joueur.getArgent())!=null) {
            aConstruire = this.coutMinMaxConstructible(this.getMain(), "+", joueur.getArgent());
            construireBatiment(aConstruire);
        }
        return aConstruire;
    }

    public void jouerMessageDuTour(int indiceCible,Batiment aConstruire,Bot[] adversaires){
        if(indiceCible!=-1){
            addMessage(this.joueur.messageAction(adversaires[indiceCible].getJoueur()));
        }
        if(aConstruire!=null && !estPersonnage(ARCHITECTE)){
            addMessage(aConstruire.messageActionCarte());
        }
    }

    //--// ----------------------------- // --- ---------------------------------------- --- // ----------------------------- //--//
    //--// ----------------------------- // --- fin méthodes pour le jeu suivant le rôle --- // ----------------------------- //--//
    //--// ----------------------------- // --- ---------------------------------------- --- // ----------------------------- //--//

    public int[] nombreBatimentParQuartier(){
        int[] nombreBatiment = new int[]{0,0,0,0,0};
        // religieux,militaire,noble,commerce,merveille
        for(int i=0;i<this.getTailleCite();i++){
            nombreBatiment[this.getCite().get(i).getType().ordinal()]+=1;
        }
        return nombreBatiment;
    }

    public void construireBatiment(Batiment batiment) {
        this.joueur.construireBatiment(batiment);
    }

    public Batiment coutMinMaxConstructible(List<Batiment> liste,String minOuMax,int maxArgent) { // donne le bâtiments au cout minimum ou maximum de la liste de bâtiments qui n'est pas dans la ville
        Batiment res = null;
        for (Batiment batiment : liste) {
            if (!this.getCite().contains(batiment) && (batiment.getCout() <= maxArgent)) {
                if (res != null) {
                    res = plusOuMoins(minOuMax,res,batiment);
                } else {
                    res = batiment;
                }
            }
        }
        return res;
    }

    public Batiment plusOuMoins(String minOuMax, Batiment res, Batiment batiment) {
        if (minOuMax.equals("+")) {
            if (res.getCout() < batiment.getCout()) {
                res = batiment;
            }
        } else {
            if (res.getCout() > batiment.getCout()) {
                res = batiment;
            }
        }
        return res;
    }

    public int[] choixSuivantPersonnalite(int[] choixPersonnage){
        int[] nombreBatiment = this.nombreBatimentParQuartier(); // RELIGIEUX,MILITAIRE,NOBLE,COMMERCE,MERVEILLE

        choixPersonnage[PERSONNAGE_VOLEUR] += 3 - this.getArgent(); // voleur ça dépend de son argent
        choixPersonnage[PERSONNAGE_ROI] += nombreBatiment[2]; // on ajoute le nombre de bâtiments noble au roi
        choixPersonnage[PERSONNAGE_EVEQUE] += nombreBatiment[0]; // on ajoute le nombre de bâtiments religieux à l'évèque
        choixPersonnage[PERSONNAGE_MARCHAND] += nombreBatiment[3] +1; // on ajoute le nombre de bâtiments marchands au marchand
        choixPersonnage[PERSONNAGE_ARCHITECTE] += this.getArgent()-2; // si le joueur a plein d'argent il prend architecte
        choixPersonnage[PERSONNAGE_CONDOTTIERE] += nombreBatiment[1]; // on ajoute le nombre de bâtiments militaire au condottiere

        if(this.getMain().size()<2){ //si on n'a plus de carte on prend magicien ou architecte
            choixPersonnage[PERSONNAGE_MAGICIEN] += 5;
            choixPersonnage[PERSONNAGE_ARCHITECTE] += 2;
        }

        switch(joueur.getHostileLevel()){
            case 0,1,2,3 : // batisseur = 0-3
                choixPersonnage[PERSONNAGE_ROI] += 2;
                choixPersonnage[PERSONNAGE_EVEQUE] += 2;
                choixPersonnage[PERSONNAGE_MARCHAND] += 2;
                choixPersonnage[PERSONNAGE_ARCHITECTE] += 2;
                choixPersonnage[PERSONNAGE_CONDOTTIERE] += 2;
                break;

            case 4,5,6,7 : // opportuniste = 4-7
                choixPersonnage[PERSONNAGE_ASSASSIN] += this.getJoueur().getHostileLevel()/2;
                choixPersonnage[PERSONNAGE_VOLEUR] += this.getJoueur().getHostileLevel()/2;
                if(this.getTailleCite()>=5){
                    choixPersonnage[PERSONNAGE_CONDOTTIERE] += 2;
                    choixPersonnage[PERSONNAGE_EVEQUE] += 4;
                }
                break;

            default: // agressif = 8 - 10
                choixPersonnage[PERSONNAGE_ASSASSIN] += this.getJoueur().getHostileLevel()/2;
                choixPersonnage[PERSONNAGE_VOLEUR] += this.getJoueur().getHostileLevel()/2 -1;
                choixPersonnage[PERSONNAGE_CONDOTTIERE] += this.getJoueur().getHostileLevel()/2 -1;
        }
        return  choixPersonnage;
    }

    public int[] choixFinDePartie(int[] choixPersonnage){
        if(this.getTailleCite()>=4 && this.getArgent()>=4){
            choixPersonnage[PERSONNAGE_ARCHITECTE] += 10;
        }
        if(this.getTailleCite()== (CITE_TERMINEE - 1)){
            for(int i =0;i<choixPersonnage.length;i++){
                choixPersonnage[i] += choixPersonnage.length - i ;
            }
            choixPersonnage[PERSONNAGE_EVEQUE] += 2;
            choixPersonnage[PERSONNAGE_CONDOTTIERE] += 2;
        }

        if(this.getTailleCite()>=5 && (coutMinMaxConstructible(this.getMain(),"+",this.getArgent())!=null)){
            choixPersonnage[PERSONNAGE_ROI] += 3;
        }
        return choixPersonnage;
    }

    // Le bot choisit un personnage dans la liste disponible et renvoie la liste sans le personnage
    public ArrayList<Personnage> chooseCharacter(ArrayList<Personnage> listePersonnage,ArrayList<Personnage> defausse){
        int[] choixPersonnage = new int[]{0,0,0,0,0,0,0,0}; // plus une valeur est grande plus un personnage va être pris

        choixPersonnage = choixSuivantPersonnalite(choixPersonnage);

        // stratégie de fin de partie
        choixPersonnage = choixFinDePartie(choixPersonnage);

        // on prend donc dans la liste des personnages possibles, le personnage avec la plus grande valeur
        int[] choixPersonnageDispo = new int[]{0,0,0,0,0,0,0,0};
        for(int indexPersonnage=0;indexPersonnage<listePersonnage.size();indexPersonnage++){
            choixPersonnageDispo[listePersonnage.get(indexPersonnage).getNumero()-1]=1;
        }

        // on enlève le score de choix pour les personnages n'ont disponibles
        for(int indexPersonnage=0;indexPersonnage<8;indexPersonnage++){
            if(choixPersonnageDispo[indexPersonnage]==0){choixPersonnage[indexPersonnage]=0;}
        }
        int choixFinal = 0;
        int compteur = choixPersonnage[PERSONNAGE_ASSASSIN];
        for(int indexPersonnage=1;indexPersonnage<8;indexPersonnage++){
            if(choixPersonnage[indexPersonnage]>compteur){
                compteur = choixPersonnage[indexPersonnage];
                choixFinal = indexPersonnage;
            }
        }
        for(int indexPersonnage=0;indexPersonnage<listePersonnage.size();indexPersonnage++){
            if(listePersonnage.get(indexPersonnage).getNumero()==choixFinal+1){
                this.setPersonnage(listePersonnage.get(indexPersonnage).getNumero());
                listePersonnage.remove(indexPersonnage);
                this.ciblesDispo = this.setCibles(listePersonnage,defausse);
                return listePersonnage;
            }
        }
        this.setPersonnage(listePersonnage.get(0).getNumero()); // si aucun personnage n'est meilleur le bot prend le premier
        listePersonnage.remove(0);
        this.ciblesDispo = this.setCibles(listePersonnage,defausse);
        return listePersonnage;
    }

    //Sélectionne les cibles à la disposition du bot pour son action
    public ArrayList<Integer> setCibles(ArrayList<Personnage> encoreDispo, ArrayList<Personnage> defausse){
        ArrayList<Integer> cibles = new ArrayList<>();
        if(encoreDispo.size()>=3){
            for (Personnage personnage : encoreDispo) {
                cibles.add(personnage.getNumero());
            }
        }
        for(int i=2; i<9;i++){
            cibles.add(i);
        }
        for (Personnage personnage : encoreDispo) {
            cibles.remove((Integer) personnage.getNumero());
        }
        for (Personnage personnage : defausse) {
            cibles.remove((Integer) personnage.getNumero());
        }
        cibles.remove((Integer) this.joueur.getPersonnage().getNumero());
        return cibles;
    }

    public int choixCible(Bot[] adversaires){
        if(this.getRole().getNumero()==1){return choixCibleAssassin(adversaires);}
        int actualKilled = -1;
        for (Bot adversaire : adversaires) {
            if (adversaire.getJoueur().getKilled()) {
                actualKilled = adversaire.getJoueur().getPersonnage().getNumero();
            }
        }
        if(this.ciblesDispo.contains(7) && actualKilled!=7){return 7;}
        if(this.ciblesDispo.contains(6) && actualKilled!=6){return 6;}
        if(this.ciblesDispo.contains(4) && actualKilled!=4){return 4;}
        if(this.ciblesDispo.contains(8) && actualKilled!=8){return 8;}
        if(this.ciblesDispo.contains(5) && actualKilled!=5){return 5;}
        return 3;
    }

    // l'assassin choisit sa cible suivant plusieurs facteurs
    public int choixCibleAssassin(Bot[] adversaires){
        if(this.ciblesDispo.contains((PERSONNAGE_CONDOTTIERE + 1))){//On regarde si le condottiere est présent
            int maxBat = 0;
            for (Bot adversaire : adversaires) {
                if (adversaire.getTailleCite() >= maxBat) {maxBat = adversaire.getTailleCite();}
            }
            if(this.getTailleCite()==maxBat){//on regarde si on est une cible probable du condottiere
                return (PERSONNAGE_CONDOTTIERE + 1);
            }
            if(maxBat>=6 && this.ciblesDispo.contains((PERSONNAGE_EVEQUE + 1))){//Si quelqu'un va bientot finir, on tue l'eveque dans l'espoir que le condottiere le ralentisse
                return (PERSONNAGE_EVEQUE + 1);
            }
        }
        if(this.ciblesDispo.contains((PERSONNAGE_MAGICIEN + 1))){//Si on possède le maximum de cartes, on tue le magicien pour ne pas se les faire voler
            int maxCarte = 0;
            for (Bot adversaire : adversaires) {
                if (adversaire.getMain().size() >= maxCarte) {maxCarte = adversaire.getMain().size();}
            }
            if(maxCarte==this.getMain().size()){return (PERSONNAGE_MAGICIEN + 1);}
        }
        if(this.ciblesDispo.contains((PERSONNAGE_VOLEUR + 1))) {//Si le voleur a possibilité de beaucoup s'enrichir
            int maxArgent = 0;
            for (Bot adversaire : adversaires) {
                if (adversaire.getArgent() >= maxArgent) {maxArgent = adversaire.getArgent();}
            }
            if (maxArgent >= 4) {return (PERSONNAGE_VOLEUR + 1);}
        }
        int[] order = new int[]{7,6,4,2,8,5};
        for (int k : order) {
            if (this.ciblesDispo.contains(k)) {
                return k;
            }
        }
        return (PERSONNAGE_MAGICIEN + 1);
    }

    public String toString() {
        return joueur.getPersonnage()+"";
    }
}