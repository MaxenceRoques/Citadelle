package testCartes;

import carte.*;
import personnages.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteTest {

    @Test
    void testCartesBatiment() {
        Carte taverne = new Batiment("Taverne", 1, quartier.COMMERCE);
        Carte monastere = new Batiment("Monastere", 3, quartier.RELIGIEUX);
        Carte forteresse = new Batiment("Forteresse", 5, quartier.MILITAIRE);
        Carte prison = new Batiment("Prison", 2, quartier.MILITAIRE);
        Carte tourGuet = new Batiment("Tour de Guet", 1, quartier.MILITAIRE);

        assertEquals("La carte Taverne a été construite.", taverne.messageActionCarte());
        assertEquals("La carte Monastere a été construite.", monastere.messageActionCarte());
        assertEquals("La carte Forteresse a été construite.", forteresse.messageActionCarte());
        assertEquals("La carte Prison a été construite.", prison.messageActionCarte());
        assertEquals("La carte Tour de Guet a été construite.", tourGuet.messageActionCarte());
    }

    @Test
    void testCartesPersonnage() {
        Personnage assassin = new Assassin();
        Personnage voleur = new Voleur();
        Personnage magicien = new Magicien();
        Personnage roi = new Roi();
        Personnage eveque = new Eveque();
        Personnage marchand = new Marchand();
        Personnage architecte = new Architecte();
        Personnage condottiere = new Condottiere();

        assertEquals("Assassin a joué", assassin.messageActionCarte());
        assertEquals("Voleur a joué", voleur.messageActionCarte());
        assertEquals("Magicien a joué", magicien.messageActionCarte());
        assertEquals("Roi a joué", roi.messageActionCarte());
        assertEquals("Eveque a joué", eveque.messageActionCarte());
        assertEquals("Marchand a joué", marchand.messageActionCarte());
        assertEquals("Architecte a joué", architecte.messageActionCarte());
        assertEquals("Condottiere a joué", condottiere.messageActionCarte());
    }
}
