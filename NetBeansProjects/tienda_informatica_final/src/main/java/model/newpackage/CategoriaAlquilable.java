package model.newpackage;

public class CategoriaAlquilable {

    private int categoriaProductoIdcategoria; // PK/FK

    public CategoriaAlquilable() {
    }

    public CategoriaAlquilable(int categoriaProductoIdcategoria) {
        this.categoriaProductoIdcategoria = categoriaProductoIdcategoria;
    }

    // Getters y Setters...
    public int getCategoriaProductoIdcategoria() {
        return categoriaProductoIdcategoria;
    }

    public void setCategoriaProductoIdcategoria(int categoriaProductoIdcategoria) {
        this.categoriaProductoIdcategoria = categoriaProductoIdcategoria;
    }
}