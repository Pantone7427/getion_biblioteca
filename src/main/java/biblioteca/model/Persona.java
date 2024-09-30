package biblioteca.model;

public abstract class Persona {
    // Atributos
    protected String nombre;
    protected String apellido;

    // Constructor
    public Persona(String nombre, String apellido) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío.");
        }
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public abstract void mostrarDatos();

    @Override
    public String toString() {
        return String.format("Nombre: %s, Apellido: %s", nombre, apellido);
    }

    public String getNombreCompleto() {
        return String.format("%s %s", nombre, apellido);
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}

