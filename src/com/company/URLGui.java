package com.company;

import javax.swing.*;
import java.util.ArrayList;

public class URLGui {

    JTextField txtUrl;
    JTextField txtResult;
    JLabel lblcount;
    ArrayList<String> urlList;
    JDBCWriter jdbcWriter;
    JButton pbSavetoDB;
    JButton pbSearch;

    public URLGui() {
        jdbcWriter = new JDBCWriter();
        urlList = new ArrayList<>();
        urlList.add("Hej do");
    }


    public void connect() {
        boolean gotCon = jdbcWriter.setConnection();
        System.out.println("Got Connection=" + gotCon);
        pbSavetoDB.setEnabled(gotCon);
        pbSearch.setEnabled(gotCon);
    }

    public void retrieveURL() {
        String url = txtUrl.getText();
        System.out.println(url);
        URLReader reader = new URLReader();
        urlList.clear();
        urlList = reader.readUrl(url);
        System.out.println("antal linjer=" + urlList.size());
        txtResult.setText("" + urlList.size());
        String[] splitArr = new String[1];

        for (String testStr : urlList) {
            if (testStr.length() > 15000) {
                System.out.println("Long String Found=" + testStr.length());
                splitArr = testStr.split("}");
                //System.out.println(splitArr[0]);
                //System.out.println(splitArr[1]);
                System.out.println("splitArr length=" + splitArr.length);
            }
        }
        for (String newStr : splitArr) {
            urlList.add(newStr);
        }
    }

    public void saveToMySQL() {
        String url = txtUrl.getText();
        int rowcnt = jdbcWriter.writeLines(urlList, url);
        System.out.println("Lines Saved=" + rowcnt);
    }

    public void searchDB() {
        String url = txtUrl.getText();
        String word = txtResult.getText();
        int cnt = jdbcWriter.searchDB(url, word);
        lblcount.setText("" + cnt);
    }

    public void createGui() {
        final JFrame frame = new JFrame("URL Gui");
        JPanel panelTop = new JPanel();
        JButton pbConnect = new JButton("Connect");
        JButton pbRetrieveUrl = new JButton("Retrieve URL");
        pbSearch = new JButton("SearchDB");
        pbSavetoDB = new JButton("Save");
        txtUrl = new JTextField("", 50);
        txtResult = new JTextField("", 20);
        lblcount = new JLabel("Count");

        frame.add(panelTop);
        panelTop.add(pbConnect);
        panelTop.add(pbRetrieveUrl);
        panelTop.add(pbSavetoDB);
        panelTop.add(pbSearch);
        panelTop.add(txtUrl);
        panelTop.add(txtResult);
        panelTop.add(lblcount);

        pbConnect.addActionListener(a -> connect());
        pbRetrieveUrl.addActionListener(a -> retrieveURL());
        pbSavetoDB.addActionListener(a -> saveToMySQL());
        pbSearch.addActionListener(a -> searchDB());

        pbSavetoDB.setEnabled(false);
        pbSearch.setEnabled(false);

        frame.pack();
        frame.setBounds(100, 100, 600, 200);
        frame.setVisible(true);
    }
}
