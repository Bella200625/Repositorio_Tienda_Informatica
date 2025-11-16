import adapters.MySQLProductoRepositoryAdapter;
import model.DetalleAltaTecnologia;
import model.EmpresaFabricante;
import model.Producto;
import model.*;
import ports.ProductoRepositoryPort;


public class testConsultarProducto {

    public static void main(String[] args) {
        
        // El ID del producto que se buscará.
        // ⚠️ IMPORTANTE: Reemplaza 1 con un ID de producto que sepas que existe en tu BD.
        final int ID_PRODUCTO_A_BUSCAR = 3; 
        
        // Inicializar el adaptador
        ProductoRepositoryPort repository = new MySQLProductoRepositoryAdapter();

        System.out.println("--- INICIANDO PRUEBA DE LECTURA (R) ---");
        System.out.println("Buscando producto con ID: " + ID_PRODUCTO_A_BUSCAR);
        
        // Llamada al método de consulta
        Producto productoEncontrado = repository.buscarProductoPorId(ID_PRODUCTO_A_BUSCAR);

        if (productoEncontrado != null) {
            System.out.println("\n✅ LECTURA EXITOSA. Datos completos recuperados:");
            System.out.println("----------------------------------------------");
            
            // 1. Datos Base del Producto
            System.out.println("ID: " + productoEncontrado.getIdProducto());
            System.out.println("Nombre: " + productoEncontrado.getNombreProducto());
            System.out.println("Modelo: " + productoEncontrado.getModelo());
            System.out.println("Descripción: " + productoEncontrado.getDescripcion());
            
            // 2. Datos de la Categoría (Relación JOIN simple)
            if (productoEncontrado.getCategoria() != null) {
                System.out.println("Categoría: " + productoEncontrado.getCategoria().getNombre() + 
                                   " (ID: " + productoEncontrado.getCategoriaProductoId() + ")");
            }

            // 3. Datos de Alta Tecnología (Relación LEFT JOIN)
            DetalleAltaTecnologia detalleRecuperado = productoEncontrado.getDetalleAltaTecnologia();
            if (detalleRecuperado != null) {
                System.out.println("\n--- Detalle Alta Tecnología ---");
                System.out.println("País de Origen: " + detalleRecuperado.getPaisOrigen());
                System.out.println("Fecha Fab.: " + detalleRecuperado.getFechaFabricacion());
                
                // 4. Datos del Fabricante (Relación anidada)
                EmpresaFabricante fabricanteRecuperado = detalleRecuperado.getFabricante();
                if (fabricanteRecuperado != null) {
                    System.out.println("Fabricante: " + fabricanteRecuperado.getNombre() + 
                                       " (Empleados: " + fabricanteRecuperado.getNumeroEmpleados() + ")");
                }
            } else {
                System.out.println("\n--- No es Producto de Alta Tecnología ---");
            }
            
        } else {
            System.out.println("\n❌ LECTURA FALLIDA. Producto con ID " + ID_PRODUCTO_A_BUSCAR + " no fue encontrado en la base de datos.");
            System.out.println("Asegúrate de que el ID exista y que las credenciales de conexión sean correctas.");
        }
    }
}