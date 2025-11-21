package ports;

import model.ProductoProveedor;
import java.time.LocalDate;
import java.util.List;

public interface ProductoProveedorRepositoryPort {

    int registrarAdquisicion(ProductoProveedor adquisicion);
    
    List<ProductoProveedor> buscarAdquisicionesPorProveedor(int idProveedor);

    List<ProductoProveedor> buscarAdquisicionesPorFecha(LocalDate fecha);

    int contarProductosPorProveedor(int idProveedor);
}