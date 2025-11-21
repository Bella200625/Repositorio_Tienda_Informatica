package Services;

import model.ProductoProveedor;
import model.Producto;
import model.Proveedor;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface ProductoProveedorServicePort {

    int registrarNuevaAdquisicion(int idProducto, int idProveedor, LocalDate fechaAdquisicion);

    List<ProductoProveedor> obtenerHistorialPorProveedor(int idProveedor);

    int contarProductosSuministrados(int idProveedor);


    List<ProductoProveedor> obtenerAdquisicionesPorFecha(LocalDate fecha);

}