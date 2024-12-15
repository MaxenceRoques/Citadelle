package personnages;

import carte.*;
import joueurs.Joueur;


public abstract class Personnage extends Carte {
    private String nom; // nom du perso
    private int numero; // numéro d'ordre de jeu
    private quartier type;

    protected Personnage(String nom,int numero,quartier type){
        this.nom = nom;
        this.numero = numero;
        this.type = type;
    }

    public String getNom(){
        return this.nom;
    }

    public int getNumero(){
        return this.numero;
    }

    public quartier getType(){
        return this.type;
    }

    // message de fin de tour du personnage
    public String messageActionCarte(){
        return this.getNom() + " a joué";
    }


    public abstract String message(String nom, int numero);

    public abstract int action(Joueur joueurActuel, Joueur cible, Deck deck, int[] nombre,int typeAction);

    public String toString() {
        return this.getNom();
    }
}
