package com.opencsv;

import java.io.*;

public class WriterCSV {
    private static final String SEPARATOR = "\n";
    private static File fileCSV;
    private static FileWriter fileWriter;
    private static FileReader fileReader;
    private static CSVWriter writer;
    static final String BOTVSBOT = "./stats/botVSbot.csv";
    static final String GAMESTAT = "./stats/gamestats.csv";
    static final String DEMO = "./stats/demo.csv";

    private WriterCSV() {
        throw new IllegalStateException("WriterCSV class");
    }

    public static void setUp2thousands(int nbBot) throws IOException {
        String[] header = new String[nbBot+1];
        for (int i = 0; i< header.length; i++) {
            if (i==0) {
                header[i] = "Liste des joueurs : ";
            } else {
                header[i] = "j" + i;
            }
        }
        fileWriter = new FileWriter(BOTVSBOT);
        fileReader = new FileReader(BOTVSBOT);
        writer = new CSVWriter(fileWriter);
        writer.writeNext(header);
        fileWriter.append(SEPARATOR);
    }

    public static void setUpCSV() throws IOException {
        fileCSV = new File(GAMESTAT);
        fileReader = new FileReader(fileCSV);
        fileWriter = new FileWriter(fileCSV);
        writer = new CSVWriter(fileWriter);
    }

    public static void setUpDemoCSV() throws IOException {
        fileCSV = new File(DEMO);
        fileReader = new FileReader(fileCSV);
        fileWriter = new FileWriter(fileCSV);
        writer = new CSVWriter(fileWriter);
    }

    public static FileWriter getFileWriter() {
        return fileWriter;
    }

    public static FileReader getFileReader() {
        return fileReader;
    }

    public static CSVWriter getWriter() {
        return writer;
    }
}