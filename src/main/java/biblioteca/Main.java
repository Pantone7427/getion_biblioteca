package biblioteca;

import biblioteca.data.DatabaseConnection;
import biblioteca.model.Bibliotecario;
import biblioteca.model.Libro;
import biblioteca.model.Usuario;
import biblioteca.model.Prestamo;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Conexión a la base de datos establecida.");

            // Insertar registros en las tablas
            insertarUsuarios(connection);
            insertarBibliotecarios(connection);
            insertarLibros(connection);

            // Crear instancias desde la base de datos
            Usuario usuario = obtenerUsuarioDesdeDB(connection, 1); // Obtener usuario con ID 1
            Bibliotecario bibliotecario = obtenerBibliotecarioDesdeDB(connection, 1); // Obtener bibliotecario con ID 1
            Libro libro = obtenerLibroDesdeDB(connection, 1); // Obtener libro con ID 1

            // Simulación de préstamo y devolución
            realizarPrestamo(libro, usuario, bibliotecario, connection);

            // Mostrar usuarios y bibliotecarios desde la base de datos
            mostrarUsuarios(connection);
            mostrarBibliotecarios(connection);

        } catch (SQLException e) {
            System.err.println("Error de conexión en la base de datos: " + e.getMessage());
        }
    }

    // Insertar datos en la tabla usuario
    private static void insertarUsuarios(Connection connection) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, apellido) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Juan");
            statement.setString(2, "Pérez");
            statement.executeUpdate();

            statement.setString(1, "Ana");
            statement.setString(2, "García");
            statement.executeUpdate();

            statement.setString(1, "Luis");
            statement.setString(2, "Martínez");
            statement.executeUpdate();

            statement.setString(1, "María");
            statement.setString(2, "López");
            statement.executeUpdate();

            statement.setString(1, "Carlos");
            statement.setString(2, "Sánchez");
            statement.executeUpdate();

            System.out.println("Usuarios insertados correctamente.");
        }
    }

    // Insertar datos en la tabla bibliotecario
    private static void insertarBibliotecarios(Connection connection) throws SQLException {
        String sql = "INSERT INTO bibliotecario (nombre, apellido) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Luis");
            statement.setString(2, "Martínez");
            statement.executeUpdate();

            statement.setString(1, "Carmen");
            statement.setString(2, "Fernández");
            statement.executeUpdate();

            statement.setString(1, "Diego");
            statement.setString(2, "Rodríguez");
            statement.executeUpdate();

            statement.setString(1, "Laura");
            statement.setString(2, "Gómez");
            statement.executeUpdate();

            statement.setString(1, "Elena");
            statement.setString(2, "Ruiz");
            statement.executeUpdate();

            System.out.println("Bibliotecarios insertados correctamente.");
        }
    }

    // Insertar datos en la tabla libro
    private static void insertarLibros(Connection connection) throws SQLException {
        String sql = "INSERT INTO libro (titulo, autor, isbn, disponible) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "El Quijote");
            statement.setString(2, "Miguel de Cervantes");
            statement.setString(3, "1234567890123");
            statement.setBoolean(4, true);
            statement.executeUpdate();

            statement.setString(1, "Cien Años de Soledad");
            statement.setString(2, "Gabriel García Márquez");
            statement.setString(3, "1234567890124");
            statement.setBoolean(4, true);
            statement.executeUpdate();

            statement.setString(1, "1984");
            statement.setString(2, "George Orwell");
            statement.setString(3, "1234567890125");
            statement.setBoolean(4, true);
            statement.executeUpdate();

            statement.setString(1, "Don Juan Tenorio");
            statement.setString(2, "José Zorrilla");
            statement.setString(3, "1234567890126");
            statement.setBoolean(4, true);
            statement.executeUpdate();

            statement.setString(1, "La Casa de los Espíritus");
            statement.setString(2, "Isabel Allende");
            statement.setString(3, "1234567890127");
            statement.setBoolean(4, true);
            statement.executeUpdate();

            System.out.println("Libros insertados correctamente.");
        }
    }

    // Obtener un usuario desde la base de datos
    private static Usuario obtenerUsuarioDesdeDB(Connection connection, int idUsuario) throws SQLException {
        String sql = "SELECT id, nombre, apellido FROM usuario WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
                }
            }
        }
        throw new SQLException("Usuario con ID " + idUsuario + " no encontrado.");
    }

    // Obtener un bibliotecario desde la base de datos
    private static Bibliotecario obtenerBibliotecarioDesdeDB(Connection connection, int idBibliotecario) throws SQLException {
        String sql = "SELECT id, nombre, apellido FROM bibliotecario WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idBibliotecario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Bibliotecario(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
                }
            }
        }
        throw new SQLException("Bibliotecario con ID " + idBibliotecario + " no encontrado.");
    }

    // Obtener un libro desde la base de datos
    private static Libro obtenerLibroDesdeDB(Connection connection, int idLibro) throws SQLException {
        String sql = "SELECT id, titulo, autor, isbn, disponible FROM libro WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idLibro);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("isbn"), rs.getBoolean("disponible"));
                }
            }
        }
        throw new SQLException("Libro con ID " + idLibro + " no encontrado.");
    }

    // Simulación de préstamo
    private static void realizarPrestamo(Libro libro, Usuario usuario, Bibliotecario bibliotecario, Connection connection) {
        try {
            LocalDate localDate = LocalDate.now();
            Date fechaPrestamo = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Registrar el préstamo en la base de datos
            registrarPrestamoEnDB(libro, usuario, bibliotecario, connection);

            System.out.println("Préstamo realizado: Libro '" + libro.getTitulo() + "' prestado a " + usuario.getNombreCompleto() + " por " + bibliotecario.getNombreCompleto());
        } catch (SQLException e) {
            System.err.println("Error al registrar el préstamo: " + e.getMessage());
        }
    }

    // Registrar un préstamo en la base de datos
    private static void registrarPrestamoEnDB(Libro libro, Usuario usuario, Bibliotecario bibliotecario, Connection connection) throws SQLException {
        String sql = "INSERT INTO prestamo (id_libro, id_usuario, fecha_prestamo, id_bibliotecario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, libro.getId());
            statement.setInt(2, usuario.getId());
            statement.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Fecha actual
            statement.setInt(4, bibliotecario.getId());
            statement.executeUpdate();
        }
    }

    // Mostrar usuarios desde la base de datos
    private static void mostrarUsuarios(Connection connection) throws SQLException {
        String sql = "SELECT id, nombre, apellido FROM usuario";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            System.out.println("Usuarios en la base de datos:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre") + ", Apellido: " + rs.getString("apellido"));
            }
        }
    }

    // Mostrar bibliotecarios desde la base de datos
    private static void mostrarBibliotecarios(Connection connection) throws SQLException {
        String sql = "SELECT id, nombre, apellido FROM bibliotecario";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            System.out.println("Bibliotecarios en la base de datos:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre") + ", Apellido: " + rs.getString("apellido"));
            }
        }
    }
}
