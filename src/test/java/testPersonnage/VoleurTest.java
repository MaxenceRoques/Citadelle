package testPersonnage;

import personnages.*;
import joueurs.Joueur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoleurTest {


    Personnage Voleur = new Voleur();

    @Test
    void action() {
        Joueur j1 = new Joueur(12, "Billy the Kid");
        Joueur j2 = new Joueur(8, "Jules");
        j1.setPersonnage(2);
        j2.setPersonnage(7);

        Voleur.action(j1, j2, null, null, 0);
        assertTrue(j2.getRobbed());
    }

    @Test
    void message() {
        Personnage Magicien = new Magicien();
        Personnage Assassin = new Assassin();
        assertEquals("", Voleur.message(Assassin.getNom(),Assassin.getNumero()));
        assertEquals("", Voleur.message(Voleur.getNom(),Voleur.getNumero()));
        assertEquals("Voleur a volé Magicien", Voleur.message(Magicien.getNom(),Magicien.getNumero()));


    }
}