package predavanje;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class PripremljenaProcedura {
    public static void main(String[] args) {

        DataSource ds = createDataSource();
        System.out.println(dohvatiKupca(45));


    }

    public static String dohvatiKupca(int id) {
        try (Connection conn = createDataSource().getConnection()) {
            System.out.println("Uspješno ste spojeni na bazu podataka");

            CallableStatement cs = conn.prepareCall("{call DohvatiImeKupca(?,?)}");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.NVARCHAR);
            cs.executeUpdate();
            return cs.getString(2);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
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
