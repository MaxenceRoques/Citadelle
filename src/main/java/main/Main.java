package main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import com.opencsv.WriterCSV;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import com.vogella.logger.MyLogger;
import com.vogella.logger.test.UseLogger;

import java.io.*;
import java.io.FileReader;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Level;

import plateau.Plateau;

public class Main {

    private static final String MOYENNE = "Moyenne";
    private static final String VICTOIRE = "Victoire";
    private static final String TAUXV = "TauxV";
    private static final String DEFAITE = "Défaite";
    private static final String TAUXD = "TauxD";
    private static final String NBGAME = "nbGame";

    @Parameter(names = "--demo", description = "Run a demo")
    private boolean demo = false;

    @Parameter(names = "--2thousands", description = "Run 2000 games")
    private boolean twoThousands = false;

    @Parameter(names = "--csv", description = "Run a game and write the results in a CSV file, updating the stats")
    private boolean csv = false;

    private SecureRandom random = new SecureRandom();

    public static void main(String... argv) throws IOException, CsvException {
        Main main = new Main();
        MyLogger.setup();
        UseLogger log = new UseLogger();

        JCommander.newBuilder().addObject(main).build().parse(argv);


        if (main.demo && !main.twoThousands && !main.csv) {
            main.runDemo(log);
        }

        if (main.twoThousands && !main.demo && !main.csv) {
            main.runTwoThousand(log);
        }

        if (main.csv && !main.demo && !main.twoThousands) {
            Path path = Paths.get("stats", "gamestats.csv");
            File file = new File(path.toString());

            List<String[]> allRows = null;

            if (file.exists()) {
                //Build reader instance
                try (CSVReader reader = new CSVReader(new FileReader(String.valueOf(Paths.get("stats", "gamestats.csv"))))) {
                    //Read all rows at once
                    allRows = reader.readAll();
                }
                WriterCSV.setUpCSV();
            }
            main.runCsv(log, allRows);
        }

        if (main.demo && main.csv && !main.twoThousands) {
            WriterCSV.setUpDemoCSV();
            main.runDemoCSV(log, 6);
        }

    }

    public void runDemo(UseLogger log) {
        log.getLogger().setLevel(Level.INFO);
        Plateau plateau = new Plateau(6, new int[]{0, 0, 2, 4, 8, 10}, random, log);
        plateau.allRound(true);
    }

    public void runTwoThousand(UseLogger log) throws IOException {
        log.getLogger().setLevel(Level.SEVERE);
        WriterCSV.setUp2thousands(6);
        float[] tab1 = new float[6];
        int[] victory1 = new int[6];
        int nbGame = 0;

        for (int i = 0; i < 1000; i++) {
            Plateau plateau = new Plateau(6, new int[]{0, 0, 0, 2, 2, 2}, random, log);
            plateau.allRound(false);

            String[] msg = plateau.getMessageRound().split(" ");
            tab1[0] += Integer.parseInt(msg[0]);
            tab1[1] += Integer.parseInt(msg[1]);
            tab1[2] += Integer.parseInt(msg[2]);
            tab1[3] += Integer.parseInt(msg[3]);
            tab1[4] += Integer.parseInt(msg[4]);
            tab1[5] += Integer.parseInt(msg[5]);
            victory1[Integer.parseInt(msg[6])]++;
            nbGame++;
        }

        String[] line1 = new String[tab1.length+1];
        String[] messsage = new String[tab1.length+1];
        messsage[0] = "Meilleur Bot contre le deuxieme meilleur";
        WriterCSV.getWriter().writeNext(messsage);
        String[] nombreGame = new String[tab1.length+1];
        nombreGame[0] = NBGAME;
        nombreGame[1] = nbGame+"";
        WriterCSV.getWriter().writeNext(nombreGame);

        // Ecriture des moyennes
        ecritureMoyenne(line1, MOYENNE, tab1, nbGame, null);

        // Ecriture des victoires
        ecritureStat(line1, VICTOIRE, victory1, nbGame, null, nbGame);

        // Taux de victoire
        ecritureTaux(line1, TAUXV, victory1, nbGame, null, nbGame);
        // Ecriture des défaites

        ecritureStat(line1, DEFAITE, victory1, nbGame, null, nbGame);
        // Taux de défaites

        ecritureTaux(line1, TAUXD, victory1, nbGame, null, nbGame);

        // 1000 Partie entre le meilleur bot et le deuxieme meilleur
        System.out.println(" \n" + nbGame + " parties du meilleur bot contre le deuxieme meilleur \n");
        if(nbGame==0){nbGame=1;}
        for (int j = 0; j < tab1.length; j++) {
            System.out.println("joueur " + (j + 1) + " : score moyen " + (tab1[j]/nbGame) + " | Pourcentage de Victoire : " + (victory1[j]/(float)nbGame*100) + "% | Pourcentage de défaite : " + (((nbGame - victory1[j])/(float)nbGame)*100) +"%");
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 1000 Partie entre le meilleur bot et lui meme

        System.out.println(" \n" + nbGame + " parties du meilleur bot contre lui meme \n");

        float[] tab2 = new float[6];
        int[] victory2 = new int[6];
        int nbGame2 = 0;

        for (int i = 0; i < 1000; i++) {
            Plateau plateau = new Plateau(6, new int[]{2, 2, 2, 2, 2, 2}, random, log);
            plateau.allRound(false);
            String[] msg = plateau.getMessageRound().split(" ");
            tab2[0] += Integer.parseInt(msg[0]);
            tab2[1] += Integer.parseInt(msg[1]);
            tab2[2] += Integer.parseInt(msg[2]);
            tab2[3] += Integer.parseInt(msg[3]);
            tab2[4] += Integer.parseInt(msg[4]);
            tab2[5] += Integer.parseInt(msg[5]);
            victory2[Integer.parseInt(msg[6])]++;

            nbGame2++;
        }

        String[] line2 = new String[tab2.length+1];
        WriterCSV.getWriter().writeNext(new String[tab2.length+1]);
        String[] message2 = new String[tab2.length+1];
        message2[0] = "Meilleur Bot contre lui-même";
        WriterCSV.getWriter().writeNext(message2);
        String[] nombreGame2 = new String[tab2.length+1];
        nombreGame2[0] = NBGAME;
        nombreGame2[1] = nbGame2+"";
        WriterCSV.getWriter().writeNext(nombreGame2);

        // Ecriture des moyennes
        ecritureMoyenne(line2, MOYENNE, tab2, nbGame2, null);

        // Ecriture des victoires
        ecritureStat(line2, VICTOIRE, victory2, nbGame2, null, nbGame2);

        // Taux de victoire
        ecritureTaux(line2, TAUXV, victory2, nbGame2, null, nbGame2);

        // Ecriture des défaites
        ecritureStat(line2, DEFAITE, victory2, nbGame2, null, nbGame2);

        // Taux de défaites
        ecritureTaux(line2, TAUXD, victory2, nbGame2, null, nbGame2);



        WriterCSV.getFileWriter().close();
        WriterCSV.getFileReader().close();
        if(nbGame2==0){nbGame2=1;}
        for (int j = 0; j < tab2.length; j++) {
            System.out.println("joueur " + (j + 1) + " : score moyen " + (tab2[j] / nbGame2) + " | Pourcentage de Victoire : " + (victory2[j]/(float)nbGame2*100) + "% | Pourcentage de défaite : " + (((nbGame2 - victory2[j])/(float)nbGame2)*100)+ "%");
        }
    }

    public void runCsv(UseLogger log, List<String[]> allRows) throws IOException {
        log.getLogger().setLevel(Level.SEVERE);
        float[] tab = new float[6];
        int[] victory = new int[6];

        Plateau plateau = new Plateau(6, new int[]{0, 10, 4, 2, 8, 6}, random, log);
        plateau.allRound(false);
        String[] msg = plateau.getMessageRound().split(" ");
        tab[0] += Integer.parseInt(msg[0]);
        tab[1] += Integer.parseInt(msg[1]);
        tab[2] += Integer.parseInt(msg[2]);
        tab[3] += Integer.parseInt(msg[3]);
        tab[4] += Integer.parseInt(msg[4]);
        tab[5] += Integer.parseInt(msg[5]);
        victory[Integer.parseInt(msg[6])]++;
        String[] line = new String[tab.length+1];

        if (allRows.isEmpty()) {
            String[] nombreGame = new String[tab.length+1];
            nombreGame[0] = NBGAME;
            nombreGame[1] = 1+"";
            WriterCSV.getWriter().writeNext(nombreGame);
            ecritureMoyenne(line, MOYENNE, tab, 1, null);
            ecritureStat(line, VICTOIRE, victory, 1, null, 0);
            ecritureTaux(line, TAUXV, victory, 1, null, 0);
            ecritureStat(line, DEFAITE, victory, 1, null, 0);
            ecritureTaux(line, TAUXD, victory, 1, null, 0);

        } else {
            String[] nombreGame = new String[tab.length+1];
            nombreGame[0] = NBGAME;
            nombreGame[1] = (Integer.parseInt(allRows.get(0)[1]) + 1) + "";
            WriterCSV.getWriter().writeNext(nombreGame);
            String[] previousTab = new String[tab.length+1];
            String[] previousVictory = new String[tab.length+1];
            String[] previousTaux = new String[tab.length+1];
            for (int i = 1; i < (tab.length+1); i++) {
                previousTab[i] = "" + Float.parseFloat(allRows.get(1)[i]);
                previousVictory[i] = "" + Float.parseFloat(allRows.get(2)[i]);
                previousTaux[i] = "" + Float.parseFloat((allRows.get(3)[i].replace("%", "")));
            }
            ecritureMoyenne(line, MOYENNE, tab, 1, previousTab);
            ecritureStat(line, VICTOIRE, victory, 1, previousVictory, Integer.parseInt(allRows.get(0)[1]));
            ecritureTaux(line, TAUXV, victory, 1, previousTaux, Integer.parseInt(allRows.get(0)[1]));
            ecritureStat(line, DEFAITE, victory, 1, previousVictory, Integer.parseInt(allRows.get(0)[1]));
            ecritureTaux(line, TAUXD, victory, 1, previousTaux, Integer.parseInt(allRows.get(0)[1]));
        }
        WriterCSV.getWriter().close();
    }

    public void runDemoCSV(UseLogger log, int nbBot) throws IOException {
        String[] titre = new String[3];
        String[] vide = new String[3];
        String[] entete = new String[] {"Joueur","Agressivité","Score"};

        for (int i=0; i<titre.length; i++) {
            if (i==0) {
                titre[i] = "Éxecution d'une partie";
                vide[i] = " ";
            } else {
                titre[i] = " ";
                vide[i] = " ";
            }
        }
        WriterCSV.getWriter().writeNext(titre);
        WriterCSV.getWriter().writeNext(vide);
        WriterCSV.getWriter().writeNext(entete);

        int[] agressivite = new int[nbBot];
        for (int i=0; i<agressivite.length; i++) {
            agressivite[i] = this.random.nextInt(0,10);
        }

        Plateau plateau = new Plateau(6, agressivite, this.random, log);
        plateau.allRound(true);

        String[] line = new String[3];
        int[] score = plateau.scoreBot();

        for (int i=0; i<nbBot; i++) {
            line[0] = "j" + (i+1);
            line[1] = "" + agressivite[i];
            line[2] = "" + score[i];
            WriterCSV.getWriter().writeNext(line);
        }
        WriterCSV.getFileWriter().close();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void ecritureMoyenne(String[] line, String nomLigne, float[] tab, int nbGame, String[] previous) {
        line[0] = nomLigne;
        if (previous == null) {
            for (int i = 0; i < tab.length; i++) {
                line[i + 1] = "" + tab[i] / nbGame;
            }
        } else {
            for (int i = 0; i < tab.length; i++) {
                float ant = Float.parseFloat(previous[i + 1]);
                line[i + 1] = "" + (((tab[i] / nbGame) + ant) / 2);
            }
        }
        WriterCSV.getWriter().writeNext(line);
    }

    public void ecritureStat(String[] line, String nomLigne, int[] victory, int nbGame, String[] previous, int previousNbGame) {
        line[0] = nomLigne;
        if (previous == null) {
            for (int i = 0; i < victory.length; i++) {
                if (nomLigne.equals(VICTOIRE)) {
                    line[i + 1] = "" + victory[i];
                } else {
                    line[i + 1] = "" + (nbGame - victory[i]);
                }
            }
        } else {
            for (int i = 0; i < victory.length; i++) {
                int nombreVictoire = victory[i] + (int) Float.parseFloat(previous[i + 1]);
                if (nomLigne.equals(VICTOIRE)) {
                    line[i + 1] = "" + nombreVictoire;
                } else {
                    line[i + 1] = "" + (nbGame+previousNbGame-nombreVictoire);
                }

            }
        }
        WriterCSV.getWriter().writeNext(line);
    }

    public void ecritureTaux(String[] line, String nomLigne, int[] victory, int nbGame, String[] previous, int previousNbGame) {
        line[0] = nomLigne;
        if (previous == null) {
            for (int i = 0; i < victory.length; i++) {
                float victoryPercent = (((float) victory[i]) / nbGame) * 100;
                if (nomLigne.equals(TAUXV)) {
                    line[i + 1] = victoryPercent + "%";
                } else if (nomLigne.equals(TAUXD)) {
                    line[i + 1] = (100 - victoryPercent) + "%";
                }
            }
        } else {
            for (int i = 0; i < victory.length; i++) {
                float victoryPercent = ((((float) victory[i]) * nbGame + Float.parseFloat(previous[i + 1])/100 * previousNbGame) / (nbGame + previousNbGame)) *100;
                if(nomLigne.equals(TAUXD)){
                    victoryPercent = 100 - victoryPercent;
                }
                line[i + 1] = victoryPercent + "%";
            }
        }

        WriterCSV.getWriter().writeNext(line);
    }
}