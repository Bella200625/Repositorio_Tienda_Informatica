import adapters.MySQLProveedorRepositoryAdapter;
import Services.ProveedorService;
import Services.ProveedorServicePort;
import model.Proveedor;
import model.Direccion;

public class testConsultarProveedor {

    public static void main(String[] args) {
        
        MySQLProveedorRepositoryAdapter adaptador = new MySQLProveedorRepositoryAdapter();
        ProveedorServicePort servicio = new ProveedorService(adaptador);

        final int ID_A_BUSCAR = 2;

        System.out.println("--- CONSULTANDO PROVEEDOR CON ID " + ID_A_BUSCAR + " ---");
        
        Proveedor proveedorRecuperado = servicio.consultarProveedorPorId(ID_A_BUSCAR);

        if (proveedorRecuperado != null) {
            System.out.println("Proveedor Encontrado:");
            System.out.println("ID Proveedor: " + proveedorRecuperado.getIdProveedor());
            System.out.println("Nombre: " + proveedorRecuperado.getNombre());
            System.out.println("NIF: " + proveedorRecuperado.getNif());
            
            Direccion dir = proveedorRecuperado.getDireccion();
            if (dir != null) {
                System.out.println("--- Datos de Dirección ---");
                System.out.println("ID Dirección: " + dir.getIdDireccion());
                System.out.println("País: " + dir.getPais());
                System.out.println("Ciudad: " + dir.getCiudad());
                System.out.println("Calle: " + dir.getCalle() + ", Barrio: " + dir.getBarrio());
            }
        } else {
            System.err.println("Error: No se encontró el proveedor con ID " + ID_A_BUSCAR);
        }
    }
}