package biblioteca;

import biblioteca.data.DatabaseConnection;
import biblioteca.model.Bibliotecario;
import biblioteca.model.Libro;
import biblioteca.model.Usuario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Conexión a la base de datos establecida.");

            // Ruta relativa de los scripts SQL
            String createScriptPath = "src/main/java/biblioteca/data/create_script.sql";
            String insertScriptPath = "src/main/java/biblioteca/data/insert_script.sql";

            // Ejecutar scripts de creación e inserción
            ejecutarScriptSQL(connection, createScriptPath);
            ejecutarScriptSQL(connection, insertScriptPath);

            System.out.println("Scripts SQL ejecutados correctamente.");

            // Simulación de préstamo
            realizarPrestamo(1, 1, 1, connection); // Préstamo de libro (ID 1) al usuario (ID 1) por bibliotecario (ID 1)

            // Mostrar usuarios, bibliotecarios y libros
            mostrarDatos(connection, "usuario");
            mostrarDatos(connection, "bibliotecario");
            mostrarLibros(connection);

        } catch (SQLException e) {
            System.err.println("Error de conexión en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al ejecutar los scripts SQL: " + e.getMessage());
        }
    }

    // Método para ejecutar un script SQL desde un archivo en el sistema de archivos
    private static void ejecutarScriptSQL(Connection connection, String rutaArchivoSQL) throws Exception {
        StringBuilder script = new StringBuilder();
        File archivoSQL = new File(rutaArchivoSQL);

        try (BufferedReader br = new BufferedReader(new FileReader(archivoSQL))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                script.append(linea).append(System.lineSeparator());
                // Ejecutar si la línea contiene un punto y coma, indicando el final de una declaración
                if (linea.trim().endsWith(";")) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(script.toString());
                        script.setLength(0); // Limpiar el StringBuilder para la próxima declaración
                    } catch (SQLException e) {
                        System.err.println("Error al ejecutar la declaración: " + script);
                        throw e; // Relanzar la excepción después de imprimir el error
                    }
                }
            }
        }
    }

    // Simulación de préstamo de un libro
    private static void realizarPrestamo(int idLibro, int idUsuario, int idBibliotecario, Connection connection) throws SQLException {
        Libro libro = obtenerEntidadDesdeDB(connection, "libro", idLibro, Libro.class);
        Usuario usuario = obtenerEntidadDesdeDB(connection, "usuario", idUsuario, Usuario.class);
        Bibliotecario bibliotecario = obtenerEntidadDesdeDB(connection, "bibliotecario", idBibliotecario, Bibliotecario.class);

        if (!libro.isDisponible()) {
            System.out.println("El libro '" + libro.getTitulo() + "' no está disponible para préstamo.");
            return;
        }

        LocalDate localDate = LocalDate.now();
        Date fechaPrestamo = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        String sql = "INSERT INTO prestamo (id_libro, id_usuario, fecha_prestamo, id_bibliotecario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, libro.getId());
            statement.setInt(2, usuario.getId());
            statement.setDate(3, new java.sql.Date(fechaPrestamo.getTime()));
            statement.setInt(4, bibliotecario.getId());
            statement.executeUpdate();

            // Marcar libro como no disponible
            marcarLibroComoNoDisponible(connection, libro.getId());

            System.out.println("Préstamo realizado: Libro '" + libro.getTitulo() + "' prestado a " + usuario.getNombreCompleto() + " por " + bibliotecario.getNombreCompleto());
        }
    }

    // Método para marcar un libro como no disponible
    private static void marcarLibroComoNoDisponible(Connection connection, int idLibro) throws SQLException {
        String sql = "UPDATE libro SET disponible = FALSE WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idLibro);
            statement.executeUpdate();
        }
    }

    // Método genérico para obtener entidades desde la base de datos
    private static <T> T obtenerEntidadDesdeDB(Connection connection, String tabla, int id, Class<T> tipoEntidad) throws SQLException {
        String sql = "SELECT * FROM " + tabla + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    if (tipoEntidad == Usuario.class) {
                        return tipoEntidad.cast(new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido")));
                    } else if (tipoEntidad == Bibliotecario.class) {
                        return tipoEntidad.cast(new Bibliotecario(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido")));
                    } else if (tipoEntidad == Libro.class) {
                        return tipoEntidad.cast(new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("isbn"), rs.getBoolean("disponible")));
                    }
                }
            }
        }
        throw new SQLException(tipoEntidad.getSimpleName() + " con ID " + id + " no encontrado.");
    }

    // Método para mostrar datos generales (usuarios o bibliotecarios)
    private static void mostrarDatos(Connection connection, String tabla) throws SQLException {
        String sql = "SELECT id, nombre, apellido FROM " + tabla;
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            System.out.println("Datos de " + tabla + ":");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre") + ", Apellido: " + rs.getString("apellido"));
            }
        }
    }

    // Método para mostrar los libros
    private static void mostrarLibros(Connection connection) throws SQLException {
        String sql = "SELECT id, titulo, autor, isbn, disponible FROM libro";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            System.out.println("Libros en la base de datos:");
            while (rs.next()) {
                String disponibilidad = rs.getBoolean("disponible") ? "Disponible" : "No disponible";
                System.out.println("ID: " + rs.getInt("id") + ", Título: " + rs.getString("titulo") + ", Autor: " + rs.getString("autor") +
                        ", ISBN: " + rs.getString("isbn") + ", Estado: " + disponibilidad);
            }
        }
    }
}



