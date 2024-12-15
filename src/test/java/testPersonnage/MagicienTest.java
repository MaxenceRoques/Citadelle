package testPersonnage;

import carte.*;
import joueurs.Joueur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicienTest {

    @Test
    void actionEchangeJoueur() {
        Joueur j1 = new Joueur(2,"Merlin");
        Joueur j2 = new Joueur(2,"j2");
        Deck deck = new Deck();
        Batiment comptoir = new Batiment("Comptoir", 3, quartier.COMMERCE);
        Batiment cathedrale = new Batiment("Cathedrale", 5, quartier.RELIGIEUX);
        j1.setPersonnage(3);
        j2.setPersonnage(1);
        j1.piocherBatiment(comptoir);
        j2.piocherBatiment(cathedrale);
        assertTrue(j1.getMainJoueur().contains(comptoir));
        assertTrue(j2.getMainJoueur().contains(cathedrale));
        j1.jouerAction(j1,j2,deck,new int[1],0);
        assertTrue(j2.getMainJoueur().contains(comptoir));
        assertTrue(j1.getMainJoueur().contains(cathedrale));
    }

    @Test
    void actionEchangeJoueur2(){
        Joueur j1 = new Joueur(2,"Merlin");
        Joueur j2 = new Joueur(2,"j2");
        Deck deck = new Deck();

        Batiment comptoir = new Batiment("Comptoir", 3, quartier.COMMERCE);
        Batiment echoppe = new Batiment("Echoppe", 2, quartier.COMMERCE);
        Batiment hdv = new Batiment("Hotel de Ville", 5, quartier.COMMERCE);
        Batiment marche = new Batiment("Marche", 2, quartier.COMMERCE);
        Batiment port = new Batiment("Port", 4, quartier.COMMERCE);
        Batiment taverne = new Batiment("Taverne", 1, quartier.COMMERCE);

        Batiment cathedrale = new Batiment("Cathedrale", 5, quartier.RELIGIEUX);
        Batiment eglise = new Batiment("Eglise", 2, quartier.RELIGIEUX);
        Batiment monastere = new Batiment("Monastere", 3, quartier.RELIGIEUX);
        Batiment temple = new Batiment("Temple", 1, quartier.RELIGIEUX);

        j1.setPersonnage(3);
        j2.setPersonnage(1);

        j1.piocherBatiment(comptoir);
        j1.piocherBatiment(echoppe);
        j1.piocherBatiment(hdv);
        j1.piocherBatiment(marche);
        j1.piocherBatiment(port);
        j1.piocherBatiment(taverne);
        j2.piocherBatiment(cathedrale);
        j2.piocherBatiment(eglise);
        j2.piocherBatiment(monastere);
        j2.piocherBatiment(temple);

        assertEquals("[Comptoir, Echoppe, Hotel de Ville, Marche, Port, Taverne]",j1.getMainJoueur().toString());
        assertEquals("[Cathedrale, Eglise, Monastere, Temple]",j2.getMainJoueur().toString());

        j1.jouerAction(j1,j2,deck,new int[0],0);

        assertEquals("[Comptoir, Echoppe, Hotel de Ville, Marche, Port, Taverne]",j2.getMainJoueur().toString());
        assertEquals("[Cathedrale, Eglise, Monastere, Temple]",j1.getMainJoueur().toString());
    }

    @Test
    void actionEchangeJoueurVide(){
        Joueur j1 = new Joueur(2,"Merlin");
        Joueur j2 = new Joueur(2,"j2");
        Deck deck = new Deck();
        Batiment cathedrale = new Batiment("Cathedrale", 5, quartier.RELIGIEUX);
        j1.setPersonnage(3);
        j2.setPersonnage(1);
        j2.piocherBatiment(cathedrale);
        assertTrue(j1.getMainJoueur().isEmpty());
        assertTrue(j2.getMainJoueur().contains(cathedrale));
        j1.jouerAction(j1,j2,deck,new int[1],0);
        assertTrue(j2.getMainJoueur().isEmpty());
        assertTrue(j1.getMainJoueur().contains(cathedrale));
    }

    @Test
    void actionEchangePioche(){
        Joueur j1 = new Joueur(2,"Merlin");
        Deck deck = new Deck();
        int[] tab = new int[4];
        tab[1]=1;tab[2]=1;

        Batiment comptoir = new Batiment("Comptoir", 3, quartier.COMMERCE);
        Batiment echoppe = new Batiment("Echoppe", 2, quartier.COMMERCE);
        Batiment hdv = new Batiment("Hotel de Ville", 5, quartier.COMMERCE);
        Batiment marche = new Batiment("Marche", 2, quartier.COMMERCE);
        Batiment port = new Batiment("Port", 4, quartier.COMMERCE);
        Batiment taverne = new Batiment("Taverne", 1, quartier.COMMERCE);

        Batiment cathedrale = new Batiment("Cathedrale", 5, quartier.RELIGIEUX);
        Batiment eglise = new Batiment("Eglise", 2, quartier.RELIGIEUX);
        Batiment monastere = new Batiment("Monastere", 3, quartier.RELIGIEUX);
        Batiment temple = new Batiment("Temple", 1, quartier.RELIGIEUX);

        j1.setPersonnage(3);
        j1.piocherBatiment(cathedrale);
        j1.piocherBatiment(eglise);
        j1.piocherBatiment(monastere);
        j1.piocherBatiment(temple);

        for (int i=0; i<3; i++) {deck.jette(comptoir);}
        for (int i=0; i<3; i++) {deck.jette(echoppe);}
        for (int i=0; i<2; i++) {deck.jette(hdv);}
        for (int i=0; i<4; i++) {deck.jette(marche);}
        for (int i=0; i<3; i++) {deck.jette(port);}
        for (int i=0; i<5; i++) {deck.jette(taverne);}

        assertTrue(j1.getMainJoueur().contains(cathedrale));
        assertTrue(j1.getMainJoueur().contains(eglise));
        assertTrue(j1.getMainJoueur().contains(monastere));
        assertTrue(j1.getMainJoueur().contains(temple));

        j1.jouerAction(j1,null,deck,tab,1);

        assertTrue(j1.getMainJoueur().contains(cathedrale));
        assertFalse(j1.getMainJoueur().contains(eglise));
        assertFalse(j1.getMainJoueur().contains(monastere));
        assertTrue(j1.getMainJoueur().contains(temple));
    }

    @Test
    void message() {
        Joueur j1 = new Joueur(2,"Merlin");
        Joueur j2 = new Joueur(2,"j2");
        Deck deck = new Deck();
        j1.setPersonnage(3);
        j2.setPersonnage(1);
        j1.jouerAction(j1,j2,deck,new int[0],0);
        assertEquals("Magicien a échangé ses cartes avec Assassin\n", j1.messageAction(j2));
        j1.jouerAction(j1,j2,deck,new int[0],1);
        assertEquals("Magicien a échangé ses cartes avec la pioche\n", j1.messageAction(j2));
    }
}