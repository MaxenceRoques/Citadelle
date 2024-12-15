package personnages;

import carte.Deck;
import joueurs.Joueur;

public class Assassin extends Personnage {
    private static final int PERSONNAGE_ASSASSIN = 1;

    public Assassin(){
        super("Assassin",PERSONNAGE_ASSASSIN,null);
    }

    @Override
    public int action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) {
        cible.isKilled();
        return 0;
    }

    @Override
    public String message(String nom, int numero){
        if (numero!=PERSONNAGE_ASSASSIN){
            return this.getNom() + " a tué " + nom;
        }
        return "";
    }
}
