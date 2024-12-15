package plateau;

import com.vogella.logger.test.UseLogger;

import carte.Batiment;
import carte.Deck;
import personnages.*;
import joueurs.Bot;

import java.util.ArrayList;
import java.util.Random;

public class Plateau {

    private static final int CITE_TERMINEE = 7;
    private static final int PERSONNAGE_CONDOTTIERE = 8;
    private Deck pioche;
    private final Bot[] listeBots; // liste des joueurs présents dans le jeu
    private ArrayList<Bot> ordreDeChoix; // liste ordonnée des joueurs selon le roi actuel

    private int banque; // compteur d'argent disponible
    private int actualKilled; // Numéro du personnage tué pendant ce tour
    private int actualStolen; // Numéro du personnage dépouillé pendant ce tour
    private Bot actualKing; // joueur roi pendant ce tour
    private final Random random;
    private final UseLogger log;

    private ArrayList<Personnage> listePersonnage = new ArrayList<>();
    private ArrayList<Personnage> defausseeVisible = new ArrayList<>();
    private ArrayList<Personnage> defausseeCachee = new ArrayList<>();
    private String messageRound = "";
    private int numRound = 1;
    private int firstToFinish = -1;

    public Plateau(int nbBot, int[] hostileLvel, Random random, UseLogger log) {
        this.random = random;
        this.listeBots = new Bot[nbBot];
        this.listePersonnage.add(new Assassin());
        this.listePersonnage.add(new Voleur());
        this.listePersonnage.add(new Magicien());
        this.listePersonnage.add(new Roi());
        this.listePersonnage.add(new Eveque());
        this.listePersonnage.add(new Marchand());
        this.listePersonnage.add(new Architecte());
        this.listePersonnage.add(new Condottiere());

        for (int joueur=0; joueur<nbBot; joueur++) {
            listeBots[joueur] = new Bot(2, "j"+(joueur+1));
            listeBots[joueur].getJoueur().setHostileLevel(hostileLvel[joueur]);
        }

        this.ordreDeChoix = new ArrayList<>();
        this.pioche = new Deck();

        this.banque = 30-2*nbBot;
        this.actualKilled = 0;
        this.actualStolen = 0;
        this.actualKing = this.listeBots[this.random.nextInt(0,nbBot)];
        this.log = log;
    }

    public Plateau(int nbBot, Random random) {
        this(nbBot,new int[nbBot],random, new UseLogger());
    }
    // Accesseurs
    public Bot[] getListeJoueurs() {return this.listeBots;}

    public ArrayList<Bot> getOrdreDeChoix() {return this.ordreDeChoix;}

    public int getBanque() {return this.banque;}

    public int getActualKilled() {return this.actualKilled;}

    public int getActualStolen() {return this.actualStolen;}

    public Bot getActualKing() {return this.actualKing;}
    public String getMessageRound(){ return this.messageRound;}

    // Mutateurs
    public void setBanque(int argent) {this.banque = argent;}

    public void setActualKilled(int persoNum) {this.actualKilled = persoNum;}

    public void setActualStolen(int persoNum) {this.actualStolen = persoNum;}

    public void setActualKing(Bot bot) {this.actualKing = bot;}
    public void setFirstToFinish(Bot bot, int indexJoueur) {
        if (bot.getTailleCite()>=CITE_TERMINEE && this.firstToFinish == -1) {
            this.firstToFinish = indexJoueur;
        }
    }

    // Autres méthodes
    public void choosingOrder() { // Créé la liste des joueurs selon leur ordre de choix de carte
        int indexRoi = 0;
        StringBuilder str = new StringBuilder();
        this.ordreDeChoix.clear();
        for (int indexBot = 0; indexBot < this.listeBots.length; indexBot++) { // On récupère l'index du roi actuel
            if (this.listeBots[indexBot] == this.actualKing) {
                indexRoi = indexBot;
            }
        }
        str.append("Le roi actuel est : "+listeBots[indexRoi].getNom()+"\nLes joueurs choisissent leur rôle selon l'ordre suivant: ");
        for (int j=0; j < this.listeBots.length; j++) { // La liste ordonnée est créée selon l'ordre horaire (modulo)
            this.ordreDeChoix.add(this.listeBots[(j+indexRoi)%(this.listeBots.length)]);
        }
        for (int i=0; i<this.ordreDeChoix.size()-1; i++) {
            str.append(this.ordreDeChoix.get(i).getNom() + ", ");
        }
        str.append(this.ordreDeChoix.get(this.ordreDeChoix.size()-1).getNom());
        messageRound = str.toString();
    }

    // méthode qui demande au bot de choisir son personnage
    public void choosingCharacter(Bot bot){
        listePersonnage = bot.chooseCharacter(listePersonnage,defausseeVisible);
        messageRound = "Le bot "+bot.getNom()+" a choisi le rôle " +bot.getRole();

    }

    // méthode qui remet les personnages en jeu pour le prochain tour
    public void personnageComeBack(){
        for (Bot listeBot : this.listeBots) {
            this.listePersonnage.add(listeBot.retirePersonnage());
        }
    }

    public void miseEnPlaceCarte(){
        for(Bot bot:listeBots){
            for(int i=0;i<4;i++){
                bot.recoisBatiment(pioche.pioche());
            }
        }
    }

    // on fait jouer tout les joueurs
    public void tourDeJeu(boolean affichage){
        this.log.getLogger().info("\n Tour de jeu : \n");
        if(affichage) {
            System.out.println(("\n Tour de jeu : \n")); // le choix des personnages a été fait, on peut donc commencer l'appel des rôles
        }
        for (int indexPersonnage = 1; indexPersonnage< 9; indexPersonnage++) {
            for (int indexJoueur = 0; indexJoueur < this.listeBots.length; indexJoueur++) {
                if (this.listeBots[indexJoueur].getRole().getNumero() == indexPersonnage) {
                    this.listeBots[indexJoueur].jouerSonTour(this.listeBots,this.pioche);
                    this.log.getLogger().info(this.listeBots[indexJoueur].getMessageRound());
                    if(affichage) {
                        System.out.println(this.listeBots[indexJoueur].getMessageRound());
                    }

                    // Cas du cimetiere
                    cimetiere(indexPersonnage,indexJoueur, affichage);

                    // on regarde si un joueur a fini sa ville est que le premier joueur à finir n'a pas été nommé
                    setFirstToFinish(this.listeBots[indexJoueur], indexJoueur);
                }
            }
        }
    }

    public void cimetiere(int numeroPersoEnCours,int joueurEnCours, boolean affichage) {
        if (numeroPersoEnCours == PERSONNAGE_CONDOTTIERE && listeBots[joueurEnCours].getJoueur().getDetruitBatiment()) {
            for (int indexJoueur = 0; indexJoueur < this.listeBots.length; indexJoueur++) {
                if (listeBots[indexJoueur].getJoueur().citeContains("Cimetiere") && listeBots[indexJoueur].getRole().getNumero() != 8) {
                    Batiment bat = this.pioche.getDeck().get(this.pioche.getTaille() - 1);
                    if (listeBots[indexJoueur].getArgent() >= 1 && !listeBots[indexJoueur].getJoueur().citeContains(bat.getNom()) && listeBots[indexJoueur].getMain().size() <= 6) {
                        listeBots[indexJoueur].recoisBatiment(bat);
                        this.pioche.getDeck().remove(this.pioche.getTaille() - 1);
                        listeBots[indexJoueur].getJoueur().retireArgent(1);
                        this.log.getLogger().info("Le joueur j" + (indexJoueur + 1) + " a utilisé 1PO pour récupérer le batiment " + bat.getNom() + " dans sa main.");
                        if (affichage) {
                            System.out.println("Le joueur j" + (indexJoueur + 1) + " a utilisé 1PO pour récupérer le batiment " + bat.getNom() + " dans sa main.");
                        }
                    }
                }
            }
        }
    }

    public void allRound(boolean affichage) { // Fait agir chaque joueur dans l'ordre des numéros de personnage
        //on donne 4 cartes à chaque joueur
        miseEnPlaceCarte();
        while (!isEndGame()) {
            choosingOrder();
            this.log.getLogger().info("\n-------------------------------------------------------------------------------------------------\n\n" + "Tour "+this.numRound + " :\n\n" + messageRound);
            if(affichage) {
                System.out.println("\n-------------------------------------------------------------------------------------------------\n\n" + "Tour "+this.numRound++ + " :\n\n" + messageRound);
            }
            defausserPersonnages(affichage);
            for(int bot=0; bot<this.listeBots.length;bot++){
                this.choosingCharacter(this.ordreDeChoix.get(bot));
                this.log.getLogger().info(messageRound);
                if(affichage) {
                    System.out.println(messageRound);
                }
            }

            tourDeJeu(affichage);

            score(affichage);

            // On actualise le changement de roi
            for (Bot listeBot : this.listeBots) {
                if (listeBot.estPersonnage("Roi")) {
                    this.actualKing = listeBot;
                }
            }
            personnageComeBack();
            listePersonnage.addAll(defausseeVisible);
            listePersonnage.addAll(defausseeCachee);
            defausseeVisible = new ArrayList<>();
            defausseeCachee = new ArrayList<>();
        }
        this.log.getLogger().severe(messageRound);
        if(affichage) {
            System.out.println(messageRound);
        }
    }

    public void defausserPersonnages(boolean affichage) {
        for (int i=0; i < (6-listeBots.length); i++){
            int indice = random.nextInt(listePersonnage.size());
            while (listePersonnage.get(indice).getNom().equals("Roi")){indice = random.nextInt(listePersonnage.size());}
            this.log.getLogger().info(listePersonnage.get(indice).getNom() + " a été défaussé et ne jouera pas ce tour ci.");
            if(affichage) {
                System.out.println(listePersonnage.get(indice).getNom() + " a été défaussé et ne jouera pas ce tour ci.");
            }
            defausseeVisible.add(listePersonnage.get(indice));
            listePersonnage.remove(listePersonnage.get(indice));
        }
        if(listeBots.length < 7){
            int indice = random.nextInt(listePersonnage.size());
            defausseeCachee.add(listePersonnage.get(indice));
            listePersonnage.remove(listePersonnage.get(indice));
            this.log.getLogger().info("Un autre personnage secret à été défaussé et ne jouera pas ce tour ci. " + defausseeCachee);
            if(affichage) {
                System.out.println("Un autre personnage secret à été défaussé et ne jouera pas ce tour ci. " + defausseeCachee);
            }
        }
    }

    // Déclare la fin du jeu si l'un des bots a 7 bâtiments construits ou plus
    public boolean isEndGame() {
        for (Bot listeBot : this.listeBots) {
            if (listeBot.getTailleCite() >= CITE_TERMINEE) {
                return true;
            }
        }
        return false;
    }

    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... Calcul du score ....... --------------- --- --- --- --- --- //
    // --- --- --- --- --- --------------- ....... --------------- ....... --------------- --- --- --- --- --- //

    // On ajoute au score le cout de chaque batiment construit
    public int scoreEnsembleBatiment(Bot bot){
        int result = 0;
        for (int indexJoueur=0; indexJoueur<bot.getTailleCite(); indexJoueur++) {
            result += bot.getCite().get(indexJoueur).getCout(); // On ajoute au score le cout de chaque batiment construit
        }
        return result;
    }

    // score des différentes merveilles sauf cour des miracles
    public int scoreMerveille(Bot bot){
        int result = 0;
        // Bonus Dracoport
        if (bot.getJoueur().citeContains("Dracoport")) {
            result += 2;
        }
        // Bonus Universite
        if (bot.getJoueur().citeContains("Universite")) {
            result += 2;
        }
        // Bonus Trésor Impérial
        if (bot.getJoueur().citeContains("Tresor Imperial")) {
            result += bot.getArgent();
        }
        // Bonus Salle des Cartes
        if (bot.getJoueur().citeContains("Salle des Cartes")) {
            result += bot.getMain().size();
        }
        return result;
    }

    // Bonus pour finir la cité et finir en premier
    public int scoreFinirCiteOuPremier(Bot bot, int numero){
        if (bot.getTailleCite() >= CITE_TERMINEE) { // Si le joueur a une cité complete
            if (numero == this.firstToFinish) { // et est le premier à l'avoir terminée
                return 4;
            } else { // Si c'est un 2e, 3e, ..., gagnant
                return 2;
            }
        }
        return 0;
    }

    // Si le joueur possède les cinq quartiers différents ou quatre avec la cour des miracles renvoit true sinon false
    public boolean scoreCinqTypeBatiment(Bot bot){
        int[] typeDeQuartiersConstruits = new int[]{0,0,0,0,0}; // RELIGIEUX,MILITAIRE,NOBLE,COMMERCE,MERVEILLE
        for (int j=0; j<bot.getTailleCite(); j++) {
            typeDeQuartiersConstruits[bot.getCite().get(j).getType().ordinal()] ++; // On ajoute 1 au quartier correspondant dans la HashMap
        }
        int nombreTypeQuartier = 0;
        for(int i=0;i<5;i++){
            if(typeDeQuartiersConstruits[i]!=0){nombreTypeQuartier++;}
        }
        return nombreTypeQuartier == 5 || (nombreTypeQuartier == 4 && bot.getJoueur().citeContains("Cour des Miracles") && typeDeQuartiersConstruits[4]>1);
    }

    public Bot score(boolean affichage) {
        int[] resultats = scoreBot();
        // Identification du gagnant
        int scoreMax = resultats[0];
        int gagnant = 0;
        for (int indexJoueur=0; indexJoueur<this.listeBots.length; indexJoueur++){
            if((resultats[indexJoueur] > scoreMax) || (resultats[indexJoueur] == scoreMax && this.listeBots[indexJoueur].getJoueur().getPersonnage().getNumero() > this.listeBots[gagnant].getJoueur().getPersonnage().getNumero())) {
                scoreMax = resultats[indexJoueur];
                gagnant = indexJoueur;
            }
        }
        StringBuilder message = new StringBuilder();
        if(affichage){
            // Message de fin
            message.append("Les scores sont de :\n");
            for (int indexJoueur=0; indexJoueur<this.listeBots.length; indexJoueur++) {
                message.append(resultats[indexJoueur]).append(" pour le joueur ").append(this.listeBots[indexJoueur].getNom());
                if (indexJoueur != this.listeBots.length - 1) {
                    message.append(",");
                }
                message.append("\n");
            }
            this.messageRound = message.toString() + "\nLe joueur " + this.listeBots[gagnant].getNom() + " remporte la victoire !";
        }
        else{
            for(int indexJoueur=0; indexJoueur<this.listeBots.length; indexJoueur++){
                message.append(resultats[indexJoueur]).append(" ");
            }
            this.messageRound = message.toString() + gagnant + " \n";
        }

        return this.listeBots[gagnant];
    }

    public int[] scoreBot() {
        int[] resultat = new int[this.listeBots.length];
        for (int indexJoueur=0; indexJoueur<this.listeBots.length; indexJoueur++) { // Calcul des scores par joueur
            resultat[indexJoueur] += scoreEnsembleBatiment(this.listeBots[indexJoueur]);
            resultat[indexJoueur] += scoreMerveille(this.listeBots[indexJoueur]);
            resultat[indexJoueur] += scoreFinirCiteOuPremier(this.listeBots[indexJoueur],indexJoueur);
            if (scoreCinqTypeBatiment(this.listeBots[indexJoueur])) { // si le joueur a tous les types de quartier
                resultat[indexJoueur] += 3; // on ajoute 3 à son score
            }
        }
        return resultat;
    }
}