package ports;

import java.util.List;

import model.Proveedor;

public interface ProveedorRepositoryPort {

    int guardarProveedor(Proveedor proveedor);
    Proveedor buscarProveedorPorId(int id);
    List<Proveedor> obtenerTodosLosProveedores();
    boolean actualizarProveedor(Proveedor proveedor);
    boolean eliminarProveedor(int id);

}  