package model;

public class UsuarioSistema {

    private int idUsuarioSistema;
    private String primerNombre;
    private String primerApellido;
    private String dni;
    private String rol;
    private String contrasena; 

    public UsuarioSistema() {
    }

    public UsuarioSistema(int idUsuarioSistema, String primerNombre, String primerApellido, String dni, String rol, String contrasena) {
        this.idUsuarioSistema = idUsuarioSistema;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.dni = dni;
        this.rol = rol;
        this.contrasena = contrasena;
    }

    // Getters y Setters...
    public int getIdUsuarioSistema() {
        return idUsuarioSistema;
    }

    public void setIdUsuarioSistema(int idUsuarioSistema) {
        this.idUsuarioSistema = idUsuarioSistema;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }
    
    // ... (resto de getters y setters)
    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}