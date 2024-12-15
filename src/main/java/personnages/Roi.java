package personnages;

import carte.*;
import joueurs.Joueur;

public class Roi extends Personnage {
    private static final int PERSONNAGE_ROI = 4;
    public Roi(){
        super("Roi",PERSONNAGE_ROI, quartier.NOBLE);
    }

    @Override
    public int action(Joueur joueurActuel, Joueur cible, Deck deck, int[] nombre,int typeAction) {
        int argentGagne = 0;
        for (Batiment batiment:joueurActuel.getCiteJoueur()){
            if (batiment.getType()==quartier.NOBLE){argentGagne++;}
            if (batiment.getNom().equals("Ecole de Magie")){argentGagne++;}
        }
        return argentGagne;
    }

    @Override
    public String message(String nom, int numero){
        return this.getNom() + " prend la couronne\n";
    }
}
