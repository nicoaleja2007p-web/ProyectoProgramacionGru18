package modelo;

public class Usuario {

    private int id;
    private String nombre;
    private String email;
    private String contrasena;

    public Usuario(int id, String nombre, String email, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }

    public boolean iniciarSesion(String emailIngresado, String contrasenaIngresada) {
        if (this.email.equals(emailIngresado) && this.contrasena.equals(contrasenaIngresada)) {
            return true;
        }
        return false;
    }

    public void cerrarSesion() {
        System.out.println("Sesion cerrada para el usuario: " + nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + "]";
    }
}
