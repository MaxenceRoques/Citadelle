package testPlateau;

import carte.*;
import joueurs.*;
import plateau.Plateau;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlateauTest {
    Plateau plateau;
    Bot j1;
    Bot j2;
    Bot j3;
    Bot j4;

    @Mock Random mockedRandom = mock(Random.class);

    @BeforeEach
    void setUp() {
        when(mockedRandom.nextInt(0,4)).thenReturn(2);
        plateau = new Plateau(4,mockedRandom);
        j1 = new Bot(200,"j1");
        j2 = new Bot(200,"j2");
        j3 = new Bot(200,"j3");
        j4 = new Bot(200,"j4");
    }

    @Test
    void testInit(){//Test de l'initialisation d'un plateau et vérification des valeurs initiales de ses attributs
        Bot[] listeBots = plateau.getListeJoueurs();
        assertEquals(4,listeBots.length);
        for(int i=0;i<listeBots.length;i++){
            assertEquals(2,listeBots[i].getArgent());
            assertEquals("j"+(i+1),listeBots[i].getNom());
            assertEquals(0,listeBots[i].getJoueur().getHostileLevel());
        }
        assertTrue(plateau.getOrdreDeChoix().isEmpty());
        assertEquals(22,plateau.getBanque());
        assertEquals(0,plateau.getActualKilled());
        assertEquals(0,plateau.getActualStolen());
        assertEquals("",plateau.getMessageRound());
        assertEquals("j3",plateau.getActualKing().getNom());
    }

    @Test
    void testSetter(){//Test des méthode de modification des valeurs des attributs
        assertEquals(22,plateau.getBanque());
        assertEquals(0,plateau.getActualKilled());
        assertEquals(0,plateau.getActualStolen());
        assertEquals(plateau.getListeJoueurs()[2].getNom(),plateau.getActualKing().getNom());
        plateau.setBanque(30);
        plateau.setActualKilled(1);
        plateau.setActualStolen(4);
        plateau.setActualKing(plateau.getListeJoueurs()[2]);
        assertEquals(30,plateau.getBanque());
        assertEquals(1,plateau.getActualKilled());
        assertEquals(4,plateau.getActualStolen());
        assertEquals(plateau.getListeJoueurs()[2].getNom(),plateau.getActualKing().getNom());
    }

    @Test
    void testChoosingOrder(){//Test de la création de l'ordre de choix des personnages par rapport au roi actuel
        for (int i=0; i<100; i++) {
            setUp();
            plateau.choosingOrder();
            assertEquals(plateau.getActualKing(),plateau.getOrdreDeChoix().get(0));
            assertEquals(4,plateau.getOrdreDeChoix().size());
            assertTrue(plateau.getMessageRound().contains("Le roi actuel est : j3\n"));
            assertTrue(plateau.getMessageRound().contains("Les joueurs choisissent leur rôle selon l'ordre suivant: j3, j4, j1, j2"));
        }
    }

    @Test
    void testChoosingCharacter(){//Test de l'attribution d'un personnage à un bot
        Bot bot1 = new Bot(5, "Bot1");
        assertNull(bot1.getRole());
        assertEquals("",plateau.getMessageRound());
        plateau.choosingCharacter(bot1);
        assertEquals("Architecte",bot1.getRole().getNom());
        assertEquals("Le bot Bot1 a choisi le rôle Architecte",plateau.getMessageRound());
    }

    @Test
    void testPersonnageComeBack(){//Test de la desattribution d'un personnage à un bot
        for(Bot b:plateau.getListeJoueurs()){
            plateau.choosingCharacter(b);
        }
        for(Bot b:plateau.getListeJoueurs()){
            assertNotNull(b.getRole());
        }
        plateau.personnageComeBack();
        for(Bot b:plateau.getListeJoueurs()){
            assertNull(b.getRole());
        }
    }

    @Test
    void testIsEndGame() {//Test de la vérification de fin de jeu
        Batiment B1 = new Batiment("Random", 0, quartier.MILITAIRE);
        Batiment B2 = new Batiment("Random2", 0, quartier.MILITAIRE);
        assertFalse(plateau.isEndGame());
        plateau.getListeJoueurs()[0].recoisBatiment(B1);
        plateau.getListeJoueurs()[0].construireBatiment(B1);
        assertFalse(plateau.isEndGame());
        plateau.getListeJoueurs()[0].recoisBatiment(B2);
        plateau.getListeJoueurs()[0].construireBatiment(B2);
        plateau.getListeJoueurs()[0].recoisBatiment(B1);
        plateau.getListeJoueurs()[0].construireBatiment(B1);
        assertFalse(plateau.isEndGame());
        plateau.getListeJoueurs()[0].recoisBatiment(B2);
        plateau.getListeJoueurs()[0].construireBatiment(B2);
        plateau.getListeJoueurs()[0].recoisBatiment(B1);
        plateau.getListeJoueurs()[0].construireBatiment(B1);
        assertFalse(plateau.isEndGame());
        plateau.getListeJoueurs()[0].recoisBatiment(B2);
        plateau.getListeJoueurs()[0].construireBatiment(B2);
        plateau.getListeJoueurs()[0].recoisBatiment(B1);
        plateau.getListeJoueurs()[0].construireBatiment(B1);
        assertTrue(plateau.isEndGame());//Fin du jeu
    }

    @Test
    void testEnsembleBatiment(){//Test du calcul du score par rapport aux bâtiments construit au sein de la cité d'un bot
        Batiment taverne = new Batiment("Taverne", 1, quartier.COMMERCE);
        Batiment temple = new Batiment("Temple", 1, quartier.RELIGIEUX);
        Batiment chateau = new Batiment("Chateau", 4, quartier.NOBLE);
        Batiment caserne = new Batiment("Caserne", 3, quartier.MILITAIRE);
        Batiment donjon = new Batiment("Donjon", 3, quartier.MERVEILLE);


        assertEquals(0,plateau.scoreEnsembleBatiment(j1));
        j1.recoisBatiment(taverne);
        j1.construireBatiment(j1.getMain().get(0));
        assertEquals(1,plateau.scoreEnsembleBatiment(j1));
        j1.recoisBatiment(temple);
        j1.construireBatiment(j1.getMain().get(0));
        assertEquals(2,plateau.scoreEnsembleBatiment(j1));
        j1.recoisBatiment(chateau);
        j1.construireBatiment(j1.getMain().get(0));
        assertEquals(6,plateau.scoreEnsembleBatiment(j1));
        j1.recoisBatiment(caserne);
        j1.construireBatiment(j1.getMain().get(0));
        assertEquals(9,plateau.scoreEnsembleBatiment(j1));
        j1.recoisBatiment(donjon);
        j1.construireBatiment(j1.getMain().get(0));
        assertEquals(12,plateau.scoreEnsembleBatiment(j1));
    }

    @Test
    void testScoreAvecMerveilles(){//Test du calcul du score ajouté par les effets des merveilles construites dans la cité d'un bot
        Batiment comptoir = new Batiment("Comptoir", 3, quartier.COMMERCE);
        Batiment dracoport = new Batiment("Dracoport", 6, quartier.MERVEILLE);
        Batiment universite = new Batiment("Universite", 6, quartier.MERVEILLE);
        Batiment salleCartes = new Batiment("Salle des Cartes", 5, quartier.MERVEILLE);
        Batiment tresorImperial = new Batiment("Tresor Imperial", 5, quartier.MERVEILLE);

        assertEquals(0,plateau.scoreMerveille(j1));
        j1.recoisBatiment(dracoport);
        j1.construireBatiment(j1.getMain().get(0));
        assertEquals(2,plateau.scoreMerveille(j1));//score += coût Dracoport + 2
        j1.recoisBatiment(universite);
        j1.construireBatiment(j1.getMain().get(0));
        assertEquals(4,plateau.scoreMerveille(j1));//score += coût Université + 2
        j1.recoisBatiment(tresorImperial);
        j1.construireBatiment(j1.getMain().get(0));
        assertEquals(187,plateau.scoreMerveille(j1));//score += argent du bot
        j1.recoisBatiment(salleCartes);
        j1.construireBatiment(j1.getMain().get(0));
        for (int i=0; i<5; i++) {
            j1.recoisBatiment(comptoir);
        }
        assertEquals(187,plateau.scoreMerveille(j1));//score += nombre de carte dans la main du bot
    }

    @Test
    void testCinqTypeBatiment(){//Test du calcul du score si un bot a construit tout les types de bâtiments
        Batiment taverne = new Batiment("Taverne", 1, quartier.COMMERCE);
        Batiment temple = new Batiment("Temple", 1, quartier.RELIGIEUX);
        Batiment chateau = new Batiment("Chateau", 4, quartier.NOBLE);
        Batiment caserne = new Batiment("Caserne", 3, quartier.MILITAIRE);
        Batiment donjon = new Batiment("Donjon", 3, quartier.MERVEILLE);
        Batiment courMiracles = new Batiment("Cour des Miracles", 2, quartier.MERVEILLE);

        assertFalse(plateau.scoreCinqTypeBatiment(j1));
        j1.recoisBatiment(taverne);
        j1.construireBatiment(j1.getMain().get(0));
        assertFalse(plateau.scoreCinqTypeBatiment(j1));
        j1.recoisBatiment(temple);
        j1.construireBatiment(j1.getMain().get(0));
        assertFalse(plateau.scoreCinqTypeBatiment(j1));
        j1.recoisBatiment(chateau);
        j1.construireBatiment(j1.getMain().get(0));
        assertFalse(plateau.scoreCinqTypeBatiment(j1));
        j1.recoisBatiment(caserne);
        j1.construireBatiment(j1.getMain().get(0));
        assertFalse(plateau.scoreCinqTypeBatiment(j1));
        j1.recoisBatiment(donjon);
        j1.construireBatiment(j1.getMain().get(0));
        assertTrue(plateau.scoreCinqTypeBatiment(j1));

        //cas de la cour des miracles
        j1.getCite().remove(taverne);
        j1.recoisBatiment(courMiracles);
        j1.construireBatiment(j1.getMain().get(0));
        assertTrue(plateau.scoreCinqTypeBatiment(j1));
        j1.getCite().remove(temple);
        j1.recoisBatiment(taverne);
        j1.construireBatiment(j1.getMain().get(0));
        assertTrue(plateau.scoreCinqTypeBatiment(j1));
        j1.getCite().remove(chateau);
        j1.recoisBatiment(temple);
        j1.construireBatiment(j1.getMain().get(0));
        assertTrue(plateau.scoreCinqTypeBatiment(j1));
        j1.getCite().remove(caserne);
        j1.recoisBatiment(chateau);
        j1.construireBatiment(j1.getMain().get(0));
        assertTrue(plateau.scoreCinqTypeBatiment(j1));
        j1.getCite().remove(donjon);
        assertFalse(plateau.scoreCinqTypeBatiment(j1));
        j1.recoisBatiment(caserne);
        j1.construireBatiment(j1.getMain().get(0));
        assertTrue(plateau.scoreCinqTypeBatiment(j1));
    }

    @Test
    void testEgalite() {
        Plateau plate = new Plateau(4,new Random());
        Batiment dracoport = new Batiment("Dracoport", 6, quartier.MERVEILLE);

        plate.getListeJoueurs()[0].setPersonnage(4);
        plate.getListeJoueurs()[1].setPersonnage(3);
        plate.getListeJoueurs()[2].setPersonnage(8);
        plate.getListeJoueurs()[3].setPersonnage(7);

        for (int i=0; i<7; i++) {
            for (int j=0; j<4; j++) {
                plate.getListeJoueurs()[j].recoisBatiment(dracoport);
                plate.getListeJoueurs()[j].construireBatiment(dracoport);
            }
        }
        assertEquals("j3",plate.score(true).getJoueur().getNom());
    }

    ////////////////// à faire pas de s-out
    @Test
    void testCimetiere() {
        Plateau plate = new Plateau(2,new Random());
        Batiment cimetiere = new Batiment("Cimetiere", 5, quartier.MERVEILLE);
        Batiment taverne = new Batiment("Taverne", 1, quartier.COMMERCE);

        plate.getListeJoueurs()[0].recoisBatiment(cimetiere);
        plate.getListeJoueurs()[0].recoisBatiment(taverne);
        plate.getListeJoueurs()[0].getJoueur().piocherArgent(4);
        plate.getListeJoueurs()[0].construireBatiment(cimetiere);
        plate.getListeJoueurs()[0].construireBatiment(taverne);

        for (int i=0; i<6; i++) {
            plate.getListeJoueurs()[1].recoisBatiment(taverne);
            plate.getListeJoueurs()[1].getJoueur().piocherArgent(1);
            plate.getListeJoueurs()[1].construireBatiment(taverne);
        }

        assertEquals(0, plate.getListeJoueurs()[0].getMain().size());
        assertEquals(2, plate.getListeJoueurs()[0].getTailleCite());
    }

    @Test
    void testScoreEquivalent(){
        Plateau plateau = new Plateau(2, new Random());
        Batiment bibliotheque = new Batiment("Bibliotheque", 6, quartier.MERVEILLE);
        Batiment dracoport = new Batiment("Dracoport", 6, quartier.MERVEILLE);

        Batiment caserne = new Batiment("Caserne", 3, quartier.MILITAIRE);
        Batiment forteresse = new Batiment("Forteresse", 5, quartier.MILITAIRE);
        Batiment prison = new Batiment("Prison", 2, quartier.MILITAIRE);
        Batiment port = new Batiment("Port", 4, quartier.COMMERCE);

        plateau.getListeJoueurs()[0].getJoueur().piocherArgent(100);
        plateau.getListeJoueurs()[1].getJoueur().piocherArgent(100);

        plateau.getListeJoueurs()[0].getJoueur().setPersonnage(1);
        plateau.getListeJoueurs()[1].getJoueur().setPersonnage(7);

        plateau.getListeJoueurs()[0].recoisBatiment(bibliotheque);
        plateau.getListeJoueurs()[0].recoisBatiment(dracoport);
        plateau.getListeJoueurs()[0].construireBatiment(bibliotheque);
        plateau.getListeJoueurs()[0].construireBatiment(dracoport);

        plateau.getListeJoueurs()[1].recoisBatiment(caserne);
        plateau.getListeJoueurs()[1].recoisBatiment(forteresse);
        plateau.getListeJoueurs()[1].recoisBatiment(prison);
        plateau.getListeJoueurs()[1].recoisBatiment(port);
        plateau.getListeJoueurs()[1].construireBatiment(caserne);
        plateau.getListeJoueurs()[1].construireBatiment(forteresse);
        plateau.getListeJoueurs()[1].construireBatiment(prison);
        plateau.getListeJoueurs()[1].construireBatiment(port);

        assertEquals("j2",plateau.score(true).getNom());
        assertEquals("Les scores sont de :\n14 pour le joueur j1,\n14 pour le joueur j2\n\nLe joueur j2 remporte la victoire !",plateau.getMessageRound());
    }
}