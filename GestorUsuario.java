package negocio;

import modelo.Usuario;
import java.util.ArrayList;

public class GestorUsuario {

    private ArrayList<Usuario> usuarios;
    private Usuario usuarioActivo;
    private int intentosFallidos;

    public GestorUsuario() {
        this.usuarios = new ArrayList<Usuario>();
        this.usuarioActivo = null;
        this.intentosFallidos = 0;
    }

    public boolean login(String email, String contrasena) {
        if (intentosFallidos >= 3) {
            System.out.println("Cuenta bloqueada. Demasiados intentos fallidos.");
            return false;
        }

        if (email == null || email.equals("") || contrasena == null || contrasena.equals("")) {
            System.out.println("Error: Email y contrasena son obligatorios.");
            return false;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            if (usuario.iniciarSesion(email, contrasena)) {
                usuarioActivo = usuario;
                intentosFallidos = 0;
                System.out.println("Sesion iniciada: " + usuario.getNombre());
                return true;
            }
        }

        intentosFallidos = intentosFallidos + 1;
        System.out.println("Credenciales incorrectas. Intento " + intentosFallidos + " de 3.");
        return false;
    }

    public void logout() {
        if (usuarioActivo != null) {
            usuarioActivo.cerrarSesion();
            usuarioActivo = null;
            intentosFallidos = 0;
        }
    }

    public boolean registrarUsuario(String nombre, String email, String contrasena) {
        if (nombre == null || nombre.equals("") || email == null || email.equals("") || contrasena == null || contrasena.equals("")) {
            System.out.println("Error: Todos los campos son obligatorios.");
            return false;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(email)) {
                System.out.println("Error: Ya existe un usuario con ese email.");
                return false;
            }
        }

        int nuevoId = usuarios.size() + 1;
        Usuario nuevoUsuario = new Usuario(nuevoId, nombre, email, contrasena);
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario registrado exitosamente.");
        return true;
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public boolean haySesionActiva() {
        if (usuarioActivo != null) {
            return true;
        }
        return false;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }
}
