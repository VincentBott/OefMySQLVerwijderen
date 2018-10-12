package org.betavzw.db;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class App {

    private static final String CONN_STRING = "jdbc:mysql://localhost/testdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels";

    private static final String DELETE = "DELETE from persoon where id = ?";


    public static void main(String[] args) throws IOException, SQLException {

        Scanner scanner = new Scanner(System.in);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");


        try (Connection conn = DriverManager.getConnection(CONN_STRING, "root", "VDAB");

             Statement stm = conn.createStatement();

             ResultSet rs = stm.executeQuery("SELECT * FROM persoon")) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String voornaam = rs.getString("voornaam");


                LocalDate geboortedatum = rs.getDate("geboortedatum").toLocalDate();


                System.out.printf("%d) %s (%s)%n", id, voornaam, geboortedatum.format(formatter));

            }


            System.out.print("\nWelke persoon wil je verwijderen? ");

            int inputID = Integer.parseInt(scanner.nextLine());

            PreparedStatement prep = conn.prepareStatement(DELETE);

            prep.setInt(1, inputID);


            if (prep.executeUpdate() == 0)
                System.out.printf("%nRecord met id %d is niet gevonden.%n", inputID);

            else
                System.out.printf("%nRecord met id %d is verwijderd.%n", inputID);


        } catch (SQLException e) {

            e.printStackTrace();
        }

    }
}