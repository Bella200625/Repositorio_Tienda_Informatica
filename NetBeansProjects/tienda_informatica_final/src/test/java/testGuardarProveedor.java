import adapters.MySQLProveedorRepositoryAdapter;
import Services.ProveedorService;
import Services.ProveedorServicePort;
import model.Proveedor;
import model.Direccion;

public class testGuardarProveedor {

    public static void main(String[] args) {

        MySQLProveedorRepositoryAdapter adaptador = new MySQLProveedorRepositoryAdapter();
        ProveedorServicePort servicio = new ProveedorService(adaptador);

        Direccion dir = new Direccion("Colombia", "Medellín", "Calle 48", "Boston");

        Proveedor nuevoProveedor = new Proveedor(
            0,
            "0000011",
            "Empresa ASUS",
            dir
        );

        
        try {
            int id = servicio.registrarNuevoProveedor(nuevoProveedor);

            if (id > 0) {
                System.out.println("✅ Proveedor registrado correctamente con ID: " + id);
                System.out.println("   Dirección registrada con ID: " + dir.getIdDireccion());
            } else {
                System.out.println("❌ Fallo al registrar proveedor.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de Negocio: " + e.getMessage());
        }
    }
}