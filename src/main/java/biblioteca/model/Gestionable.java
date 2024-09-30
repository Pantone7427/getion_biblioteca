package biblioteca.model;

public interface Gestionable {
    void prestarLibro(Usuario usuario, Libro libro);
    void devolverLibro(Usuario usuario, Libro libro);
}