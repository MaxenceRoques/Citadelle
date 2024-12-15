package personnages;

import carte.*;
import joueurs.Joueur;

public class Condottiere extends Personnage {
    private static final int TYPE_ACTION_PIECE_SUIVANT_TYPE_QUARTIER = 0;
    private static final int PERSONNAGE_CONDOTTIERE = 8;
    private String batimentDetruit = "";
    public Condottiere(){
        super("Condottiere",PERSONNAGE_CONDOTTIERE, quartier.MILITAIRE);
    }

    public String getBatimentDetruit() {
        return this.batimentDetruit;
    }

    @Override
    public int action(Joueur joueurActuel, Joueur cible, Deck deck, int[] indiceCible,int typeAction){
        this.batimentDetruit = "";
        int argentGagne = 0;
        if(typeAction==TYPE_ACTION_PIECE_SUIVANT_TYPE_QUARTIER){
            for (Batiment batiment:joueurActuel.getCiteJoueur()){
                if (batiment.getType()==quartier.MILITAIRE){argentGagne++;}
                if (batiment.getNom().equals("Ecole de Magie")){argentGagne++;}
            }
        }
        else{
            int argentJoueur = joueurActuel.getArgent();
            if(joueurActuel.getHostileLevel()<argentJoueur){
                argentJoueur = joueurActuel.getHostileLevel();
            }
            int batimentCible = -1;
            int grandeMuraille = 0;
            if(cible.citeContains("Grande Muraille")){
                grandeMuraille = 1;
            }
            int coutMax = 0;
            if(joueurActuel.getHostileLevel()==8){coutMax=2;}
            if(joueurActuel.getHostileLevel()==9){coutMax=4;}
            if(joueurActuel.getHostileLevel()==10){coutMax=6;}
            int coutBatiment = joueurActuel.getArgent()+1;
            if(cible.getCiteJoueur().size()<=4){ // si le joueur cible a peu de batîment on détruit un batîment peu cher
                for(int batimentCiteJoueurCible=0;batimentCiteJoueurCible<cible.getCiteJoueur().size();batimentCiteJoueurCible++){
                    // si ce n'est pas le donjon, que son coût est supérieur à celui enregistré et inférieur ou égal à l'argent disponible
                    if(!cible.getCiteJoueur().get(batimentCiteJoueurCible).getNom().equals("Donjon") && cible.getCiteJoueur().get(batimentCiteJoueurCible).getCout()+grandeMuraille-1<=coutMax && cible.getCiteJoueur().get(batimentCiteJoueurCible).getCout()<=coutBatiment && (cible.getCiteJoueur().get(batimentCiteJoueurCible).getCout()+grandeMuraille-1)<=argentJoueur){
                        batimentCible = batimentCiteJoueurCible;
                        coutBatiment = cible.getCiteJoueur().get(batimentCiteJoueurCible).getCout();
                    }
                }
            }
            else { // si le joueur a beaucoup de batîments on détruit un batîment cher
                coutBatiment = -1;
                for(int batimentCiteJoueurCibleCher=0;batimentCiteJoueurCibleCher<cible.getCiteJoueur().size();batimentCiteJoueurCibleCher++){
                    // si ce n'est pas le donjon, que son coût est inférieur à celui enregistré et inférieur ou égal à l'argent disponible
                    if(!cible.getCiteJoueur().get(batimentCiteJoueurCibleCher).getNom().equals("Donjon") && cible.getCiteJoueur().get(batimentCiteJoueurCibleCher).getCout()>=coutBatiment && cible.getCiteJoueur().get(batimentCiteJoueurCibleCher).getCout()+grandeMuraille-1<=coutMax && (cible.getCiteJoueur().get(batimentCiteJoueurCibleCher).getCout()+grandeMuraille-1)<=argentJoueur){
                        batimentCible = batimentCiteJoueurCibleCher;
                        coutBatiment = cible.getCiteJoueur().get(batimentCiteJoueurCibleCher).getCout();
                    }

                }
            }
            joueurActuel.setaDetruitBatiment(false);
            if( batimentCible != -1){
                argentGagne -= cible.getCiteJoueur().get(batimentCible).getCout()+grandeMuraille-1;
                batimentDetruit = cible.getCiteJoueur().get(batimentCible).getNom();
                deck.jette(cible.getCiteJoueur().get(batimentCible));
                cible.getCiteJoueur().remove(batimentCible);
                joueurActuel.setaDetruitBatiment(true);
            }
        }
        return argentGagne;
    }

    @Override
    public String message(String nom, int numero){
        String result = "";
        result += this.getNom() + " cible "+nom;
        if(!this.batimentDetruit.isEmpty()) {
            result += "\n"+this.getNom() + " a attaqué " + nom + " et a détruit " + this.batimentDetruit;this.batimentDetruit = "";
        }
        return result;
    }
}
