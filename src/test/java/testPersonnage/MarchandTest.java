package testPersonnage;

import personnages.*;
import carte.*;
import joueurs.Joueur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarchandTest {


    Personnage Marchand = new Marchand();

    @Test
    void action() {
        Joueur j1 = new Joueur(12, "Ponzi");
        Batiment batimentTest = new Batiment("batimentTest", 1, quartier.COMMERCE);
        Batiment merveille = new Batiment("Ecole de Magie", 1, quartier.MERVEILLE);


        j1.setPersonnage(6);
        assertEquals(0, Marchand.action(j1, null, null, null, 0));

        j1.piocherBatiment(batimentTest);
        j1.construireBatiment(batimentTest);
        assertEquals(1, Marchand.action(j1, null, null, null, 0));

        j1.piocherBatiment(merveille);
        j1.construireBatiment(merveille);
        assertEquals(2, Marchand.action(j1, null, null, null, 0));

        assertEquals(1, Marchand.action(j1, null, null, null, 1));
    }

    @Test
    void message() {
        Personnage Roi = new Roi();
        assertEquals("", Marchand.message(Roi.getNom(),Roi.getNumero()));
    }
}