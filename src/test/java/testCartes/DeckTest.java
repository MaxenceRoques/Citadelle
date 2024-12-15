package testCartes;

import carte.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {


    @Test
    void testJette() {
        Deck deck = new Deck();
        Batiment batiment = new Batiment("Test Batiment", 1, quartier.RELIGIEUX);
        deck.jette(batiment);
        assertEquals(batiment, deck.getDeck().getLast()); // Vérifie que le batiment a été jeté au bon endroit
    }

    @Test
    void testDeckSize() {
        Deck deck = new Deck();
        int initialSize = deck.getDeck().size();
        deck.pioche();
        assertEquals(initialSize - 1, deck.getDeck().size()); // Vérifie que la taille du deck diminue après avoir pioché
    }

    @Test
    void testDeckShuffling() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Vérifie que deux decks créés successivement ne sont pas dans le même ordre
        assertNotEquals(deck1.toString(), deck2.toString());
    }

    @Test
    void testPioche() {
        Deck deck = new Deck();
        assertNotNull(deck.pioche()); // Vérifie que la pioche ne renvoie pas null
    }
}
