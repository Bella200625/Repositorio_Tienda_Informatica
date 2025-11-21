package Services;

import ports.ProveedorRepositoryPort;
import model.Proveedor;
import java.util.List;


public class ProveedorService implements ProveedorServicePort {

    private final ProveedorRepositoryPort proveedorRepository;

    public ProveedorService(ProveedorRepositoryPort proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public int registrarNuevoProveedor(Proveedor proveedor) {
        if (proveedor.getNif() == null || proveedor.getNif().length() < 5) {
            throw new IllegalArgumentException("NIF inválido para el proveedor.");
        }
        return proveedorRepository.guardarProveedor(proveedor);
    }

    @Override
    public Proveedor consultarProveedorPorId(int id) {
        return proveedorRepository.buscarProveedorPorId(id);
    }
    
    @Override
    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorRepository.obtenerTodosLosProveedores();
    }
    
    @Override
    public boolean actualizarProveedor(Proveedor proveedor) {
        return proveedorRepository.actualizarProveedor(proveedor);
    }
    
    @Override
    public boolean eliminarProveedor(int id) {
        return proveedorRepository.eliminarProveedor(id);
    }
}