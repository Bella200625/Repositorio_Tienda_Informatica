package ports;

import java.util.List;

import model.Producto;

public interface ProductoRepositoryPort {

    int guardarProducto(Producto producto); // Método para guardar un producto.

    Producto buscarProductoPorId(int id); // Método para buscar un producto por su ID.

    List<Producto> obtenerTodosLosProductos(); // Método para obtener todos los productos.

    boolean actualizarProducto(Producto producto); // Método para actualizar un producto.

boolean eliminarProducto(int id); // Método para eliminar un producto por su ID.



}
        