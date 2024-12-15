package testPersonnage;

import personnages.*;
import carte.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonnageTest {

    Personnage Assassin = new Assassin();
    Personnage Voleur = new Voleur();
    Personnage Magicien = new Magicien();
    Personnage Roi = new Roi();
    Personnage Eveque = new Eveque();
    Personnage Marchand = new Marchand();
    Personnage Architecte = new Architecte();
    Personnage Condottiere = new Condottiere();


    @Test
    void testGetNow() {
        assertEquals("Assassin", Assassin.getNom());
        assertEquals("Voleur", Voleur.getNom());
        assertEquals("Magicien", Magicien.getNom());
        assertEquals("Roi", Roi.getNom());
        assertEquals("Eveque", Eveque.getNom());
        assertEquals("Marchand", Marchand.getNom());
        assertEquals("Architecte", Architecte.getNom());
        assertEquals("Condottiere", Condottiere.getNom());
    }

    @Test
    void testGetNumero() {
        assertEquals(1,Assassin.getNumero());
        assertEquals(2, Voleur.getNumero());
        assertEquals(3, Magicien.getNumero());
        assertEquals(4, Roi.getNumero());
        assertEquals(5, Eveque.getNumero());
        assertEquals(6, Marchand.getNumero());
        assertEquals(7, Architecte.getNumero());
        assertEquals(8, Condottiere.getNumero());
    }

    @Test
    void testGetType() {
        assertNull(Assassin.getType());
        assertNull(Voleur.getType());
        assertNull(Magicien.getType());
        assertEquals(quartier.NOBLE, Roi.getType());
        assertEquals(quartier.RELIGIEUX, Eveque.getType());
        assertEquals(quartier.COMMERCE, Marchand.getType());
        assertNull(Architecte.getType());
        assertEquals(quartier.MILITAIRE, Condottiere.getType());

    }
    @Test
    void testMessageActionCarte() {
        assertEquals("Assassin a joué", Assassin.messageActionCarte());
        assertEquals("Voleur a joué", Voleur.messageActionCarte());
        assertEquals("Magicien a joué", Magicien.messageActionCarte());
        assertEquals("Roi a joué",Roi.messageActionCarte());
        assertEquals("Eveque a joué", Eveque.messageActionCarte());
        assertEquals("Marchand a joué", Marchand.messageActionCarte());
        assertEquals("Architecte a joué", Architecte.messageActionCarte());
        assertEquals("Condottiere a joué", Condottiere.messageActionCarte());
    }

    @Test
    void testMessage() {
        assertEquals("", Assassin.message("Assassin", 1));
        assertEquals("Assassin a tué Marchand", Assassin.message("Marchand", 6));

        assertEquals("", Voleur.message("Assassin", 1));
        assertEquals("Voleur a volé Marchand", Voleur.message("Marchand", 6));

        assertEquals("Magicien a échangé ses cartes avec Assassin\n", Magicien.message("Assassin", 1));

        assertEquals("Roi prend la couronne\n", Roi.message("Eveque",5));

        assertEquals("", Eveque.message("Roi",4));

        assertEquals("", Marchand.message("Roi",4));

        assertEquals("", Architecte.message("Roi",4));

        assertEquals("Condottiere cible Roi", Condottiere.message("Roi",4));
    }

    @Test
    void testToString() {
        assertEquals("Assassin", Assassin.toString());
        assertEquals("Voleur", Voleur.toString());
        assertEquals("Magicien", Magicien.toString());
        assertEquals("Roi", Roi.toString());
        assertEquals("Eveque", Eveque.toString());
        assertEquals("Marchand", Marchand.toString());
        assertEquals("Architecte", Architecte.toString());
        assertEquals("Condottiere", Condottiere.toString());
    }
    
}