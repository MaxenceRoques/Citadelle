package testJoueurs;

import personnages.*;
import carte.*;
import joueurs.Joueur;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

    Personnage Assassin = new Assassin();
    Personnage Voleur = new Voleur();
    Personnage Magicien = new Magicien();
    Personnage Roi = new Roi();
    Personnage Eveque = new Eveque();
    Personnage Marchand = new Marchand();
    Personnage Architecte = new Architecte();
    Personnage Condottiere = new Condottiere();

    Batiment m = new Batiment("maison",5, quartier.COMMERCE);
    Batiment e = new Batiment("eglise",3, quartier.RELIGIEUX);
    Batiment p = new Batiment("port",4, quartier.COMMERCE);
    Batiment t = new Batiment("taverne",2, quartier.COMMERCE);
    Batiment c = new Batiment("chateau",1, quartier.NOBLE);

    Joueur j1 = new Joueur(2, "j1");
    Joueur j2 = new Joueur(10, "j2");
    Joueur j3 = new Joueur(7, "j3");
    Joueur j4 = new Joueur(5, "j4");

    @Test
    void TestGetArgent() {
        assertEquals(2,j1.getArgent());
        assertEquals(10,j2.getArgent());
    }
    @Test
    void TestGetPersonnage() {
        j1.setPersonnage(Assassin.getNumero());
        j2.setPersonnage(Magicien.getNumero());
        assertEquals("Assassin","" + j1.getPersonnage());
        assertEquals("Magicien","" + j2.getPersonnage());
    }

    @Test
    void TestPiocherArgent() {
        assertEquals(2,j1.getArgent());
        j1.piocherArgent(2);
        assertEquals(4,j1.getArgent());
        assertEquals("Le joueur a pioché 2 pièces.", j1.messagePiocheArgent());
    }

    @Test
    void TestSetPersonnage() {
        j1.setPersonnage(Magicien.getNumero());
        assertEquals("Magicien", "" + j1.getPersonnage());
    }

    @Test
    void TestMessagePiocheBatiment(){
        assertEquals("Le joueur a pioché un batiment.", j1.messagePiocheBatiment());
    }

    @Test
    void construireBatiment(){
        j2.piocherBatiment(m);
        assertTrue(j2.construireBatiment(m));
        assertFalse(j2.construireBatiment(t));
    }
    @Test
    void TestGetMainJoueur(){
        ArrayList<Batiment> main1 = new ArrayList<Batiment>();
        assertEquals(main1, j2.getMainJoueur());
        j2.piocherBatiment(m);
        main1.add(m);
        assertEquals(main1, j2.getMainJoueur());
        j2.piocherBatiment(e);
        main1.add(e);
        assertEquals(main1, j2.getMainJoueur());
    }
    @Test
    void TestGetCiteJoueur(){
        ArrayList<Batiment> cite1 = new ArrayList<Batiment>();
        ArrayList<Batiment> main1 = new ArrayList<Batiment>();
        assertEquals(10, j2.getArgent());
        j2.piocherBatiment(m);
        main1.add(m);
        assertEquals(main1, j2.getMainJoueur());
        j2.construireBatiment(m);
        cite1.add(m);
        main1.remove(m);
        assertEquals(main1, j2.getMainJoueur());
        assertEquals(cite1, j2.getCiteJoueur());
        assertEquals(5, j2.getArgent());
    }

    @Test
    void TestAffichageJoueur(){
        j2.piocherBatiment(m);
        j2.piocherBatiment(e);
        j2.piocherBatiment(t);
        j2.construireBatiment(e);
        j2.setPersonnage(Magicien.getNumero());
        assertEquals("Roi appelle Magicien(0). Pour ce tour, il possède 7 pièces, 1 batiments dans sa cité qui sont [eglise] et une main qui est [maison, taverne].", j2.affichageJoueur());
    }

    @Test
    void retireArgent(){
        assertEquals(2,j1.getArgent());
        j1.retireArgent(1);
        assertEquals(1,j1.getArgent());
        j1.retireArgent(1);
        assertEquals(0,j1.getArgent());
    }

    @Test
    void testPopBatiment(){
        assertTrue(j2.getMainJoueur().isEmpty());
        assertNull(j2.popBatiment());
        j2.piocherBatiment(e);
        ArrayList<Batiment> cite1 = new ArrayList<>();
        cite1.add(e);
        assertEquals(cite1, j2.getMainJoueur());
        j2.popBatiment();
        cite1.remove(e);
        assertEquals(cite1, j2.getMainJoueur());
    }

    @Test
    void testResetKilled(){
        j2.isKilled();
        assertTrue(j2.getKilled());
        j2.resetKilled();
        assertFalse(j2.getKilled());
    }

    @Test
    void testEchangeBatiment(){
        assertNull(j3.echangeBatiment(e,-1));
        assertNull(j3.echangeBatiment(e,1));
        j3.piocherBatiment(t);
        j3.piocherBatiment(e);
        assertEquals("eglise", "" + j3.echangeBatiment(e,1));
        assertEquals("taverne", "" + j3.echangeBatiment(t, 0));
    }

    @Test
    void testHostileLevel(){
        assertEquals(0, j1.getHostileLevel());
        j1.setHostileLevel(11);
        assertEquals(0, j1.getHostileLevel());
        j1.setHostileLevel(-1);
        assertEquals(0, j1.getHostileLevel());
        j1.setHostileLevel(10);
        assertEquals(10, j1.getHostileLevel());
        j1.setHostileLevel(3);
        assertEquals(3, j1.getHostileLevel());
    }

    @Test
    void testResetRobbed(){
        j2.isRobbed();
        assertTrue(j2.getRobbed());
        j2.resetRobbed();
        assertFalse(j2.getRobbed());
    }

    @Test
    void testJouerAction(){
        j1.isKilled();
        j1.setPersonnage(6);
        j1.jouerAction(j1,j2,null,null,0);
        assertEquals(2,j1.getArgent());
        j1.jouerAction(j1,j2,null,null,1);
        assertEquals(2,j1.getArgent());
        j1.resetKilled();
        j1.jouerAction(j1,j2,null,null,1);
        assertEquals(3,j1.getArgent());
    }

    @Test
    void testEstPersonnage(){
        j1.setPersonnage(1);
        assertTrue(j1.estPersonnage("Assassin"));
        j1.setPersonnage(2);
        assertTrue(j1.estPersonnage("Voleur"));
        j1.setPersonnage(3);
        assertTrue(j1.estPersonnage("Magicien"));
        j1.setPersonnage(4);
        assertTrue(j1.estPersonnage("Roi"));
        j1.setPersonnage(5);
        assertTrue(j1.estPersonnage("Eveque"));
        j1.setPersonnage(6);
        assertTrue(j1.estPersonnage("Marchand"));
        j1.setPersonnage(7);
        assertTrue(j1.estPersonnage("Architecte"));
        j1.setPersonnage(8);
        assertTrue(j1.estPersonnage("Condottiere"));
    }
}