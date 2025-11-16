package model;

public class CategoriaProducto {
    private int idCategoria;
    private String nombre;  

    public CategoriaProducto() {
    }

    public CategoriaProducto(int idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    //Getters y Setters
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
