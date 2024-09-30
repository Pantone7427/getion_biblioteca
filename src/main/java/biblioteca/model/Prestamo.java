package biblioteca.model;

import java.util.Date;

public class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private Date fechaPrestamo;
    private Date fechaDevolucion;  // Fecha de devolución estimada
    private Date fechaDevolucionReal;  // Fecha de devolución real
    private Bibliotecario bibliotecario;  // Bibliotecario que realizó el préstamo

    // Constructor principal
    public Prestamo(Libro libro, Usuario usuario, Date fechaPrestamo, Date fechaDevolucion, Bibliotecario bibliotecario) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.bibliotecario = bibliotecario;
        this.fechaDevolucionReal = null;  // Inicialmente, el libro no ha sido devuelto
    }

    // Constructor alternativo sin fecha de devolución y bibliotecario
    public Prestamo(Libro libro, Usuario usuario, Date fechaPrestamo) {
        this(libro, usuario, fechaPrestamo, null, null);
    }

    // Métodos get
    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public Date getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    // Registra la fecha de devolución real
    public void registrarDevolucion() {
        this.fechaDevolucionReal = new Date();
    }

    // Obtiene la información completa del préstamo
    public String obtenerInformacionCompleta() {
        StringBuilder informacion = new StringBuilder();
        informacion.append(String.format("Libro: %s\n", libro.getTitulo()));
        informacion.append(String.format("Autor: %s\n", libro.getAutor()));
        informacion.append(String.format("Usuario: %s\n", usuario.getNombreCompleto()));
        informacion.append(String.format("Fecha de Préstamo: %s\n", fechaPrestamo));
        informacion.append(String.format("Fecha de Devolución Estimada: %s\n", fechaDevolucion != null ? fechaDevolucion : "No especificada"));
        informacion.append(String.format("Bibliotecario: %s %s\n", bibliotecario != null ? bibliotecario.getNombre() : "No especificado", bibliotecario != null ? bibliotecario.getApellido() : ""));

        if (fechaDevolucionReal != null) {
            informacion.append(String.format("Fecha de Devolución Real: %s\n", fechaDevolucionReal));
        } else {
            informacion.append("El libro aún no ha sido devuelto.\n");
        }

        return informacion.toString();
    }
}
