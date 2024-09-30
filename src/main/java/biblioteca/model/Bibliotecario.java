package biblioteca.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

public class Bibliotecario extends Persona implements Gestionable {
    private int id;
    private List<Prestamo> prestamos;

    public Bibliotecario(int id, String nombre, String apellido) {
        super(nombre, apellido);
        this.id = id;
        this.prestamos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Bibliotecario: " + getNombreCompleto());
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }

    @Override
    public void prestarLibro(Usuario usuario, Libro libro) {
        if (libro.isDisponible()) {
            Prestamo prestamo = new Prestamo(libro, usuario, new Date());
            prestamos.add(prestamo);
            libro.prestar();
            System.out.println("Prestando el libro " + libro.getTitulo() + " a " + usuario.getNombre());
        } else {
            System.out.println("El libro " + libro.getTitulo() + " no está disponible.");
        }
    }

    @Override
    public void devolverLibro(Usuario usuario, Libro libro) {
        Iterator<Prestamo> iterator = prestamos.iterator();
        while (iterator.hasNext()) {
            Prestamo prestamo = iterator.next();
            if (prestamo.getUsuario().equals(usuario) && prestamo.getLibro().equals(libro)) {
                iterator.remove();
                libro.devolver();
                System.out.println("Libro " + libro.getTitulo() + " devuelto por " + usuario.getNombre());
                return;
            }
        }
        System.out.println("No se encontró el préstamo del libro " + libro.getTitulo() + " para " + usuario.getNombre());
    }

    // Método para obtener la lista de préstamos
    public List<Prestamo> getPrestamos() {
        return new ArrayList<>(prestamos); // Devuelve una copia para proteger la lista original
    }

    // Método para agregar un préstamo
    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
    }
}
