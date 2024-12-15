package testPersonnage;

import personnages.*;
import carte.Batiment;
import carte.quartier;
import joueurs.Joueur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoiTest {
    Personnage Roi = new Roi();

    @Test
    void action() {
        Joueur j1 = new Joueur(12, "Louis XVI");
        Batiment batimentTest = new Batiment("batimentTest", 1, quartier.NOBLE);
        Batiment merveille = new Batiment("Ecole de Magie", 1, quartier.MERVEILLE);


        j1.setPersonnage(6);
        assertEquals(0, Roi.action(j1, null, null, null, 0));

        j1.piocherBatiment(batimentTest);
        j1.construireBatiment(batimentTest);
        assertEquals(1, Roi.action(j1, null, null, null, 0));

        j1.piocherBatiment(merveille);
        j1.construireBatiment(merveille);
        assertEquals(2, Roi.action(j1, null, null, null, 0));
    }

    @Test
    void message() {
        assertEquals("Roi prend la couronne\n", Roi.message(null,0));
    }
}