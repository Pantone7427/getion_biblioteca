package biblioteca.model;

import biblioteca.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    // Lista para simular una base de datos de usuarios
    private static List<Usuario> usuarios = new ArrayList<>();

    // Create: Añadir un nuevo usuario
    public static void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario añadido: " + usuario);
    }

    // Read: Obtener todos los usuarios
    public static List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // Update: Actualizar un usuario existente
    public static void updateUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido) {
        usuario.setNombre(nuevoNombre);  // Supongamos que añadimos setters en Usuario
        usuario.setApellido(nuevoApellido);
        System.out.println("Usuario actualizado: " + usuario);
    }

    // Delete: Eliminar un usuario
    public static void deleteUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        System.out.println("Usuario eliminado: " + usuario);
    }
}

