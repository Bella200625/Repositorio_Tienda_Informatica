import adapters.MySQLProveedorRepositoryAdapter; 
import Services.ProveedorService;     
import Services.ProveedorServicePort; 
import model.Proveedor; 
import model.Direccion; 

import java.util.List;
import java.util.Scanner;

public class testCrudProveedor {

    public static void main(String[] args) {
        
        MySQLProveedorRepositoryAdapter adaptadorBD = new MySQLProveedorRepositoryAdapter(); 
        ProveedorServicePort servicioProveedor = new ProveedorService(adaptadorBD); 
        
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = solicitarOpcion(scanner);

            try {
                switch (opcion) {
                    case 1:
                        agregarProveedor(scanner, servicioProveedor);
                        break;
                    case 2:
                        actualizarProveedor(scanner, servicioProveedor);
                        break;
                    case 3:
                        consultarProveedorPorId(scanner, servicioProveedor);
                        break;
                    case 4:
                        listarTodosLosProveedores(servicioProveedor);
                        break;
                    case 5:
                        eliminarProveedor(scanner, servicioProveedor);
                        break;
                    case 6:
                        System.out.println("Saliendo del Módulo de Proveedores...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("Ocurrió un error en la operación: " + e.getMessage());
                if (scanner.hasNextLine()) {
                    scanner.nextLine(); 
                }
            }
        } while (opcion != 6);
        
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=============================================");
        System.out.println("   MÓDULO DE GESTIÓN DE PROVEEDORES (HU2)   ");
        System.out.println("=============================================");
        System.out.println("1. Agregar Nuevo Proveedor");
        System.out.println("2. Actualizar Proveedor por ID");
        System.out.println("3. Consultar Proveedor por ID");
        System.out.println("4. Listar Todos los Proveedores");
        System.out.println("5. Eliminar Proveedor por ID");
        System.out.println("6. Salir del Módulo");
        System.out.println("=============================================");
        System.out.print("Seleccione una opción: ");
    }

    private static int solicitarOpcion(Scanner scanner) {
        if (scanner.hasNextInt()) {
            int op = scanner.nextInt();
            scanner.nextLine();
            return op;
        } else {
            scanner.next(); 
            return 0;
        }
    }
    

    //AGREGAR PROVEEDOR
    private static void agregarProveedor(Scanner scanner, ProveedorServicePort servicio) {
        System.out.println("\n--- REGISTRO DE NUEVO PROVEEDOR ---");
        
        System.out.print("Ingrese NIF/Identificación: ");
        String nif = scanner.nextLine();
        
        System.out.print("Ingrese Nombre de la Empresa: ");
        String nombre = scanner.nextLine();
        
        System.out.println("\n--- DATOS DE DIRECCIÓN ---");
        System.out.print("País: ");
        String pais = scanner.nextLine();
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();
        System.out.print("Calle: ");
        String calle = scanner.nextLine();
        System.out.print("Barrio: ");
        String barrio = scanner.nextLine();

        Direccion dir = new Direccion(pais, ciudad, calle, barrio);
        
        Proveedor nuevoProveedor = new Proveedor(0, nif, nombre, dir);
        
        try {
            int id = servicio.registrarNuevoProveedor(nuevoProveedor);

            if (id > 0) {
                System.out.println("Proveedor registrado exitosamente con ID: " + id);
                System.out.println("   Dirección asociada con ID: " + dir.getIdDireccion());
            } else {
                System.err.println("Error al registrar el proveedor.");
            }
        } catch (IllegalArgumentException e) {
             System.err.println("Error de Validación: " + e.getMessage());
        }
    }


    // 2. ACTUALIZAR PROVEEDOR (U)

    private static void actualizarProveedor(Scanner scanner, ProveedorServicePort servicio) {
        System.out.print("Ingrese el ID del proveedor a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Proveedor p = servicio.consultarProveedorPorId(id);
        
        if (p == null) {
            System.out.println("Proveedor no encontrado.");
            return;
        }
        
        System.out.println("Proveedor actual: " + p.getNombre() + " (" + p.getNif() + ")");
        System.out.print("Ingrese nuevo nombre (o Enter para mantener '" + p.getNombre() + "'): ");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.trim().isEmpty()) {
            p.setNombre(nuevoNombre);
        }
        
        // Actualizar Dirección
        Direccion dir = p.getDireccion();
        System.out.println("\n--- Actualizando Dirección (ID: " + dir.getIdDireccion() + ") ---");
        System.out.print("Nuevo País (actual: " + dir.getPais() + "): ");
        String nuevoPais = scanner.nextLine();
        if (!nuevoPais.trim().isEmpty()) { dir.setPais(nuevoPais); }
        
        System.out.print("Nueva Ciudad (actual: " + dir.getCiudad() + "): ");
        String nuevaCiudad = scanner.nextLine();
        if (!nuevaCiudad.trim().isEmpty()) { dir.setCiudad(nuevaCiudad); }
        
        if (servicio.actualizarProveedor(p)) {
            System.out.println("✅ Proveedor y Dirección actualizados exitosamente.");
        } else {
             System.err.println("❌ Error al actualizar el proveedor.");
        }
    }


    //CONSULTAR POR ID
    private static void consultarProveedorPorId(Scanner scanner, ProveedorServicePort servicio) {
        System.out.print("Ingrese el ID del proveedor a consultar: ");
        int id = scanner.nextInt(); 
        scanner.nextLine(); 
        
        Proveedor p = servicio.consultarProveedorPorId(id);
        
        if (p != null) {
            System.out.println("\n--- DETALLES DEL PROVEEDOR ID: " + p.getIdProveedor() + " ---");
            System.out.println("Nombre Empresa: " + p.getNombre());
            System.out.println("NIF: " + p.getNif());
            
            Direccion dir = p.getDireccion();
            if (dir != null) {
                System.out.println("--- Dirección (ID: " + dir.getIdDireccion() + ") ---");
                System.out.println("Ubicación: " + dir.getCalle() + ", " + dir.getBarrio());
                System.out.println("Ciudad/País: " + dir.getCiudad() + ", " + dir.getPais());
            } 
        } else {
            System.out.println("❌ Proveedor no encontrado.");
        }
    }
    

    //LISTAR TODOS
    private static void listarTodosLosProveedores(ProveedorServicePort servicio) {
        System.out.println("\n--- LISTADO COMPLETO DE PROVEEDORES ---");
        List<Proveedor> proveedores = servicio.obtenerTodosLosProveedores();

        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }

        for (Proveedor p : proveedores) {
            Direccion dir = p.getDireccion();
            String ubicacion = (dir != null) ? dir.getCiudad() + ", " + dir.getPais() : "Dirección Desconocida";
            
            System.out.printf("ID: %-4d | Nombre: %-30s | NIF: %-15s | Ubicación: %s%n",
                               p.getIdProveedor(), p.getNombre(), p.getNif(), ubicacion);
        }
    }
    

    //ELIMINAR PROVEEDOR
    private static void eliminarProveedor(Scanner scanner, ProveedorServicePort servicio) {
        System.out.print("Ingrese el ID del proveedor a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        if (servicio.eliminarProveedor(id)) {
            System.out.println("Proveedor con ID " + id + " eliminado exitosamente (incluyendo su dirección).");
        } else {
            System.err.println("Error al eliminar el proveedor. Podría estar referenciado en la tabla de Adquisición o el ID no existe.");
        }
    }
}