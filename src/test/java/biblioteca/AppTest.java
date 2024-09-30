package biblioteca;

import biblioteca.data.DatabaseConnection;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.SQLException;

public class AppTest extends TestCase {
    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    // Método de prueba para verificar la conexión a la base de datos
    public void testDatabaseConnection() {
        Connection connection = null;
        try {
            // Intenta obtener la conexión
            connection = DatabaseConnection.getConnection();
            assertNotNull("La conexión no debería ser nula.", connection);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            fail("Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }
}

