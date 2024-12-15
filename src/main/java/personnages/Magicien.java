package personnages;

import carte.Batiment;
import carte.Deck;
import joueurs.Joueur;
import java.util.ArrayList;

public class Magicien extends Personnage {
    private static final int PERSONNAGE_MAGICIEN = 3;
    private static final int TYPE_ACTION_MAGICIEN_AVEC_JOUEUR = 0;
    private boolean messagePioche;
    public Magicien(){
        super("Magicien",PERSONNAGE_MAGICIEN,null);
    }

    @Override
    public int action(Joueur joueurActuel, Joueur cible, Deck deck, int[] nombreCartes,int typeAction) {
        if(typeAction==TYPE_ACTION_MAGICIEN_AVEC_JOUEUR){ // on échange avec un joueur
            ArrayList<Batiment> aEchanger = new ArrayList<>();
            while(!joueurActuel.getMainJoueur().isEmpty()){
                aEchanger.add(joueurActuel.popBatiment());
            }
            while(!cible.getMainJoueur().isEmpty()){
                joueurActuel.piocherBatiment(cible.popBatiment());
            }
            for (int carteEchangée=0;carteEchangée<aEchanger.size();carteEchangée++){
                cible.piocherBatiment(aEchanger.get(carteEchangée));
            }
            messagePioche = false;
        }
        else{ // on échange avec la pioche
            for (int carte=0;carte<nombreCartes.length;carte++){
                if(nombreCartes[carte]==1){
                    Batiment echange = deck.pioche();
                    Batiment defause = joueurActuel.echangeBatiment(echange,carte);
                    deck.jette(defause);
                }
            }
            messagePioche = true;
        }
        return 0;
    }

    @Override
    public String message(String nom, int numero){
        if(messagePioche){
            return this.getNom() + " a échangé ses cartes avec la pioche\n";
        }
        return this.getNom() + " a échangé ses cartes avec " + nom+"\n";
    }
}
