package Services;

import ports.ProductoProveedorRepositoryPort;
import model.ProductoProveedor;
import java.time.LocalDate;
import java.util.List;


public class ProductoProveedorService implements ProductoProveedorServicePort {

    private final ProductoProveedorRepositoryPort productoProveedorRepository;


    public ProductoProveedorService(ProductoProveedorRepositoryPort productoProveedorRepository) {
        this.productoProveedorRepository = productoProveedorRepository;
    }

    @Override
    public int registrarNuevaAdquisicion(int idProducto, int idProveedor, LocalDate fechaAdquisicion) {
        if (idProducto <= 0 || idProveedor <= 0) {
            throw new IllegalArgumentException("Los IDs de Producto y Proveedor deben ser válidos.");
        }
        
        LocalDate fecha = (fechaAdquisicion != null) ? fechaAdquisicion : LocalDate.now();

        ProductoProveedor adquisicion = new ProductoProveedor();
        adquisicion.setProductoIdProducto(idProducto);
        adquisicion.setProveedorIdProveedor(idProveedor);
        adquisicion.setFechaAdquisicion(fecha);
        
        return productoProveedorRepository.registrarAdquisicion(adquisicion);
    }

    @Override
    public List<ProductoProveedor> obtenerHistorialPorProveedor(int idProveedor) {
        if (idProveedor <= 0) {
            throw new IllegalArgumentException("El ID del Proveedor debe ser válido.");
        }
        return productoProveedorRepository.buscarAdquisicionesPorProveedor(idProveedor);
    }

    @Override
    public int contarProductosSuministrados(int idProveedor) {
        if (idProveedor <= 0) {
            throw new IllegalArgumentException("El ID del Proveedor debe ser válido.");
        }
        return productoProveedorRepository.contarProductosPorProveedor(idProveedor);
    }

    @Override
    public List<ProductoProveedor> obtenerAdquisicionesPorFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        return productoProveedorRepository.buscarAdquisicionesPorFecha(fecha);
    }
}