package testPersonnage;

import personnages.*;
import joueurs.Joueur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssassinTest {

    Personnage Assassin = new Assassin();
    @Test
    void action() {
        Joueur j1 = new Joueur(12, "Jack L'éventreur");
        Joueur j2 = new Joueur(8, "Francois");
        j1.setPersonnage(1);
        j2.setPersonnage(7);

        Assassin.action(j1, j2, null, null, 0);
        assertTrue(j2.getKilled());

    }

    @Test
    void message() {

        Personnage Voleur = new Voleur();

        assertEquals("", Assassin.message(Assassin.getNom(),Assassin.getNumero()));
        assertEquals("Assassin a tué Voleur", Assassin.message(Voleur.getNom(),Voleur.getNumero()));
    }

}