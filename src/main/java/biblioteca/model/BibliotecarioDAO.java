package biblioteca.model;

import biblioteca.data.DatabaseConnection;
import biblioteca.model.Usuario;
import biblioteca.model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class BibliotecarioDAO {

    public void prestarLibro(Usuario usuario, Libro libro) {
        String sql = "INSERT INTO prestamos (usuario_id, libro_id, fecha_prestamo) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setInt(2, libro.getId());
            // Convertir LocalDate a java.sql.Date
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al prestar el libro: " + e.getMessage());
        }
    }

    public void devolverLibro(Usuario usuario, Libro libro) {
        String sql = "UPDATE prestamos SET fecha_devolucion = ? WHERE usuario_id = ? AND libro_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Convertir LocalDate a java.sql.Date
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(2, usuario.getId());
            preparedStatement.setInt(3, libro.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al devolver el libro: " + e.getMessage());
        }
    }
}
