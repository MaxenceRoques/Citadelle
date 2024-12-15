package testPersonnage;

import carte.*;
import joueurs.*;
import personnages.Assassin;
import personnages.Condottiere;
import personnages.Personnage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CondottiereTest {

    Condottiere condottiere;

    @BeforeEach
    void setUp() {
        condottiere = new Condottiere();
    }

    @Test
    void actionDestructionBatimentFortCout() {
        Joueur j1 = new Joueur( 8,"Kim Jong Un");
        Joueur j2 = new Joueur(50,"j2");
        Deck deck = new Deck();
        Batiment batTest1 = new Batiment("BatTest1", 2, quartier.NOBLE);
        Batiment batTest2 = new Batiment("BatTest2", 2, quartier.NOBLE);
        Batiment batTest3 = new Batiment("BatTest3", 3, quartier.NOBLE);
        Batiment batTest4 = new Batiment("BatTest4", 4, quartier.NOBLE);
        Batiment batTest5 = new Batiment("BatTest5", 5, quartier.NOBLE);
        Batiment batTest6 = new Batiment("BatTest6", 6, quartier.NOBLE);
        Batiment dojon = new Batiment("Donjon", 3, quartier.MERVEILLE);

        j1.setPersonnage(8);
        j2.setPersonnage(1);
        j1.setHostileLevel(10);

        j2.piocherBatiment(batTest1);
        j2.construireBatiment(batTest1);
        j2.piocherBatiment(batTest3);
        j2.construireBatiment(batTest3);
        j2.piocherBatiment(batTest4);
        j2.construireBatiment(batTest4);
        j2.piocherBatiment(batTest5);
        j2.construireBatiment(batTest5);
        j2.piocherBatiment(batTest2);
        j2.construireBatiment(batTest2);
        j2.piocherBatiment(batTest6);
        j2.construireBatiment(batTest6);

        int tailleCite = j2.getCiteJoueur().size();
        j1.jouerAction(j1,j2,deck,null,1);
        assertEquals(tailleCite - 1, j2.getCiteJoueur().size());
        assertEquals(3, j1.getArgent());

        j2.piocherBatiment(dojon);
        j2.construireBatiment(dojon);
        j2.piocherBatiment(new Batiment("batTestPasCher",1,quartier.NOBLE));
        j2.construireBatiment(j2.getMainJoueur().get(0));
        j1.setHostileLevel(8);
        j1.retireArgent(2);
        j1.jouerAction(j1,j2,deck,null,1);
    }

    @Test
    void actionRecuperationArgent() {
        Joueur j1 = new Joueur( 8,"Kim Jong Un");
        Joueur j2 = new Joueur(50,"j2");
        Deck deck = new Deck();
        Batiment batTest1 = new Batiment("BatTest1", 2, quartier.MILITAIRE);
        Batiment ecoleDeMagie = new Batiment("Ecole de Magie", 2, quartier.MERVEILLE);
        j1.piocherBatiment(batTest1);
        j1.construireBatiment(batTest1);
        j1.piocherBatiment(ecoleDeMagie);
        j1.construireBatiment(ecoleDeMagie);
        j1.setPersonnage(8);
        assertEquals(2, condottiere.action(j1, j2, deck, null, 0));
    }

    @Test
    void actionDestructionBatimentFaibleCout() {
        Joueur j1 = new Joueur( 8,"Kim Jong Un");
        Joueur j2 = new Joueur(50,"j2");
        Deck deck = new Deck();
        Batiment batTest1 = new Batiment("BatTest1", 2, quartier.NOBLE);
        Batiment batTest2 = new Batiment("BatTest2", 3, quartier.NOBLE);

        j1.setPersonnage(8);
        j2.setPersonnage(1);
        j1.setHostileLevel(8);

        j2.piocherBatiment(batTest1);
        j2.construireBatiment(batTest1);
        j2.piocherBatiment(batTest2);
        j2.construireBatiment(batTest2);

        int tailleCite = j2.getCiteJoueur().size();
        j1.jouerAction(j1,j2,deck,null,1);
        assertEquals(tailleCite - 1, j2.getCiteJoueur().size());

        assertEquals(7, j1.getArgent());
    }

    @Test
    void actionDestructionBatimentAvecGrandeMuraille() {
        Joueur j1 = new Joueur( 8,"Kim Jong Un");
        Joueur j2 = new Joueur(50,"j2");
        Deck deck = new Deck();
        Batiment batTest1 = new Batiment("BatTest1", 2, quartier.NOBLE);
        Batiment batTest2 = new Batiment("BatTest2", 3, quartier.NOBLE);
        Batiment GrandeMuraille = new Batiment("Grande Muraille", 3, quartier.MERVEILLE);

        j1.setPersonnage(8);
        j2.setPersonnage(1);
        j1.setHostileLevel(8);

        j2.piocherBatiment(batTest1);
        j2.construireBatiment(batTest1);
        j2.piocherBatiment(batTest2);
        j2.construireBatiment(batTest2);
        j2.piocherBatiment(GrandeMuraille);
        j2.construireBatiment(GrandeMuraille);

        int tailleCite = j2.getCiteJoueur().size();
        j1.jouerAction(j1,j2,deck,null,1);

        assertEquals(tailleCite - 1, j2.getCiteJoueur().size());
        assertEquals(6, j1.getArgent());
    }

    @Test
    void message() {
        assertEquals("Condottiere cible Assassin", condottiere.message("Assassin", 1));

        Joueur j1 = new Joueur( 8,"Kim Jong Un");
        Joueur j2 = new Joueur(50,"j2");
        Deck deck = new Deck();
        Batiment batTest1 = new Batiment("BatTest1", 2, quartier.NOBLE);
        Batiment batTest2 = new Batiment("BatTest2", 3, quartier.NOBLE);
        j1.setPersonnage(8);
        j2.setPersonnage(1);
        j1.setHostileLevel(8);
        j2.piocherBatiment(batTest1);
        j2.construireBatiment(batTest1);
        j2.piocherBatiment(batTest2);
        j2.construireBatiment(batTest2);
        assertEquals(2,j2.getCiteJoueur().size());
        condottiere.action(j1,j2,deck,null,1);
        assertEquals("Condottiere cible Assassin\n" +  "Condottiere a attaqué Assassin et a détruit BatTest1", condottiere.message("Assassin", 1));
        assertEquals(1,j2.getCiteJoueur().size());
    }

    @Test
    void testBatimentDetruit() {
        assertEquals("", condottiere.getBatimentDetruit());

        Bot b1 = new Bot(200,"j1");
        Bot b2 = new Bot(20,"j2");

        b2.setPersonnage(4);

        assertEquals(0, b2.getTailleCite());
        b2.recoisBatiment(new Batiment("Taverne", 1, quartier.COMMERCE));
        b2.construireBatiment(b2.getMain().get(0));
        assertEquals(1, b2.getTailleCite());
        assertEquals(0,b1.getJoueur().getHostileLevel());

        assertEquals(0,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals("Taverne", condottiere.getBatimentDetruit());
        assertEquals(0, b2.getTailleCite());

        b2.recoisBatiment(new Batiment("Monastere", 3, quartier.RELIGIEUX));
        b2.construireBatiment(b2.getMain().get(0));
        assertEquals(1, b2.getTailleCite());
        assertEquals(0,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals(1, b2.getTailleCite());

        b1.getJoueur().setHostileLevel(8);
        assertEquals(-2,condottiere.action(b1.getJoueur(), b2.getJoueur(),new Deck(),null,1));
        assertEquals("Monastere", condottiere.getBatimentDetruit());
        assertEquals(0, b2.getTailleCite());

        b2.recoisBatiment(new Batiment("Marche",5,quartier.COMMERCE));
        b2.construireBatiment(b2.getMain().get(0));
        assertEquals(1,b2.getTailleCite());
        assertEquals(0,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals(1,b2.getTailleCite());

        b1.getJoueur().setHostileLevel(9);
        assertEquals(-4,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals("Marche", condottiere.getBatimentDetruit());
        assertEquals(0,b2.getTailleCite());

        b2.recoisBatiment(new Batiment("Universite", 6, quartier.MERVEILLE));
        b2.construireBatiment(b2.getMain().get(0));
        assertEquals(1,b2.getTailleCite());
        assertEquals(0,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals(1,b2.getTailleCite());

        b1.getJoueur().setHostileLevel(10);
        assertEquals(-5,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals("Universite", condottiere.getBatimentDetruit());
        assertEquals(0,b2.getTailleCite());

        b2.recoisBatiment(new Batiment("Donjon", 3, quartier.MERVEILLE));
        b2.construireBatiment(b2.getMain().get(0));
        assertEquals(1,b2.getTailleCite());
        assertEquals(0,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals(1,b2.getTailleCite());

        b2.recoisBatiment(new Batiment("Grande Muraille", 6, quartier.MERVEILLE));
        b2.recoisBatiment(new Batiment("Monastere", 3, quartier.RELIGIEUX));
        b2.construireBatiment(b2.getMain().get(0));
        b2.construireBatiment(b2.getMain().get(0));
        b1.getJoueur().setHostileLevel(8);
        assertEquals(3,b2.getTailleCite());
        assertEquals(0,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals(3,b2.getTailleCite());

        b1.getJoueur().setHostileLevel(9);
        assertEquals(3,b2.getTailleCite());
        assertEquals(-3,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals(2,b2.getTailleCite());

        b1.getJoueur().retireArgent(198);
        b2.recoisBatiment(new Batiment("Monastere", 3, quartier.RELIGIEUX));
        b2.construireBatiment(b2.getMain().get(0));
        assertEquals(3,b2.getTailleCite());
        assertEquals(0,condottiere.action(b1.getJoueur(),b2.getJoueur(),new Deck(),null,1));
        assertEquals(3,b2.getTailleCite());
    }
}