package joueurs;

import personnages.*;
import carte.*;
import java.util.ArrayList;

public class Joueur{
    private final String nom;
    private int argent; // Un joueur possède de l'argent
    private Personnage personnage; // Un joueur à un personnage a chaque tour
    private final ArrayList<Batiment> citeJoueur; // Un joueur possède un cité composé de batiment
    private ArrayList<Batiment> mainJoueur; // Un joueur à une main composée de batiment
    private boolean killed = false;
    private boolean robbed = false;
    private int hostileLevel = 0 ; // valeur entre 0 et 5 avec 0 qui n'est pas agressif et 5 est super violent
    private boolean aDetruitBatiment = false; // pour utilisation du cimetiere

    public Joueur(int argent, String nom){
        mainJoueur = new ArrayList<>();
        citeJoueur = new ArrayList<>();
        this.argent = argent;
        this.nom = nom;
        this.personnage = null;
    }

    public String getNom(){
        return this.nom;
    }

    public int getArgent(){ // Savoir combien d'argent possède le joueur
        return argent;
    }

    public Personnage getPersonnage(){ // Connaitre le personnage du joueur
        return personnage;
    }

    public ArrayList<Batiment> getCiteJoueur(){ // Connaitre la cité du joueur
        return citeJoueur;
    }

    public ArrayList<Batiment> getMainJoueur(){ // Connaitre la main du joueur
        return mainJoueur;
    }

    public boolean getKilled(){return this.killed;}

    public void piocherArgent(int argent){ // Le joueur a choisi de piocher de l'argent
        this.argent += argent;
    }

    public void piocherBatiment(Batiment batiment){ // Le joueur a choisi de piocher un batiment
        this.mainJoueur.add(batiment);
    }

    public void retireArgent(int cout){
        this.argent-=cout;
    }

    public void setHostileLevel(int level) {if(level>=0 && level <=10){ this.hostileLevel = level;}}

    public int getHostileLevel(){ return this.hostileLevel;}

    public boolean getDetruitBatiment() {
        return aDetruitBatiment;
    }

    public void setaDetruitBatiment(boolean a){this.aDetruitBatiment = a;}

    public void setPersonnage(int numero){ // On change le personnage du joueur
        switch (numero){
            case 1: this.personnage = new Assassin();break;
            case 2: this.personnage = new Voleur();break;
            case 3:this.personnage = new Magicien();break;
            case 4:this.personnage = new Roi();break;
            case 5:this.personnage = new Eveque();break;
            case 6:this.personnage = new Marchand();break;
            case 7:this.personnage = new Architecte();break;
            case 8:this.personnage = new Condottiere();break;
            default: this.personnage = null;
        }
    }

    public Batiment popBatiment(){
        Batiment aRetirer = null;
        if(!this.mainJoueur.isEmpty()){
            aRetirer = this.mainJoueur.get(0);
            this.mainJoueur.remove(0);
        }
        return aRetirer;
    }

    public boolean citeContains(String nomCarte) {
        for (Batiment bat : this.citeJoueur) {
            if (bat.getNom().equals(nomCarte)) {
                return true;
            }
        }
        return false;
    }

    public Batiment echangeBatiment(Batiment batiment,int indice){
        Batiment res = null;
        if (indice>=0 && indice<this.mainJoueur.size()){
            res = this.mainJoueur.get(indice);
            ArrayList<Batiment> remplacement = new ArrayList<>();
            for (int i=0;i<this.mainJoueur.size();i++){
                if(i!=indice){remplacement.add(this.mainJoueur.get(i));}
                else{remplacement.add(batiment);}
            }
            this.mainJoueur = remplacement;
        }
        return res;
    }

    public boolean construireBatiment(Batiment batiment){ // Construire un batiment dans la cité du joueur implique un cout et que l'on retire la carte de la main du joueur
        if(mainJoueur.contains(batiment)){  // On vérifie que le batiment est bien présent dans la main du joueur pour pouvoir le construire
            this.citeJoueur.add(batiment);
            this.argent -= batiment.getCout();
            this.mainJoueur.remove(batiment);
            return true;
        }
        return false;
    }

    public void jouerAction(Joueur j, Joueur cible, Deck deck, int[] indices,int typeAction){
        if(!this.killed){
            this.argent+=this.personnage.action(j,cible,deck,indices,typeAction);
        }
    }

    public String messagePiocheArgent(){ // On affiche le choix du joueur
        return "Le joueur a pioché 2 pièces.";
    }

    public String messagePiocheBatiment(){ // On affiche le choix du joueur
        return "Le joueur a pioché un batiment.";
    }

    public String messageAction(Joueur cible){return personnage.message(cible.getPersonnage().getNom(), cible.getPersonnage().getNumero());}

    public String affichageJoueur(){ // On affiche tout ce qu'il y a à savoir sur le joueur
        return "Roi appelle "+ this.getPersonnage() + "("+ this.getHostileLevel()+ ")" +". Pour ce tour, il possède " + this.getArgent() + " pièces, " + this.citeJoueur.size() +
                " batiments dans sa cité qui sont " + this.getCiteJoueur() + " et une main qui est " + this.getMainJoueur() + "." ;
    }

    // Méthode pour retirer tout l'argent d'un joueur qui a été volé
    public int isRobbed(){
        this.robbed = true;
        return 0;
    }

    public boolean getRobbed(){return this.robbed;}
    public void resetRobbed(){this.robbed = false;}

    public void isKilled(){
        this.killed = true;
    }

    public void resetKilled(){
        this.killed = false;
    }

    public boolean estPersonnage(String nom){return nom.equals(this.personnage.getNom());}
}