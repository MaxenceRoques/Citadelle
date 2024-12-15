package carte;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private final LinkedList<Batiment> pioche;

    public Deck() {
        // Batiments de type commerçant
        Batiment comptoir = new Batiment("Comptoir", 3, quartier.COMMERCE);
        Batiment echoppe = new Batiment("Echoppe", 2, quartier.COMMERCE);
        Batiment hdv = new Batiment("Hotel de Ville", 5, quartier.COMMERCE);
        Batiment marche = new Batiment("Marche", 2, quartier.COMMERCE);
        Batiment port = new Batiment("Port", 4, quartier.COMMERCE);
        Batiment taverne = new Batiment("Taverne", 1, quartier.COMMERCE);

        // Batiments de type religieux
        Batiment cathedrale = new Batiment("Cathedrale", 5, quartier.RELIGIEUX);
        Batiment eglise = new Batiment("Eglise", 2, quartier.RELIGIEUX);
        Batiment monastere = new Batiment("Monastere", 3, quartier.RELIGIEUX);
        Batiment temple = new Batiment("Temple", 1, quartier.RELIGIEUX);

        // Batiments de type noble
        Batiment chateau = new Batiment("Chateau", 4, quartier.NOBLE);
        Batiment manoir = new Batiment("Manoir", 3, quartier.NOBLE);
        Batiment palais = new Batiment("Palais", 5, quartier.NOBLE);

        // Batiments de type militaire
        Batiment caserne = new Batiment("Caserne", 3, quartier.MILITAIRE);
        Batiment forteresse = new Batiment("Forteresse", 5, quartier.MILITAIRE);
        Batiment prison = new Batiment("Prison", 2, quartier.MILITAIRE);
        Batiment tourGuet = new Batiment("Tour de Guet", 1, quartier.MILITAIRE);

        // Batiments de type merveille
        Batiment bibliotheque = new Batiment("Bibliotheque", 6, quartier.MERVEILLE);
        Batiment cimetiere = new Batiment("Cimetiere", 5, quartier.MERVEILLE);
        Batiment courMiracles = new Batiment("Cour des Miracles", 2, quartier.MERVEILLE);
        Batiment donjon = new Batiment("Donjon", 3, quartier.MERVEILLE);
        Batiment dracoport = new Batiment("Dracoport", 6, quartier.MERVEILLE);
        Batiment ecoleMagie = new Batiment("Ecole de Magie", 6, quartier.MERVEILLE);
        Batiment forge = new Batiment("Forge", 5, quartier.MERVEILLE);
        Batiment grandeMuraille = new Batiment("Grande Muraille", 6, quartier.MERVEILLE);
        Batiment laboratoire = new Batiment("Laboratoire", 5, quartier.MERVEILLE);
        Batiment observatoire = new Batiment("Observatoire", 4, quartier.MERVEILLE);
        Batiment salleCartes = new Batiment("Salle des Cartes", 5, quartier.MERVEILLE);
        Batiment tresorImperial = new Batiment("Tresor Imperial", 5, quartier.MERVEILLE);
        Batiment universite = new Batiment("Universite", 6, quartier.MERVEILLE);

        // Création de la pioche
        pioche = new LinkedList<>();

        // Ajout des batiments
        for (int i=0; i<2; i++) {
            pioche.add(hdv); // commerçant
            pioche.add(cathedrale); // religieux
            pioche.add(forteresse); // militaire
            pioche.add(donjon); // merveille
        }
        for (int i=0; i<3; i++) {
            // commerçant
            pioche.add(comptoir);
            pioche.add(echoppe);
            pioche.add(port);
            // religieux
            pioche.add(eglise);
            pioche.add(monastere);
            pioche.add(temple);
            // noble
            pioche.add(palais);
            // militaire
            pioche.add(caserne);
            pioche.add(prison);
            pioche.add(tourGuet);
        }
        for (int i=0; i<4; i++) {
            pioche.add(marche); // commerçant
            pioche.add(chateau); // noble
        }
        for (int i=0; i<5; i++) {
            pioche.add(taverne); // commerçant
            pioche.add(manoir); // noble
        }

        // merveilles
        pioche.add(bibliotheque);
        pioche.add(cimetiere);
        pioche.add(courMiracles);
        pioche.add(dracoport);
        pioche.add(ecoleMagie);
        pioche.add(forge);
        pioche.add(grandeMuraille);
        pioche.add(laboratoire);
        pioche.add(observatoire);
        pioche.add(salleCartes);
        pioche.add(tresorImperial);
        pioche.add(universite);

        // on mélange le deck
        Collections.shuffle(pioche);
    }

    public Batiment pioche() {
        return pioche.pop();
    }

    public void jette(Batiment e) {
        pioche.add(this.pioche.size(),e);
    }

    public LinkedList<Batiment> getDeck() {
        return this.pioche;
    }

    public int getTaille(){
        return pioche.size();
    }

    public String toString() {
        StringBuilder res = new StringBuilder("->");
        int i=0;
        while(this.pioche.size()>i) {
            res.append(this.pioche.get(i++)).append(" | ");
        }
        res.append("->");
        return res.toString();
    }
}
