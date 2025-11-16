package main.java.com.tienda.informatica.dominio;

public class Producto {
    private int idProducto; //PK
    private String hombreProducto;
    private String modelo;
    private String descripcion;

    //Claves foraneas
    private Integer detalleAltaTecnologiaId;
    private int categoriaProductoId;

    //Relaciones
    private CategoriaProducto categoria;
    private DetalleAltaTecnologia detalleAltaTecnologia;

    public Producto() {
    }

    public Producto(int idProducto, String hombreProducto, String modelo, String descripcion, Integer detalleAltaTecnologiaId, int categoriaProductoId) {
        this.idProducto = idProducto;
        this.hombreProducto = hombreProducto;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.categoriaProductoId = categoriaProductoId;
        this.detalleAltaTecnologiaId = detalleAltaTecnologiaId;
    }

    //Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public String getHombreProducto() {
        return hombreProducto;
    }

    public void setHombreProducto(String hombreProducto) {
        this.hombreProducto = hombreProducto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDetalleAltaTecnologiaId() {
        return detalleAltaTecnologiaId;
    }

    public void setDetalleAltaTecnologiaId(Integer detalleAltaTecnologiaId) {
        this.detalleAltaTecnologiaId = detalleAltaTecnologiaId;
    }

    public int getCategoriaProductoId() {
        return categoriaProductoId;
    }

    public void setCategoriaProductoId(int categoriaProductoId) {
        this.categoriaProductoId = categoriaProductoId;
    }
    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    } 
    public DetalleAltaTecnologia getDetalleAltaTecnologia() {
        return detalleAltaTecnologia;
    }

    public void setDetalleAltaTecnologia(DetalleAltaTecnologia detalleAltaTecnologia) {
        this.detalleAltaTecnologia = detalleAltaTecnologia;
        this.detalleAltaTecnologiaId = (detalleAltaTecnologia != null) ? detalleAltaTecnologia.getId() : null;
    }

}
