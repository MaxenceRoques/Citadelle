package testPersonnage;

import personnages.*;
import carte.*;
import joueurs.Joueur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvequeTest {

    Personnage Roi = new Roi();
    Personnage Eveque = new Eveque();

    @Test
    void action() {
        Joueur j1 = new Joueur(12, "Marc Dutroux");
        Batiment batimentTest = new Batiment("batimentTest", 1, quartier.RELIGIEUX);
        Batiment merveille = new Batiment("Ecole de Magie", 1, quartier.MERVEILLE);


        j1.setPersonnage(5);
        assertEquals(0, Eveque.action(j1, null, null, null, 0));

        j1.piocherBatiment(batimentTest);
        j1.construireBatiment(batimentTest);
        assertEquals(1, Eveque.action(j1, null, null, null, 0));

        j1.piocherBatiment(merveille);
        j1.construireBatiment(merveille);
        assertEquals(2, Eveque.action(j1, null, null, null, 0));

    }

    @Test
    void message() {
        assertEquals("", Eveque.message(Roi.getNom(),Roi.getNumero()));
    }
}