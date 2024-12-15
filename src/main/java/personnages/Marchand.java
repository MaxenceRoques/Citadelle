package personnages;

import carte.*;
import joueurs.Joueur;

public class Marchand extends Personnage {
    private static final int PERSONNAGE_MARCHAND = 6;
    private static final int TYPE_ACTION_PIECE_SUIVANT_TYPE_QUARTIER = 0;
    public Marchand(){
        super("Marchand",PERSONNAGE_MARCHAND, quartier.COMMERCE);
    }

    @Override
    public int action(Joueur joueurActuel, Joueur cible, Deck deck, int[] nombre,int typeAction){
        int argentGagne = 0;
        if(typeAction==TYPE_ACTION_PIECE_SUIVANT_TYPE_QUARTIER){
            for (Batiment batiment:joueurActuel.getCiteJoueur()){
                if (batiment.getType()==quartier.COMMERCE){argentGagne++;}
                if (batiment.getNom().equals("Ecole de Magie")){argentGagne++;}
            }
        }
        else{
            argentGagne = 1;
        }


        return argentGagne;
    }

    @Override
    public String message(String nom, int numero){return "";}
}
