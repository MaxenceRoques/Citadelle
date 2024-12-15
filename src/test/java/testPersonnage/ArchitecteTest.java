package testPersonnage;

import personnages.*;
import carte.*;
import joueurs.Joueur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArchitecteTest {

    Personnage Architecte = new Architecte();
    @Test
    void action() {

        Joueur j1 = new Joueur(12, "Riccardo Morandi");
        Batiment batimentTest = new Batiment("batimentTest", 2, quartier.RELIGIEUX);
        Deck deck = new Deck();
        int tailleDeck = deck.getTaille();

        j1.setPersonnage(7);
        j1.piocherBatiment(batimentTest);
        assertEquals(1,j1.getMainJoueur().size());

        Architecte.action(j1, null, deck, null, 1);

        assertEquals(3,j1.getMainJoueur().size());
        assertEquals(tailleDeck - 2, deck.getTaille());
    }

    @Test
    void message() {

        Personnage Roi = new Roi();
        assertEquals("", Architecte.message(Roi.getNom(),Roi.getNumero()));
        assertEquals("", Architecte.message(Architecte.getNom(),Architecte.getNumero()));
    }
}