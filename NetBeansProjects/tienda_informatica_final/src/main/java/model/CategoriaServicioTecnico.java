package model;

public class CategoriaServicioTecnico {

    private int categoriaProductoIdcategoria; // PK/FK

    public CategoriaServicioTecnico() {
    }

    public CategoriaServicioTecnico(int categoriaProductoIdcategoria) {
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