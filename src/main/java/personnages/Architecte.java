package personnages;

import carte.Deck;
import joueurs.Joueur;

public class Architecte extends Personnage {

    private static final int PERSONNAGE_ARCHITECTE = 7;
    public Architecte(){
        super("Architecte",PERSONNAGE_ARCHITECTE,null);
    }

    @Override
    public int action(Joueur joueur, Joueur cible, Deck deck,int[] nombre,int typeAction){
        joueur.piocherBatiment(deck.pioche());
        joueur.piocherBatiment(deck.pioche());
        return 0;
    }

    @Override
    public String message(String nom, int numero){return "";}
}
