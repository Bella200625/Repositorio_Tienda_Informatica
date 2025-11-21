package model;

import java.time.LocalDate;

public class ProductoProveedor {

    private int idProductoProveedor;

    private int productoIdProducto;

    private int proveedorIdProveedor;
    
    private LocalDate fechaAdquisicion; 

    public ProductoProveedor() {
        this.fechaAdquisicion = LocalDate.now(); 
    }

    public ProductoProveedor(int idProductoProveedor, int productoIdProducto, int proveedorIdProveedor, LocalDate fechaAdquisicion) {
        this.idProductoProveedor = idProductoProveedor;
        this.productoIdProducto = productoIdProducto;
        this.proveedorIdProveedor = proveedorIdProveedor;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    //Getters y Setters

    public int getIdProductoProveedor() {
        return idProductoProveedor;
    }

    public void setIdProductoProveedor(int idProductoProveedor) {
        this.idProductoProveedor = idProductoProveedor;
    }

    public int getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(int productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    public int getProveedorIdProveedor() {
        return proveedorIdProveedor;
    }

    public void setProveedorIdProveedor(int proveedorIdProveedor) {
        this.proveedorIdProveedor = proveedorIdProveedor;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }
}