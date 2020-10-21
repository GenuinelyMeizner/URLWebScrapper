package com.company;

import jdk.jshell.spi.SPIResolutionException;

import java.sql.*;
import java.util.ArrayList;

public class JDBCWriter {
    private Connection connection = null;

    public boolean setConnection() {
        final String url = "jdbc:mysql://localhost:3306/urlread?serverTimezone=UTC";
        boolean bres = false;
        try  {
            connection = DriverManager.getConnection(url, "admin", "admin");
                    bres = true;
        } catch (SQLException sqlerr) {
            System.out.println("No Connection" + sqlerr);
        }
        return bres;
    }

    public int searchDB(String url, String word) {
        int res = 0;
        String selectSql = "SELECT count(*) from urlreads where url like "  +'"' + url + '"' + " and line like " + '"' + word + '"';
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resset = preparedStatement.executeQuery(selectSql);
            if (resset.next()) {
                String str = "" + resset.getObject(1);
                res = Integer.parseInt(str);
                System.out.println("Found=" + res);
            }
        } catch (SQLException sqlerr) {
            System.out.println("Error in search=" + sqlerr.getMessage());
        }
        return res;
    }

    public int writeLines(ArrayList<String> aLst, String aurl) {
        String insertSql = "INSERT INTO urlreads (url, line, linelength) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement;
        int res = 0;
        for (String line: aLst) {
            try {
                preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.setString(1, aurl);
                preparedStatement.setString(2, line);
                preparedStatement.setString(3, "" + line.length());
                int rowcount = preparedStatement.executeUpdate();
                //System.out.println("Indsat række=" + rowcount + " rows");
                res = res + rowcount;
            } catch (SQLException sqlerr) {
                System.out.println("Fejl i INSERT=" + sqlerr.getMessage());
            }
        }
        return res;
    }
}
