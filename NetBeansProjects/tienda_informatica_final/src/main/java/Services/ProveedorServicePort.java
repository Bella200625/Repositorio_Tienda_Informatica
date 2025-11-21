package Services;

import java.util.List;
import model.Proveedor;


public interface ProveedorServicePort {

    int registrarNuevoProveedor(Proveedor proveedor);
    
    Proveedor consultarProveedorPorId(int id);
    
    List<Proveedor> obtenerTodosLosProveedores();
    
    boolean actualizarProveedor(Proveedor proveedor);
    boolean eliminarProveedor(int id);
}