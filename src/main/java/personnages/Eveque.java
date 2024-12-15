package personnages;


import carte.*;
import joueurs.Joueur;

public class Eveque extends Personnage {

    private static final int PERSONNAGE_EVEQUE = 5;
    public Eveque(){
        super("Eveque",PERSONNAGE_EVEQUE, quartier.RELIGIEUX);
    }

    @Override
    public int action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction){
        int argentGagne = 0;
        for (Batiment batiment:joueur.getCiteJoueur()){
            if (batiment.getType()==quartier.RELIGIEUX){argentGagne++;}
            if (batiment.getNom().equals("Ecole de Magie")){argentGagne++;}
        }
        return argentGagne;
    }

    @Override
    public String message(String nom, int numero){return "";}
}
