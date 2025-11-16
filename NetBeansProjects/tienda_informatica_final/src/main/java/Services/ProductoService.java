package Services;

import java.util.List;

import model.Producto;
import ports.ProductoRepositoryPort;

/**
 * Implementación de la lógica de negocio para la gestión de Productos.
 * Esta clase usa el Puerto del Dominio (ProductoRepositoryPort) para la persistencia.
 */
public class ProductoService implements ProductoServicePort {

    // Dependencia del Puerto de Persistencia
    private final ProductoRepositoryPort productoRepository;

    // Constructor para inyectar la dependencia
    public ProductoService(ProductoRepositoryPort productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public int registrarNuevoProducto(Producto producto) {
        // Lógica de negocio antes de guardar:
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        int idGenerado = productoRepository.guardarProducto(producto);
        
        if (idGenerado > 0) {
            System.out.println("LOG: Producto registrado en inventario con ID: " + idGenerado);
        }
        return idGenerado;
    }

    @Override
    public Producto consultarProductoPorId(int id) {
        return productoRepository.buscarProductoPorId(id);
    }


    public Producto obtenerProductoPorId(int id) {
        return productoRepository.buscarProductoPorId(id);
    }

    // Archivo: ProductoService.java (Capa de Aplicación)

// ... (imports y constructor) ...

// Nuevo: Actualizar (U)

public boolean actualizarProducto(Producto producto) {
    // Aquí iría la lógica de negocio antes de actualizar (ej. logs, auditoría)
    return productoRepository.actualizarProducto(producto);
}

// Nuevo: Eliminar (D)

public boolean eliminarProducto(int id) {
    // Aquí iría la lógica de negocio antes de eliminar (ej. verificar inventario)
    return productoRepository.eliminarProducto(id);
}

// Nuevo: Listar Todos (R - Múltiple)

public List<Producto> obtenerTodosLosProductos() {
    return productoRepository.obtenerTodosLosProductos();
}

// ... (fin de la clase) ...
}