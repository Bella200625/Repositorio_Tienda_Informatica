package ports;

import java.util.List;

import model.Producto;

public interface ProductoRepositoryPort {

    int guardarProducto(Producto producto); 

    Producto buscarProductoPorId(int id); 

    List<Producto> obtenerTodosLosProductos(); 

    boolean actualizarProducto(Producto producto); 

boolean eliminarProducto(int id); 



}
        