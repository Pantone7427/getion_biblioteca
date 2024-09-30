package biblioteca.model;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponible;

    // Constructor
    public Libro(int id, String titulo, String autor, String isbn, boolean disponible) {
        this.id = id;
        setTitulo(titulo);
        setAutor(autor);
        setIsbn(isbn);
        this.disponible = disponible;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        validarTextoNoVacio(titulo, "El título no puede ser nulo o vacío.");
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        validarTextoNoVacio(autor, "El autor no puede ser nulo o vacío.");
        this.autor = autor;
    }

    public void setIsbn(String isbn) {
        validarTextoNoVacio(isbn, "El ISBN no puede ser nulo o vacío.");
        this.isbn = isbn;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Métodos de negocio
    public void prestar() {
        if (!disponible) {
            throw new IllegalStateException("El libro ya está prestado");
        }
        disponible = false;
    }

    public void devolver() {
        if (disponible) {
            throw new IllegalStateException("El libro no está prestado");
        }
        disponible = true;
    }

    // Método auxiliar para validación
    private void validarTextoNoVacio(String texto, String mensajeError) {
        if (texto == null || texto.isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
    }

    // Método toString
    @Override
    public String toString() {
        return String.format("%s (%s) - %s", titulo, isbn, autor);
    }
}
