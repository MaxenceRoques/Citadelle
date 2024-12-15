package personnages;

import carte.Deck;
import joueurs.Joueur;

public class Voleur extends Personnage {
    private static final int PERSONNAGE_VOLEUR = 2;
    private static final int PERSONNAGE_ASSASSIN = 1;

    public Voleur(){
        super("Voleur",PERSONNAGE_VOLEUR,null);
    }

    @Override
    public int action(Joueur j, Joueur cible, Deck deck, int[] nombre,int typeAction){
        return cible.isRobbed();
    }

    @Override
    public String message(String nom, int numero){
        if (numero!=PERSONNAGE_VOLEUR && numero!=PERSONNAGE_ASSASSIN){
            return this.getNom() + " a volé " + nom;
        }
        return "";
    }
}
