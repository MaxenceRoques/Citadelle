package testJoueurs;

import personnages.*;
import carte.*;
import joueurs.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BotTest {

    Personnage Assassin = new Assassin();
    Personnage Voleur = new Voleur();
    Personnage Magicien = new Magicien();
    Personnage Roi = new Roi();
    Personnage Eveque = new Eveque();
    Personnage Marchand = new Marchand();
    Personnage Architecte = new Architecte();
    Personnage Condottiere = new Condottiere();

    private final ArrayList<Personnage> listePersonnage = new ArrayList<>();

    Batiment hdv = new Batiment("hotel de ville",5, quartier.COMMERCE);
    Batiment eglise = new Batiment("eglise",3, quartier.RELIGIEUX);
    Batiment port = new Batiment("port",4, quartier.COMMERCE);
    Batiment taverne = new Batiment("taverne",2, quartier.COMMERCE);
    Batiment chateau = new Batiment("chateau",1, quartier.NOBLE);
    Batiment forge = new Batiment("Forge", 5, quartier.MERVEILLE);
    Batiment caserne = new Batiment("Caserne", 3, quartier.MILITAIRE);
    Batiment temple = new Batiment("Temple", 1, quartier.RELIGIEUX);
    Batiment random_cher = new Batiment("random_cher", 100, quartier.NOBLE);

    Bot b1 = new Bot(5, "j1");
    Bot b2 = new Bot(7, "j2");
    Bot b3 = new Bot(0, "j3");
    Bot b4 = new Bot(1, "j4");

    Bot voleur = new Bot(0,"j5");

    Bot assassin = new Bot(2,"j6");

    Deck deck = new Deck();

    Bot[] listeJoueurs = new Bot[6];

    @Mock Deck mockedDeck = mock(Deck.class);

    @BeforeEach
    void setUp(){
        b1.recoisBatiment(hdv);
        b1.recoisBatiment(port);
        b1.recoisBatiment(taverne);
        b1.recoisBatiment(forge);

        b2.recoisBatiment(hdv);
        b2.recoisBatiment(chateau);

        b3.recoisBatiment(hdv);
        b3.recoisBatiment(eglise);
        b3.recoisBatiment(port);

        b4.recoisBatiment(hdv);

        b1.setPersonnage(Eveque.getNumero());
        b2.setPersonnage(Marchand.getNumero());
        b3.setPersonnage(Magicien.getNumero());
        b4.setPersonnage(Roi.getNumero());
        voleur.setPersonnage(Voleur.getNumero());
        assassin.setPersonnage(Assassin.getNumero());

        listeJoueurs[0]=b1;
        listeJoueurs[1]=b2;
        listeJoueurs[2]=b3;
        listeJoueurs[3]=b4;
        listeJoueurs[4]=voleur;
        listeJoueurs[5]=assassin;

        listePersonnage.add(Assassin);
        listePersonnage.add(Voleur);
        listePersonnage.add(Magicien);
        listePersonnage.add(Roi);
        listePersonnage.add(Eveque);
        listePersonnage.add(Marchand);
        listePersonnage.add(Architecte);
        listePersonnage.add(Condottiere);
    }

    @Test
    void testInit(){//Test initialisation
        Bot b = new Bot(4,"Grindur");
        assertNull(b.getRole());
        assertEquals(4,b.getArgent());
        assertEquals("Grindur",b.getNom());
        assertTrue(b.getMain().isEmpty());
        assertEquals(0,b.getTailleCite());
        assertTrue(b.getCite().isEmpty());
        assertEquals("",b.getMessageRound());
        assertFalse(b.getJoueur().getDetruitBatiment());
        Joueur j = b.getJoueur();
        assertEquals(4,j.getArgent());
        assertEquals("Grindur",j.getNom());
    }

    @Test
    void testConstruction(){//Test des capacités de construction des bots
        assertTrue(b1.getMain().contains(hdv));
        assertEquals(5,b1.getArgent());
        deck.getDeck().clear();
        deck.jette(random_cher);
        deck.jette(random_cher);
        b1.jouerSonTour(listeJoueurs,deck);
        assertFalse(b1.getMain().contains(hdv));
        assertEquals(2,b1.getArgent());
    }

    @Test//Test de récupération du rôle
    void testGetRole(){
        assertEquals("Eveque", "" + b1.getRole());
    }

    @Test
    void testAddMessage() {//Test de l'ajout de message dans le message d'un bot
        assertEquals("", b1.getMessageRound());
        b1.addMessage("Je suis une chèvre");
        assertEquals("Je suis une chèvre\n", b1.getMessageRound());
        b1.addMessage("En fait non c'était un prank");
        assertEquals("Je suis une chèvre\nEn fait non c'était un prank\n", b1.getMessageRound());
    }

    @Test
    void testSetPersonnage(){//Test d'attribution d'un personnage à un bot
        Bot b = new Bot(2,"Grindur");
        assertNull(b.getRole());
        b.setPersonnage(4);
        assertEquals("Roi",b.getRole().getNom());
        assertEquals(4,b.getRole().getNumero());
    }

    @Test
    void testRetirePerso(){//Test d'abandon de personnage
        assertNotNull(b1.getRole());
        assertEquals("Eveque",b1.getRole().getNom());
        assertEquals(5,b1.getRole().getNumero());
        Personnage p = b1.retirePersonnage();
        assertNull(b1.getRole());
        assertEquals("Eveque",p.getNom());
        assertEquals(5,p.getNumero());
    }

    @Test
    void testRecoisBatiment(){//Test de réception d'un bâtiment
        assertFalse(b2.getMain().contains(taverne));
        b2.recoisBatiment(taverne);
        assertTrue(b2.getMain().contains(taverne));
    }

    @Test
    void testJouerVol(){//Test de jeu d'un bot volé (retrait d'argent)
        assertEquals(5,b1.getArgent());
        assertEquals(0,voleur.getArgent());
        ArrayList<Integer> cibles = new ArrayList<>();
        cibles.add(5);
        voleur.setCiblesDispo(cibles);
        voleur.jouerAssassinVoleur(listeJoueurs);
        assertTrue(b1.getJoueur().getRobbed());//b1 a été volé
        assertEquals(5,b1.getArgent());
        assertEquals(0,voleur.getArgent());
        b1.jouerVol(listeJoueurs);
        assertFalse(b1.getJoueur().getRobbed());
        assertEquals(0,b1.getArgent());
        assertEquals(5,voleur.getArgent());
    }

    @Test
    void testJouerPiocheCarte(){//Test de piochage de carte sous différents cas
        //cas classique
        when(mockedDeck.pioche()).thenReturn(taverne);
        Bot bot1 = new Bot(100,"Grindur");
        assertTrue(bot1.getMain().isEmpty());
        assertEquals("",bot1.getMessageRound());
        bot1.jouerPiocherCarte(mockedDeck);
        assertTrue(bot1.getMain().contains(taverne));
        assertEquals(1,bot1.getMain().size());
        assertEquals("Le joueur a pioché un batiment.\n",bot1.getMessageRound());

        //cas bibliothèque (garde les deux cartes piochées)
        Batiment bibliotheque = new Batiment("Bibliotheque", 6, quartier.MERVEILLE);
        bot1.recoisBatiment(bibliotheque);
        bot1.construireBatiment(bibliotheque);
        bot1.jouerPiocherCarte(mockedDeck);
        assertEquals(3,bot1.getMain().size());
        assertTrue(bot1.getMessageRound().contains("Le joueur a pioché deux bâtiments grâce à la bibliothèque.\n"));
        bot1.getCite().remove(bibliotheque);

        //cas observatoire (pioche 3 cartes au lieu de 2)
        when(mockedDeck.pioche()).thenReturn(taverne,port,eglise);
        bot1.construireBatiment(taverne);
        Batiment observatoire = new Batiment("Observatoire", 4, quartier.MERVEILLE);
        bot1.recoisBatiment(observatoire);
        bot1.construireBatiment(observatoire);
        bot1.jouerPiocherCarte(mockedDeck);
        assertEquals(3,bot1.getMain().size());
        assertTrue(bot1.getMain().contains(port));
        assertTrue(bot1.getMessageRound().contains("Le joueur a utilise l'Observatoire et pioche un troisième choix.\n"));

        //cas bibliothèque + observatoire
        bot1.recoisBatiment(bibliotheque);
        bot1.construireBatiment(bibliotheque);
        bot1.jouerPiocherCarte(mockedDeck);
        assertEquals(6,bot1.getMain().size());
        assertTrue(bot1.getMain().contains(eglise));
        assertTrue(bot1.getMessageRound().contains("Le joueur a utilise l'Observatoire et pioche un troisième choix.\nLe joueur a pioché trois bâtiments grâce à la bibliothèque.\n"));
    }

    @Test
    void testJouerSonTour(){//Test du tour de jeu des bots
        when(mockedDeck.pioche()).thenReturn(caserne);
        Bot architecte = new Bot(4,"Grindur");
        Bot condottiere = new Bot(1,"Aussi Grindur");
        architecte.setPersonnage(7);
        condottiere.setPersonnage(8);
        Bot[] adversaires = new Bot[8];
        for(int i=0;i<listeJoueurs.length;i++){adversaires[i]=listeJoueurs[i];}
        adversaires[6]=architecte;
        adversaires[7]=condottiere;
        ArrayList<Integer> cibles = new ArrayList<>();
        for(int i=1;i<9;i++){cibles.add(i);}

        //Tour d'un assassin : il tue le condottiere et pioche un bâtiment
        assassin.setCiblesDispo(cibles);
        assertEquals("",assassin.getMessageRound());
        assertEquals(0,assassin.getMain().size());
        assertEquals(0,assassin.getCite().size());
        assertEquals(2,assassin.getArgent());
        assertFalse(condottiere.getJoueur().getKilled());
        assassin.jouerSonTour(adversaires,mockedDeck);
        assertEquals(0,assassin.getCite().size());
        assertTrue(assassin.getMain().contains(caserne));
        assertEquals(2,assassin.getArgent());
        assertTrue(condottiere.getJoueur().getKilled());
        assertTrue(assassin.getMessageRound().contains("(j6)Roi appelle Assassin(0). Pour ce tour, il possède 2 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [].\n"));
        assertTrue(assassin.getMessageRound().contains("Le joueur a pioché un batiment.\n"));
        assertTrue(assassin.getMessageRound().contains("Assassin a tué Condottiere\n"));
        assertTrue(assassin.getMessageRound().contains("Roi appelle Assassin(0). Pour ce tour, il possède 2 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [Caserne].\n"));

        //Tour d'un voleur : il vole l'architecte, pioche un bâtiment et le construit
        voleur.setCiblesDispo(cibles);
        voleur.getJoueur().piocherArgent(5);
        assertEquals("",voleur.getMessageRound());
        assertEquals(0,voleur.getMain().size());
        assertEquals(0,voleur.getCite().size());
        assertEquals(5,voleur.getArgent());
        assertFalse(architecte.getJoueur().getRobbed());
        voleur.jouerSonTour(adversaires,mockedDeck);
        assertEquals(0,voleur.getMain().size());
        assertTrue(voleur.getCite().contains(caserne));
        assertEquals(2,voleur.getArgent());
        assertTrue(architecte.getJoueur().getRobbed());
        assertTrue(voleur.getMessageRound().contains("(j5)Roi appelle Voleur(0). Pour ce tour, il possède 5 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [].\n"));
        assertTrue(voleur.getMessageRound().contains("Le joueur a pioché un batiment.\n"));
        assertTrue(voleur.getMessageRound().contains("Voleur a volé Architecte\n"));
        assertTrue(voleur.getMessageRound().contains("La carte Caserne a été construite.\n"));
        assertTrue(voleur.getMessageRound().contains("Roi appelle Voleur(0). Pour ce tour, il possède 2 pièces, 1 batiments dans sa cité qui sont [Caserne] et une main qui est [].\n"));

        //Tour d'un magicien : il échange ses cartes avec l'éveque, pioche de l'argent et construit un bâtiment
        b3.setCiblesDispo(cibles);
        ArrayList<Batiment> mainOriginale = new ArrayList<>(b3.getMain());
        ArrayList<Batiment> mainEchangee = new ArrayList<>(b1.getMain());
        assertEquals("",b3.getMessageRound());
        assertEquals(3,b3.getMain().size());
        assertEquals(0,b3.getCite().size());
        assertEquals(0,b3.getArgent());
        assertEquals(mainOriginale,b3.getMain());
        assertEquals(mainEchangee,b1.getMain());
        b3.jouerSonTour(adversaires,mockedDeck);
        assertEquals(3,b3.getMain().size());
        assertTrue(b3.getCite().contains(taverne));
        assertEquals(0,b3.getArgent());
        assertEquals(mainOriginale,b1.getMain());
        mainEchangee.remove(taverne);
        assertEquals(mainEchangee,b3.getMain());
        assertTrue(b3.getMessageRound().contains("(j3)Roi appelle Magicien(0). Pour ce tour, il possède 0 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [hotel de ville, eglise, port].\n"));
        assertTrue(b3.getMessageRound().contains("Le joueur a pioché 2 pièces.\n"));
        assertTrue(b3.getMessageRound().contains("Magicien a échangé ses cartes avec Eveque\n"));
        assertTrue(b3.getMessageRound().contains("La carte taverne a été construite.\n"));
        assertTrue(b3.getMessageRound().contains("Roi appelle Magicien(0). Pour ce tour, il possède 0 pièces, 1 batiments dans sa cité qui sont [taverne] et une main qui est [hotel de ville, port, Forge].\n"));

        //Tour d'un roi : il pioche un bâtiment
        assertEquals("",b4.getMessageRound());
        assertEquals(1,b4.getMain().size());
        assertEquals(0,b4.getCite().size());
        assertEquals(1,b4.getArgent());
        b4.jouerSonTour(adversaires,mockedDeck);
        assertEquals(2,b4.getMain().size());
        assertEquals(0,b4.getCite().size());
        assertEquals(1,b4.getArgent());
        assertTrue(b4.getMessageRound().contains("(j4)Roi appelle Roi(0). Pour ce tour, il possède 1 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [hotel de ville].\n"));
        assertTrue(b4.getMessageRound().contains("Le joueur a pioché un batiment.\n"));
        assertTrue(b4.getMessageRound().contains("Roi appelle Roi(0). Pour ce tour, il possède 1 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [hotel de ville, Caserne].\n"));

        //Tour d'un éveque : il pioche un bâtiment et en construit un
        b1.setCiblesDispo(cibles);
        assertEquals("",b1.getMessageRound());
        assertEquals(3,b1.getMain().size());
        assertEquals(0,b1.getCite().size());
        assertEquals(5,b1.getArgent());
        b1.jouerSonTour(adversaires,mockedDeck);
        assertEquals(3,b1.getMain().size());
        assertTrue(b1.getCite().contains(hdv));
        assertTrue(b1.getMain().contains(caserne));
        assertEquals(0,b1.getArgent());
        assertTrue(b1.getMessageRound().contains("(j1)Roi appelle Eveque(0). Pour ce tour, il possède 5 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [hotel de ville, eglise, port].\n"));
        assertTrue(b1.getMessageRound().contains("Le joueur a pioché un batiment.\n"));
        assertTrue(b1.getMessageRound().contains("La carte hotel de ville a été construite.\n"));
        assertTrue(b1.getMessageRound().contains("Roi appelle Eveque(0). Pour ce tour, il possède 0 pièces, 1 batiments dans sa cité qui sont [hotel de ville] et une main qui est [eglise, port, Caserne].\n"));

        //Tour d'un marchand : il pioche un bâtiment, récupère sa pièce de rôle et construit un b^timent
        assertEquals("",b2.getMessageRound());
        assertEquals(2,b2.getMain().size());
        assertEquals(0,b2.getCite().size());
        assertEquals(7,b2.getArgent());
        b2.jouerSonTour(adversaires,mockedDeck);
        assertEquals(2,b2.getMain().size());
        assertTrue(b2.getMain().contains(caserne));
        assertTrue(b2.getCite().contains(hdv));
        assertEquals(4,b2.getArgent());
        assertTrue(b2.getMessageRound().contains("(j2)Roi appelle Marchand(0). Pour ce tour, il possède 7 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [hotel de ville, chateau].\n"));
        assertTrue(b2.getMessageRound().contains("Le joueur a pioché un batiment.\n"));
        assertTrue(b2.getMessageRound().contains("La carte hotel de ville a été construite.\n"));
        assertTrue(b2.getMessageRound().contains("Roi appelle Marchand(0). Pour ce tour, il possède 4 pièces, 1 batiments dans sa cité qui sont [hotel de ville] et une main qui est [chateau, Caserne].\n"));

        //Tour d'un volé : il pert son argent et le donne au voleur
        //Tour d'un architecte : il récupère 2 bâtiments, pioche de l'argent et construit 2 bâtiments
        when(mockedDeck.pioche()).thenReturn(temple,chateau);
        architecte.getJoueur().piocherBatiment(taverne);
        assertEquals("",architecte.getMessageRound());
        assertEquals(1,architecte.getMain().size());
        assertEquals(0,architecte.getCite().size());
        assertEquals(4,architecte.getArgent());
        assertTrue(architecte.getJoueur().getRobbed());
        architecte.jouerSonTour(adversaires,mockedDeck);
        assertEquals(1,architecte.getMain().size());
        assertEquals(2,architecte.getCite().size());
        assertTrue(architecte.getCite().contains(chateau));
        assertTrue(architecte.getCite().contains(temple));
        assertEquals(0,architecte.getArgent());
        assertFalse(architecte.getJoueur().getRobbed());
        assertEquals(6,voleur.getArgent());
        assertTrue(architecte.getMessageRound().contains("Architecte a été volé\n"));
        assertTrue(architecte.getMessageRound().contains("(Grindur)Roi appelle Architecte(0). Pour ce tour, il possède 0 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [taverne].\n"));
        assertTrue(architecte.getMessageRound().contains("Le joueur a pioché 2 pièces.\n"));
        assertTrue(architecte.getMessageRound().contains("La carte Temple a été construite.\n"));
        assertTrue(architecte.getMessageRound().contains("La carte chateau a été construite.\n"));
        assertTrue(architecte.getMessageRound().contains("Roi appelle Architecte(0). Pour ce tour, il possède 0 pièces, 2 batiments dans sa cité qui sont [Temple, chateau] et une main qui est [taverne].\n"));

        //Tour d'un tué  : il ne joue pas
        condottiere.setCiblesDispo(cibles);
        assertTrue(condottiere.getJoueur().getKilled());
        assertEquals(0,condottiere.getMain().size());
        assertEquals(0,condottiere.getCite().size());
        assertEquals(1,condottiere.getArgent());
        assertTrue(condottiere.getJoueur().getKilled());
        condottiere.jouerSonTour(adversaires,mockedDeck);
        assertEquals(0,condottiere.getMain().size());
        assertEquals(0,condottiere.getCite().size());
        assertEquals(1,condottiere.getArgent());
        assertFalse(condottiere.getJoueur().getKilled());
        assertTrue(condottiere.getMessageRound().contains("Condottiere a été tué"));

        //Tour d'un condottiere : il pioche un bâtiment, en construit un et attaque et détruit un bâtiment de l'architecte
        condottiere.jouerSonTour(adversaires,mockedDeck);
        assertEquals(0,condottiere.getMain().size());
        assertTrue(condottiere.getCite().contains(chateau));
        assertEquals(0,condottiere.getArgent());
        assertTrue(condottiere.getMessageRound().contains("(Aussi Grindur)Roi appelle Condottiere(0). Pour ce tour, il possède 1 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [].\n"));
        assertTrue(condottiere.getMessageRound().contains("Le joueur a pioché un batiment.\n"));
        assertTrue(condottiere.getMessageRound().contains("Condottiere cible Architecte\n"));
        assertTrue(condottiere.getMessageRound().contains("Condottiere a attaqué Architecte et a détruit chateau\n"));
        assertTrue(condottiere.getMessageRound().contains("La carte chateau a été construite.\n"));
        assertTrue(condottiere.getMessageRound().contains("Roi appelle Condottiere(0). Pour ce tour, il possède 0 pièces, 1 batiments dans sa cité qui sont [chateau] et une main qui est [].\n"));
    }

    @Test
    void testJouerAssassinVoleur(){//Test de la sélection de cible et d'action d'un assassin et d'un voleur
        ArrayList<Integer> cibles = new ArrayList<>();
        cibles.add(5);
        cibles.add(6);
        cibles.add(3);
        cibles.add(4);
        cibles.add(2);
        assassin.setCiblesDispo(cibles);
        voleur.setCiblesDispo(cibles);
        assertFalse(voleur.getJoueur().getKilled());
        assertFalse(b4.getJoueur().getRobbed());
        assertEquals(4,assassin.jouerAssassinVoleur(listeJoueurs));
        assertTrue(voleur.getJoueur().getKilled());
        voleur.getJoueur().resetKilled();
        assertEquals(1,voleur.jouerAssassinVoleur(listeJoueurs));
        assertTrue(b2.getJoueur().getRobbed());

        //cas de choix d'une cible absente
        cibles.remove((Integer) 5);
        cibles.remove((Integer) 6);
        cibles.remove((Integer) 4);
        cibles.remove((Integer) 2);
        voleur.setCiblesDispo(cibles);
        Bot[] listeJoueurSansMagicien = new Bot[]{b1,b2,b4};
        assertEquals(-1,voleur.jouerAssassinVoleur(listeJoueurSansMagicien));
    }

    @Test
    void testJouerMagicien(){//Test de l'action d'un magicien (échange avec un joueur et échange avec la pioche)
        when(mockedDeck.pioche()).thenReturn(eglise);
        ArrayList<Batiment> plusGrandeMain = new ArrayList<>(b1.getMain());
        ArrayList<Batiment> mainOriginale = new ArrayList<>(b3.getMain());
        assertEquals(mainOriginale,b3.getMain());
        assertEquals(plusGrandeMain,b1.getMain());
        assertEquals(0,b3.jouerMagicien(listeJoueurs,mockedDeck));//échange avec un joueur
        assertEquals(mainOriginale,b1.getMain());
        assertEquals(plusGrandeMain,b3.getMain());
        assertEquals(-1,b3.jouerMagicien(listeJoueurs,mockedDeck));//aucune action éffectuée
        assertEquals(plusGrandeMain,b3.getMain());
        b3.getJoueur().piocherArgent(2);
        b3.construireBatiment(taverne);
        b3.recoisBatiment(taverne);
        assertFalse(b3.getMain().contains(eglise));
        assertTrue(b3.getMain().contains(taverne));
        assertEquals(-1,b3.jouerMagicien(listeJoueurs,mockedDeck));//échange avec la pioche
        assertFalse(b3.getMain().contains(taverne));
        assertTrue(b3.getMain().contains(eglise));
    }

    @Test
    void testJouerArchitecte(){//Test action d'un architecte (construction jusqu'à trois bâtiment)
        when(mockedDeck.pioche()).thenReturn(taverne,eglise);
        Bot architecte = new Bot(10,"Grindur");
        architecte.setPersonnage(7);
        architecte.recoisBatiment(port);
        assertEquals(10,architecte.getArgent());
        assertTrue(architecte.getCite().isEmpty());
        architecte.jouerArchitecte(mockedDeck);//constuit 3 bâtiment
        assertEquals(1,architecte.getArgent());
        assertEquals(3,architecte.getCite().size());
        architecte.jouerArchitecte(mockedDeck);//ne construit rien par manque d'argent
        assertEquals(1,architecte.getArgent());
        assertEquals(3,architecte.getCite().size());
    }

    @Test
    void testCibleCondotiere(){//Test de sélection de la cible d'un condottiere
        Bot condotiere = new Bot(5,"Grindur");
        condotiere.setPersonnage(8);
        b1.recoisBatiment(chateau);
        b1.construireBatiment(chateau);
        Bot[] listeCibles = new Bot[]{b1};
        assertFalse(b1.getJoueur().getKilled());
        assertEquals(-1,condotiere.choixCibleCondottiere(listeCibles));

        //cas où l'éveque a été tué
        b1.getJoueur().isKilled();
        assertTrue(b1.getJoueur().getKilled());
        assertEquals(0,condotiere.choixCibleCondottiere(listeCibles));
        b1.getJoueur().resetKilled();

        b3.getJoueur().piocherArgent(100);
        b3.recoisBatiment(taverne);
        b3.recoisBatiment(chateau);
        b3.recoisBatiment(caserne);
        b3.recoisBatiment(forge);
        b3.construireBatiment(hdv);
        b3.construireBatiment(eglise);
        b3.construireBatiment(port);
        b3.construireBatiment(taverne);
        b3.construireBatiment(chateau);
        b3.construireBatiment(caserne);
        b3.construireBatiment(forge);
        listeCibles[0] = b3;
        assertEquals(-1,condotiere.choixCibleCondottiere(listeCibles));
        b3.getCite().remove(forge);
        listeCibles = new Bot[]{b2,b3,b4,b1};
        assertEquals(1,condotiere.choixCibleCondottiere(listeCibles));
        assertEquals(-1,condotiere.choixCibleCondottiere(new Bot[]{condotiere}));
    }

    @Test
    void testJouerPersoQuartier(){//Test construction d'un quartier avant d'en récupérer l'argent ou de récupération de l'argent avant la construction
        b2.recoisBatiment(port);
        b2.getMain().remove(chateau);
        assertTrue(b2.getCite().isEmpty());
        assertEquals(2,b2.getMain().size());
        assertEquals(7,b2.getArgent());
        assertEquals(hdv,b2.jouerPersoQuartier(null));//construit puis récupère argent
        assertTrue(b2.getCite().contains(hdv));
        assertEquals(1,b2.getMain().size());
        assertEquals(3,b2.getArgent());
        assertEquals(port,b2.jouerPersoQuartier(null));
        assertTrue(b2.getCite().contains(port));
        assertEquals(0,b2.getMain().size());
        assertEquals(0,b2.getArgent());
        b2.recoisBatiment(caserne);
        assertEquals(1,b2.getMain().size());
        assertEquals(2,b2.getCite().size());
        assertNull(b2.jouerPersoQuartier(null));//récupère l'argent puis construit
        assertEquals(1,b2.getMain().size());
        assertEquals(2,b2.getArgent());
        assertEquals(2,b2.getCite().size());
    }

    @Test
    void testJouerBatimentAConstruire(){//Test de la consruction d'un bâtiment
        assertEquals(hdv,b1.jouerBatimentAConstruire(null));
        assertNull(b3.jouerBatimentAConstruire(null));
        assertEquals(chateau,b3.jouerBatimentAConstruire(chateau));
    }

    @Test
    void testJouerMessageDuTour(){//Test du message d'action du personnage
        assertEquals("",b1.getMessageRound());
        b1.jouerMessageDuTour(-1,null,listeJoueurs);//l'eveque n'a pas d'action particulière à annoncer
        assertEquals("",b1.getMessageRound());
        assertEquals("",b2.getMessageRound());
        b2.jouerMessageDuTour(-1,hdv,listeJoueurs);//annoce qu'il a construit un bâtiment
        assertEquals("La carte hotel de ville a été construite.\n",b2.getMessageRound());
        assertEquals("",voleur.getMessageRound());
        voleur.jouerMessageDuTour(1,null,listeJoueurs);//annonce qu'il a volé quelqu'un
        assertEquals("Voleur a volé Marchand\n",voleur.getMessageRound());
        Bot condotiere = new Bot(10,"Grindur");
        condotiere.setPersonnage(8);
        condotiere.getJoueur().setHostileLevel(10);
        assertEquals("",condotiere.getMessageRound());
        assertFalse(condotiere.getJoueur().getDetruitBatiment());
        condotiere.jouerMessageDuTour(1,null,listeJoueurs);//annonce qu'il a détruit un bâtiment
        assertEquals("Condottiere cible Marchand\n",condotiere.getMessageRound());
        assertFalse(condotiere.getJoueur().getDetruitBatiment());
        Bot architecte = new Bot(2,"Grindur Jr");
        architecte.setPersonnage(7);
        assertEquals("",architecte.getMessageRound());
        architecte.jouerMessageDuTour(-1,hdv,listeJoueurs);//n'annoce pas qu'il a construit un bâtiment car déjà annoncé dans une autre méthode
        assertEquals("",architecte.getMessageRound());
    }

    @Test
    void testNombreBatimentParQuartier(){//Test du calcul du nombre de bâtiment par type de quartier
        b3.getJoueur().piocherArgent(100);
        b3.recoisBatiment(taverne);
        b3.recoisBatiment(chateau);
        b3.recoisBatiment(caserne);
        b3.recoisBatiment(forge);
        b3.construireBatiment(hdv);
        b3.construireBatiment(eglise);
        b3.construireBatiment(port);
        b3.construireBatiment(taverne);
        b3.construireBatiment(chateau);
        b3.construireBatiment(caserne);
        b3.construireBatiment(forge);
        int[] result = b3.nombreBatimentParQuartier();
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        assertEquals(1, result[2]);
        assertEquals(3, result[3]);
        assertEquals(1, result[4]);
    }

    @Test
    void testCoutMinMax(){//Test de la recherche du bâtiment le plus/moins cher de la main
        Bot b = new Bot(0,"Grindur");
        b.setPersonnage(5);
        b.recoisBatiment(eglise);
        b.recoisBatiment(hdv);
        b.recoisBatiment(taverne);
        assertNull(b.coutMinMaxConstructible(b.getMain(),"+",b.getArgent()));
        assertNull(b.coutMinMaxConstructible(b.getMain(),"-",b.getArgent()));
        b.getJoueur().piocherArgent(2);
        assertEquals(taverne,b.coutMinMaxConstructible(b.getMain(),"+",b.getArgent()));
        assertEquals(taverne,b.coutMinMaxConstructible(b.getMain(),"-",b.getArgent()));
        b.getJoueur().piocherArgent(1);
        assertEquals(eglise,b.coutMinMaxConstructible(b.getMain(),"+",b.getArgent()));
        assertEquals(taverne,b.coutMinMaxConstructible(b.getMain(),"-",b.getArgent()));
        b.getJoueur().piocherArgent(2);
        assertEquals(hdv,b.coutMinMaxConstructible(b.getMain(),"+",b.getArgent()));
        assertEquals(taverne,b.coutMinMaxConstructible(b.getMain(),"-",b.getArgent()));
    }

    @Test
    void testChoixSelonPersonalite(){//Test du choix de personnage en fonction de l'hostilité
        Bot b = new Bot(5,"Grindur");//Pour hostilité 0 (Batisseur)
        int[] choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        int[] choixHostiliteBatiseur = new int[]{0,-2,5,2,2,3,7,2};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteBatiseur[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(1);//Pour hostilité 1 (Batisseur)
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteBatiseur[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(2);//Pour hostilité 2 (Batisseur)
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteBatiseur[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(3);//Pour hostilité 3 (Batisseur)
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteBatiseur[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(4);//Pour hostilité 4 (Opportuniste)
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        int[] choixHostiliteOportuniste = new int[]{2,0,5,0,0,1,5,0};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteOportuniste[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(5);//Pour hostilité 5 (Opportuniste)
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteOportuniste[i],choixPersonnage[i]);
        }
        when(mockedDeck.pioche()).thenReturn(taverne,taverne,hdv,hdv,chateau,chateau,caserne,caserne,eglise,eglise);
        b.getJoueur().setHostileLevel(6);//Pour hostilité 6 (Opportuniste) + fin de partie
        for(int i=0;i<5;i++){b.jouerPiocherCarte(mockedDeck);}
        b.getJoueur().piocherArgent(14);
        for(int i=0;i<5;i++){b.construireBatiment(b.getMain().get(0));}
        assertEquals(5,b.getCite().size());
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        int[] choixHostiliteOportunisteFin = new int[]{3,1,5,1,5,3,5,3};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteOportunisteFin[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(7);//Pour hostilité 7 (Opportuniste) + fin de partie
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixHostiliteOportunisteFin = new int[]{3,1,5,1,5,3,5,3};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteOportunisteFin[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(8);//Pour hostilité 8 (Aggréssif)
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        int [] choixHostiliteAggressif = new int[]{4,1,5,1,1,3,5,4};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteAggressif[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(9);//Pour hostilité 9 (Aggréssif)
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteAggressif[i],choixPersonnage[i]);
        }
        b.getJoueur().setHostileLevel(10);//Pour hostilité 10 (Aggréssif)
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixHostiliteAggressif = new int[]{5,2,5,1,1,3,5,5};
        choixPersonnage = b.choixSuivantPersonnalite(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixHostiliteAggressif[i],choixPersonnage[i]);
        }
    }

    @Test
    void testChoixFinPartie(){//Test du choix de personnage en fin de partie
        when(mockedDeck.pioche()).thenReturn(taverne,taverne,hdv,hdv,chateau,chateau,caserne,caserne,eglise,eglise,temple,temple,port,port);
        Bot b = new Bot(0,"Grindur");
        int[] choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        int[] choixVilleVide = new int[]{0,0,0,0,0,0,0,0};
        choixPersonnage = b.choixFinDePartie(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixVilleVide[i],choixPersonnage[i]);
        }
        b.getJoueur().piocherArgent(11);
        for(int i=0;i<4;i++){b.jouerPiocherCarte(mockedDeck);}
        for(int i=0;i<4;i++){b.construireBatiment(b.getMain().get(0));}
        choixPersonnage = b.choixFinDePartie(choixPersonnage);//4 bâtiment sur 7 sans argent
        for(int i=0;i<8;i++){
            assertEquals(choixVilleVide[i],choixPersonnage[i]);
        }
        b.getJoueur().piocherArgent(4);
        int[] choixVilleMoitieRemplie = new int[]{0,0,0,0,0,0,10,0};
        choixPersonnage = b.choixFinDePartie(choixPersonnage);//4 bâtiment sur 7 avec argent
        for(int i=0;i<8;i++){
            assertEquals(choixVilleMoitieRemplie[i],choixPersonnage[i]);
        }
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        b.jouerPiocherCarte(mockedDeck);
        b.construireBatiment(b.getMain().get(0));
        choixPersonnage = b.choixFinDePartie(choixPersonnage);//5 bâtiment sur 7 mais pas d'argent
        for(int i=0;i<8;i++){
            assertEquals(choixVilleVide[i],choixPersonnage[i]);
        }
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        b.jouerPiocherCarte(mockedDeck);
        int[] choixVillePresqueRemplie = new int[]{0,0,0,3,0,0,0,0};//5 bâtiment sur 7 et construit prochain tour
        choixPersonnage = b.choixFinDePartie(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixVillePresqueRemplie[i],choixPersonnage[i]);
        }
        b.getJoueur().piocherArgent(4);
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixVillePresqueRemplie = new int[]{0,0,0,3,0,0,10,0};
        choixPersonnage = b.choixFinDePartie(choixPersonnage);//5 bâtiment sur 7 avec argent
        for(int i=0;i<8;i++){
            assertEquals(choixVillePresqueRemplie[i],choixPersonnage[i]);
        }
        b.getJoueur().construireBatiment(b.getMain().get(0));
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixVillePresqueRemplie = new int[]{8,7,6,5,6,3,12,3};
        choixPersonnage = b.choixFinDePartie(choixPersonnage);//6 bâtiment sur 7
        for(int i=0;i<8;i++){
            assertEquals(choixVillePresqueRemplie[i],choixPersonnage[i]);
        }
        b.jouerPiocherCarte(mockedDeck);
        choixPersonnage = new int[]{0,0,0,0,0,0,0,0};
        choixVillePresqueRemplie = new int[]{8,7,6,8,6,3,12,3};//6 bâtiment sur 7
        choixPersonnage = b.choixFinDePartie(choixPersonnage);
        for(int i=0;i<8;i++){
            assertEquals(choixVillePresqueRemplie[i],choixPersonnage[i]);
        }
    }

    @Test//Test d'affichage
    void testToString(){
        assertEquals("Eveque",  b1.toString());
    }

    @Test
    void testPiocheBatiment(){//Test du piochage de bâtiment
        int taille = deck.getTaille();
        for(int i=0;i< taille;i++){
            b1.getJoueur().piocherBatiment(deck.pioche());
        }
        for (int i=0; i<30; i++) {deck.jette(random_cher);}
        assertEquals(2,b2.getMain().size());
        assertEquals(7,b2.getArgent());
        b2.jouerSonTour(listeJoueurs,deck);
        assertEquals(2,b2.getMain().size());
        assertEquals(4,b2.getArgent());
    }

    @Test
    void testPiocheArgent(){//Test du piochage d'argent
        Bot b = new Bot(0, "j");
        b.setPersonnage(4);
        for (int i=0; i<5; i++) {b.recoisBatiment(hdv);}

        assertFalse(b.getMain().contains(taverne));
        assertEquals(0,b.getArgent());

        listeJoueurs[0] = b;
        b.jouerSonTour(listeJoueurs,deck);

        assertFalse(b.getMain().contains(taverne));
        assertEquals(2,b.getArgent());
    }

    @Test
    void testPiocheBatimentSansConstruire(){
        b4.getJoueur().retireArgent(1);
        assertEquals(1,b4.getMain().size());
        assertEquals(0,b4.getArgent());

        b4.jouerSonTour(listeJoueurs,deck);

        assertEquals(2,b4.getMain().size());
        assertEquals(0,b4.getArgent());
    }

    @Test
    void testCoutMinimum(){
        b1.recoisBatiment(hdv);
        b1.recoisBatiment(chateau);
        b1.recoisBatiment(port);
        b1.getJoueur().piocherArgent(1);
        assertEquals(hdv, b1.coutMinMaxConstructible(b1.getMain(),"+",b1.getArgent()));
        b1.construireBatiment(hdv);
        assertEquals(chateau, b1.coutMinMaxConstructible(b1.getMain(),"+", b1.getArgent()));
    }

    @Test
    void testChooseCharacter(){
        assertEquals("[Assassin, Voleur, Magicien, Roi, Eveque, Marchand, Condottiere]", "" +  b1.chooseCharacter(listePersonnage,new ArrayList<Personnage>()));
        ArrayList<Personnage> liste = new ArrayList<>();
        liste.add(Voleur);
        assertEquals("[]", "" +  b1.chooseCharacter(liste,new ArrayList<Personnage>()));
    }

    @Test
    void testGetMessageRound(){
        assertEquals( "",b1.getMessageRound());
        b1.jouerSonTour(listeJoueurs,deck);
        assertEquals( "(j1)Roi appelle Eveque(0). Pour ce tour, il possède 5 pièces, 0 batiments dans sa cité qui sont [] et une main qui est [hotel de ville, port, taverne, Forge].\n" +
                "Le joueur a pioché 2 pièces.\n" +
                "La carte hotel de ville a été construite.\n" +
                "Roi appelle Eveque(0). Pour ce tour, il possède 2 pièces, 1 batiments dans sa cité qui sont [hotel de ville] et une main qui est [port, taverne, Forge].\n",
                b1.getMessageRound());
    }

    @Test
    void testRetirePersonnage(){
        assertEquals("Eveque", "" + b1.retirePersonnage());
        assertEquals("Marchand", "" + b2.retirePersonnage());
    }

    @Test
    void testForge() {
        Bot b = new Bot(20, "j");
        when(mockedDeck.pioche()).thenReturn(hdv);

        b.jouerForge(mockedDeck);//essaye de jouer la forge sans l'avoir
        assertEquals(20,b.getArgent());
        assertEquals("",b.getMessageRound());
        b.recoisBatiment(forge);
        assertEquals(0,b.getTailleCite());
        b.construireBatiment(forge);
        assertEquals(1,b.getTailleCite());

        b.jouerForge(mockedDeck);//joue la forge, pioche 3 bâtiments pour 2 PO
        assertEquals(13,b.getArgent());
        assertEquals(3,b.getMain().size());
        assertEquals("Le joueur a pioché trois bâtiments en dépensant 2PO.\n",b.getMessageRound());

        b.jouerForge(mockedDeck);//essaye de jouer la forge avec une main déjà assez remplie
        assertEquals(13,b.getArgent());
        assertEquals(3,b.getMain().size());

        b.construireBatiment(hdv);
        b.getJoueur().retireArgent(5);
        assertEquals(3,b.getArgent());
        assertEquals(2,b.getMain().size());
        b.jouerForge(mockedDeck);//essaye de jouer la forge avec peu d'argent
        assertEquals(3,b.getArgent());
        assertEquals(2,b.getMain().size());
    }

    @Test
    void testEcoleDeMagie() {
        Bot b = new Bot(6, "j");
        Batiment ecoleMagie = new Batiment("Ecole de Magie", 6, quartier.MERVEILLE);
        b.recoisBatiment(ecoleMagie);
        for (int i=0; i<4; i++) {b.recoisBatiment(hdv);}
        b.construireBatiment(ecoleMagie);
        b.getJoueur().setPersonnage(4);
        Bot[] tab = new Bot[]{b, b1, b2, b3};
        assertEquals(0,b.getArgent());
        b.jouerSonTour(tab,new Deck());
        assertEquals(3,b.getArgent());
    }

    @Test
    void testBibliotheque() {
        Batiment bibliotheque = new Batiment("Bibliotheque", 6, quartier.MERVEILLE);
        b4.getJoueur().piocherArgent(5);
        b4.recoisBatiment(bibliotheque);
        b4.construireBatiment(bibliotheque);
        assertEquals(1,b4.getMain().size());
        Bot[] tab = new Bot[]{b1, b2, b3, b4};
        b4.jouerSonTour(tab, new Deck());
        assertEquals(3, b4.getMain().size());
    }

    @Test
    void testDonjon() {
        Bot b11 = new Bot(20,"j1");
        Bot b12 = new Bot(5, "j2");
        Batiment donjon = new Batiment("Donjon", 3, quartier.MERVEILLE);
        b12.recoisBatiment(donjon);
        b12.construireBatiment(donjon);
        b11.getJoueur().setPersonnage(8);
        b12.getJoueur().setPersonnage(4);
        Bot[] tab = new Bot[]{b11, b12};
        assertEquals(1,b12.getTailleCite());
        b11.jouerSonTour(tab, new Deck());
        assertEquals(1,b12.getTailleCite());

    }

    @Test
    void testGrandeMuraille() { // Le test a une proba de ne pas passer : si le condotiere pioche une carte avec un coût de 1, il la construit et ne détruit pas la grande muraille
        Bot attaquant = new Bot(4, "j1"); // condotiere
        attaquant.getJoueur().setHostileLevel(10);
        Bot victime = new Bot(8, "j2"); // victime
        Batiment grandeMuraille = new Batiment("Grande Muraille", 6, quartier.MERVEILLE);
        Deck deck = new Deck();

        int taille = deck.getTaille();
        for(int i=0;i< taille;i++){
            victime.getJoueur().piocherBatiment(deck.pioche());
        }
        deck.jette(random_cher);
        deck.jette(random_cher);
        deck.jette(random_cher);
        deck.jette(random_cher);

        victime.recoisBatiment(grandeMuraille);
        victime.recoisBatiment(taverne);
        victime.construireBatiment(grandeMuraille);
        victime.construireBatiment(taverne);

        attaquant.setPersonnage(8);
        victime.setPersonnage(4);

        assertEquals(2, victime.getTailleCite());
        assertEquals(4, attaquant.getArgent());
        Bot[] tab = new Bot[]{attaquant, victime};
        attaquant.jouerSonTour(tab, deck);


        assertEquals(1, victime.getTailleCite());
        assertEquals(2, attaquant.getArgent());

        attaquant.getJoueur().piocherArgent(4);

        attaquant.jouerSonTour(tab, deck);

        assertEquals(0, victime.getTailleCite());
        assertEquals(0, attaquant.getArgent());


    }

    @Test
    void testLaboratoire() {
        Bot b11 = new Bot(5, "j");
        b11.jouerLaboratoire(deck);
        assertEquals("",b11.getMessageRound());

        Batiment laboratoire = new Batiment("Laboratoire", 5, quartier.MERVEILLE);
        b11.setPersonnage(1);
        b11.recoisBatiment(laboratoire);
        b11.construireBatiment(laboratoire);

        Deck deck1 = new Deck();
        deck1.getDeck().clear();
        deck1.jette(random_cher);
        deck1.jette(random_cher);

        b11.jouerLaboratoire(deck);
        assertEquals("",b11.getMessageRound());
        for (int i=0; i<5; i++) {b11.recoisBatiment(hdv);}

        b11.jouerLaboratoire(deck);
        assertEquals(2, b11.getArgent());

        b11.jouerLaboratoire(deck);
        assertEquals(4, b11.getArgent());
        assertEquals(3, b11.getMain().size());

        b11.getJoueur().piocherArgent(1);
        b11.construireBatiment(hdv);
        for (int i=0; i<3; i++) {b11.recoisBatiment(hdv);}
        b11.jouerLaboratoire(deck);
        assertEquals(4, b11.getMain().size());
        assertEquals(2, b11.getArgent());
    }

    @Test
    void testCondottiereAttaque(){
        Bot attaquant = new Bot(10,"j1");
        Bot victime = new Bot(10,"j2");
        victime.recoisBatiment(hdv);
        victime.recoisBatiment(eglise);
        victime.recoisBatiment(taverne);

        victime.construireBatiment(hdv);
        victime.construireBatiment(eglise);
        victime.construireBatiment(taverne);

        deck.getDeck().clear();
        for (int i=0; i<5; i++) {deck.jette(random_cher);}
        Bot[] tab = new Bot[]{attaquant, victime};
        attaquant.setPersonnage(8);
        victime.setPersonnage(1);
        attaquant.getJoueur().setHostileLevel(0);
        attaquant.jouerSonTour(tab,deck);

        assertEquals(3,victime.getTailleCite());
        assertEquals(10,attaquant.getArgent()); // aucune destruction car aucun bâtiment qui coûte 0 ou 1

        attaquant.getJoueur().setHostileLevel(8);
        attaquant.jouerSonTour(tab,deck);

        assertEquals(2,victime.getTailleCite());
        assertEquals(9,attaquant.getArgent()); // destruction car taverne coûte 2

        attaquant.getJoueur().setHostileLevel(10);
        attaquant.jouerSonTour(tab,deck);

        assertEquals(1,victime.getTailleCite());
        assertEquals(7,attaquant.getArgent()); // destruction car hdv coûte 5
    }

    @Test
    void testChoixCible() {
        Bot bot1 = new Bot(20, "j1");
        Bot bot4 = new Bot(20, "j4");
        Bot bot2 = new Bot(20, "j2");

        Bot[] adversaires = new Bot[]{bot1, bot4, bot2};
        bot1.setPersonnage(1);
        bot4.setPersonnage(4);
        bot2.setPersonnage(2);
        ArrayList<Integer> ciblesDispos = new ArrayList<>();
        ciblesDispos.add(4);
        bot1.setCiblesDispo(ciblesDispos);
        assertEquals(4, bot1.choixCible(adversaires));

        bot4.setPersonnage(1);
        ciblesDispos.remove(0);
        ciblesDispos.add(1);
        bot1.setCiblesDispo(ciblesDispos);
        assertEquals(3, bot1.choixCible(adversaires));

        bot4.setPersonnage(7);
        bot2.setPersonnage(8);
        ciblesDispos.remove(0);
        ciblesDispos.add(7);
        ciblesDispos.add(8);
        bot1.setCiblesDispo(ciblesDispos);
        assertEquals(8, bot1.choixCible(adversaires));

        ciblesDispos.clear();
        ciblesDispos.add(7);
        ciblesDispos.add(6);
        ciblesDispos.add(4);
        ciblesDispos.add(8);
        ciblesDispos.add(5);
        bot2.setCiblesDispo(ciblesDispos);
        Bot bot3 = new Bot(20, "j3");
        Bot bot5 = new Bot(20, "j5");
        Bot bot6 = new Bot(20, "j6");
        Bot bot7 = new Bot(20, "j7");
        Bot bot8 = new Bot(20, "j8");
        bot1.setPersonnage(1);
        bot2.setPersonnage(2);
        bot3.setPersonnage(3);
        bot4.setPersonnage(4);
        bot5.setPersonnage(5);
        bot6.setPersonnage(6);
        bot7.setPersonnage(7);
        bot8.setPersonnage(8);
        adversaires = new Bot[]{bot1,bot2,bot3,bot4,bot5,bot6,bot7,bot8};
        assertFalse(bot7.getJoueur().getKilled());
        assertEquals(7, bot2.choixCible(adversaires));
        bot7.getJoueur().isKilled();
        assertTrue(bot7.getJoueur().getKilled());
        assertFalse(bot6.getJoueur().getKilled());
        assertEquals(6, bot2.choixCible(adversaires));
        bot6.getJoueur().isKilled();
        assertTrue(bot6.getJoueur().getKilled());
        bot7.getJoueur().resetKilled();
        ciblesDispos.remove((Integer) 7);
        bot2.setCiblesDispo(ciblesDispos);
        assertFalse(bot4.getJoueur().getKilled());
        assertEquals(4, bot2.choixCible(adversaires));
        bot4.getJoueur().isKilled();
        assertTrue(bot4.getJoueur().getKilled());
        bot6.getJoueur().resetKilled();
        ciblesDispos.remove((Integer) 6);
        bot2.setCiblesDispo(ciblesDispos);
        assertFalse(bot8.getJoueur().getKilled());
        assertEquals(8, bot2.choixCible(adversaires));
        bot8.getJoueur().isKilled();
        assertTrue(bot8.getJoueur().getKilled());
        bot4.getJoueur().resetKilled();
        ciblesDispos.remove((Integer) 4);
        bot2.setCiblesDispo(ciblesDispos);
        assertFalse(bot5.getJoueur().getKilled());
        assertEquals(5, bot2.choixCible(adversaires));
        bot5.getJoueur().isKilled();
        bot8.getJoueur().resetKilled();
        ciblesDispos.remove((Integer) 8);
        bot2.setCiblesDispo(ciblesDispos);
        assertTrue(bot5.getJoueur().getKilled());
        assertEquals(3, bot2.choixCible(adversaires));
    }

    @Test
    void testSetCibles() {
        assertEquals(6, b1.setCibles(new ArrayList<>(), new ArrayList<>()).size());
        ArrayList<Personnage> defausse = new ArrayList<>();
        defausse.add(Voleur);
        defausse.add(Magicien);
        defausse.add(Roi);

        assertEquals(3, b1.setCibles(new ArrayList<>(), defausse).size());
        assertEquals(6, b1.setCibles(new ArrayList<>(), defausse).get(0));
        assertEquals(7, b1.setCibles(new ArrayList<>(), defausse).get(1));
        assertEquals(8, b1.setCibles(new ArrayList<>(), defausse).get(2));
    }

    @Test
    void testCoutMinMaxConstructible() {
        ArrayList<Batiment> batListe = new ArrayList<>();
        batListe.add(taverne);
        batListe.add(hdv);
        batListe.add(eglise);

        assertEquals("hotel de ville", b1.coutMinMaxConstructible(batListe, "+", 5).getNom());
        assertEquals("taverne", b1.coutMinMaxConstructible(batListe, "-", 5).getNom());
        assertEquals("eglise", b1.coutMinMaxConstructible(batListe, "+", 3).getNom());
        assertEquals("taverne", b1.coutMinMaxConstructible(batListe, "-", 3).getNom());
    }

    @Test
    void testChoixCibleAssassin(){
        ArrayList<Integer> cibles = new ArrayList<>();
        cibles.add(2);cibles.add(3);cibles.add(4);cibles.add(5);cibles.add(6);

        assassin.setCiblesDispo(cibles); // on vise le voleur car qq a plus de 4 pièces
        assertEquals(2,assassin.choixCibleAssassin(listeJoueurs));
        b1.getJoueur().retireArgent(7);
        b2.getJoueur().retireArgent(5);
        assertEquals(6,assassin.choixCibleAssassin(listeJoueurs)); // on vise sinon le marchand
        cibles.add(7);
        assassin.setCiblesDispo(cibles);
        assertEquals(7,assassin.choixCibleAssassin(listeJoueurs)); // on vise l'architecte s'il est présent
        cibles.clear();cibles.add(3);assassin.setCiblesDispo(cibles);assertEquals(3,listeJoueurs[5].choixCibleAssassin(listeJoueurs));
        cibles.clear();cibles.add(4);assassin.setCiblesDispo(cibles);assertEquals(4,listeJoueurs[5].choixCibleAssassin(listeJoueurs));
        cibles.clear();cibles.add(5);assassin.setCiblesDispo(cibles);assertEquals(5,listeJoueurs[5].choixCibleAssassin(listeJoueurs));
        cibles.clear();cibles.add(8);assassin.setCiblesDispo(cibles);assertEquals(8,listeJoueurs[5].choixCibleAssassin(listeJoueurs));

        // vise condottiere
        cibles.clear();cibles.add(2);cibles.add(3);cibles.add(4);cibles.add(5);cibles.add(6);cibles.add(8);
        assassin.setCiblesDispo(cibles);
        assertEquals(8,assassin.choixCibleAssassin(listeJoueurs));

        // vise magicien s'il possede le plus de cartes
        cibles.clear();cibles.add(2);cibles.add(3);cibles.add(4);cibles.add(5);cibles.add(6);
        assassin.setCiblesDispo(cibles);
        assassin.getJoueur().piocherBatiment(hdv);listeJoueurs[5].getJoueur().piocherBatiment(hdv);listeJoueurs[5].getJoueur().piocherBatiment(hdv);listeJoueurs[5].getJoueur().piocherBatiment(hdv);
        assertEquals(3,assassin.choixCibleAssassin(listeJoueurs));

        // vise eveque si aussi condottiere et qq avec plus de 6 batiments
        b1.getJoueur().piocherArgent(20);
        b1.getJoueur().piocherBatiment(chateau);
        b1.getJoueur().piocherBatiment(eglise);
        for(int i=0;i<5;i++){
            b1.construireBatiment(b1.getMain().get(0));
        }
        cibles.add(8);
        assertEquals(3,assassin.choixCibleAssassin(listeJoueurs));
        b1.construireBatiment(b1.getMain().get(0));
        cibles.remove((Integer) 5);
        assassin.setCiblesDispo(cibles);
        assertEquals(3,assassin.choixCibleAssassin(listeJoueurs));
        cibles.add(5);
        assassin.setCiblesDispo(cibles);
        assertEquals(5,assassin.choixCibleAssassin(listeJoueurs));

        cibles.clear();
        cibles.add(2);
        assassin.setCiblesDispo(cibles);
        assertEquals(2,assassin.choixCibleAssassin(listeJoueurs));
        cibles.clear();
        cibles.add(8);
        assassin.setCiblesDispo(cibles);
        assertEquals(8,assassin.choixCibleAssassin(listeJoueurs));
    }

    @Test
    void testChoixCibleVoleur(){
        ArrayList<Integer> cibles = new ArrayList<>();
        cibles.add(2);cibles.add(3);cibles.add(4);cibles.add(5);cibles.add(6);
        listeJoueurs[4].setCiblesDispo(cibles);
        assertEquals(6,listeJoueurs[4].choixCible(listeJoueurs));
        listeJoueurs[1].getJoueur().isKilled();
        assertEquals(4,listeJoueurs[4].choixCible(listeJoueurs));
        cibles.add(7);listeJoueurs[1].getJoueur().resetKilled();
        listeJoueurs[4].setCiblesDispo(cibles);
        assertEquals(7,listeJoueurs[4].choixCible(listeJoueurs));
        cibles.clear();cibles.add(2);cibles.add(8);cibles.add(5);
        listeJoueurs[4].setCiblesDispo(cibles);
        assertEquals(8,listeJoueurs[4].choixCible(listeJoueurs));
        cibles.remove(1);
        listeJoueurs[4].setCiblesDispo(cibles);
        assertEquals(5,listeJoueurs[4].choixCible(listeJoueurs));
        listeJoueurs[0].getJoueur().isKilled();
        assertEquals(3,listeJoueurs[4].choixCible(listeJoueurs));
    }

    @Test
    void testEstPersonnage(){
        b1.setPersonnage(1);
        assertTrue(b1.estPersonnage("Assassin"));
        b1.setPersonnage(2);
        assertTrue(b1.estPersonnage("Voleur"));
        b1.setPersonnage(3);
        assertTrue(b1.estPersonnage("Magicien"));
        b1.setPersonnage(4);
        assertTrue(b1.estPersonnage("Roi"));
        b1.setPersonnage(5);
        assertTrue(b1.estPersonnage("Eveque"));
        b1.setPersonnage(6);
        assertTrue(b1.estPersonnage("Marchand"));
        b1.setPersonnage(7);
        assertTrue(b1.estPersonnage("Architecte"));
        b1.setPersonnage(8);
        assertTrue(b1.estPersonnage("Condottiere"));
    }
}