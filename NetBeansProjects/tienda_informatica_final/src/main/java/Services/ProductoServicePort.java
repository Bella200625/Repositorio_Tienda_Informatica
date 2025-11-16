package Services;

import java.util.List;

import model.Producto;
import ports.ProductoRepositoryPort;

public interface ProductoServicePort {

    int registrarNuevoProducto(Producto producto);

    Producto obtenerProductoPorId(int id);

    Producto consultarProductoPorId(int id);

}