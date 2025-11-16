package model;

public class CategoriaAltaTecnologia {

    private int categoriaProductoIdcategoria; // PK/FK

    public CategoriaAltaTecnologia() {
    }

    public CategoriaAltaTecnologia(int categoriaProductoIdcategoria) {
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