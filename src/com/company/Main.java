package com.company;

import java.util.ArrayList;

public class Main {

    public static void main2(String[] args) {

        String url = "https://www.dr.dk/";
        url = "https://ekstrabladet.dk/";
        url = "https://www.edc.dk/";
        url = "https://www.bt.dk/";
        url = "https://www.jobindex.dk/";
        url = "https://www.foxnews.com/";
        URLReader reader = new URLReader();
        ArrayList<String> lst = new ArrayList<>();
        lst = reader.readUrl(url);
        System.out.println("antal linjer=" + lst.size());

        JDBCWriter jdbcWriter = new JDBCWriter();
        boolean gotCon = jdbcWriter.setConnection();
        System.out.println("Got Connection=" + gotCon);
        int rowcnt = jdbcWriter.writeLines(lst, url);
        System.out.println("Rækker skrevet=" + rowcnt);

        //Unfinished jobindex Scrapper
        /*for (int i=2; i<10; i++) {
            url = "https://www.jobindex.dk/jobsoegning/it/systemudvikling?q=java";
            url = "https://www.jobindex.dk/jobsoegning/it/systemudvikling?page=" + i + "2q=java";
            lst = reader.readUrl(url);
            System.out.println("Læst jobindex side=" + i + " linjer" + lst.size());
            rowcnt = jdbcWriter.writeLines(lst, url);
        }*/

        //IDK
        /*for  (String str: lst) {
            System.out.println("str length=" + str.length());
            System.out.println(str);
        }*/
    }

    public static void main(String[] args) {
        URLGui urlGui = new URLGui();
        urlGui.createGui();
    }
}
