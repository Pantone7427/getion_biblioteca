package biblioteca.model;

public class Usuario extends Persona {
    private int id;
    private int librosPrestados;

    public Usuario(int id, String nombre, String apellido) {
        super(nombre, apellido);
        this.id = id;
        this.librosPrestados = 0;
    }

    public int getId() {
        return id;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Usuario: " + getNombreCompleto());
        System.out.println("Libros Prestados: " + librosPrestados);
    }

    public int getLibrosPrestados() {
        return librosPrestados;
    }

    public void prestarLibro() {
        librosPrestados++;
    }

    public void devolverLibro() {
        if (librosPrestados > 0) {
            librosPrestados--;
        }
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }

    // Métodos setters para nombre y apellido
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío.");
        }
        this.apellido = apellido;
    }
}

