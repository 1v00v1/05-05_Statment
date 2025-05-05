package zadaci;

//TODO Napiši Java konzolnu aplikaciju koja unosi 10 novih država u bazu. Nazivi država se nalaze u kolekciji tipa ArrayList.
// Koristite PreparedStatement.
// Na bazi pripremite pohranjenu proceduru za brisanje svih država čiji ID je veći ili jednak od zadane vrijednosti.
// Pozovite tu pohranjenu procedure iz Java koda i pobrišite sve države koje ste kreirali u zadatku iznad.


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> države = new ArrayList<>(
                Arrays.asList("Kina", "Francuska", "Japan", "Indija", "Novi Zeland", "Finska", "Norveška", "Island", "Peru", "Nepal"));

        try (Connection conn = createDataSource().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Drzava (Naziv) VALUES(?)");
            for (String naziv : države) {
                ps.setString(1, naziv);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        deleteById(48);

    }


    public static void deleteById(int id) {
        try (Connection connection = createDataSource().getConnection()) {
            System.out.println("Uspješno ste spojeni na bazu podataka");
            CallableStatement cs = connection.prepareCall("{call BrisiSveIznad(?)}");
            cs.setInt(1, id);
            cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DataSource createDataSource() {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("localhost");
        //ds.setPortNumber(1433);
        ds.setDatabaseName("AdventureWorksOBP");
        ds.setUser("sa");
        ds.setPassword("SQL");
        ds.setEncrypt(false);

        return ds;
    }
}
