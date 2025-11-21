import adapters.MySQLProveedorRepositoryAdapter;
import adapters.MySQLProductoProveedorRepositoryAdapter;
import Services.ProveedorService;
import Services.ProveedorServicePort;
import Services.ProductoProveedorService;
import Services.ProductoProveedorServicePort;

import model.Proveedor; 
import model.Direccion; 
import model.ProductoProveedor; 

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class testFlujo2 {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        
        MySQLProveedorRepositoryAdapter adaptadorProveedor = new MySQLProveedorRepositoryAdapter(); 
        ProveedorServicePort servicioProveedor = new ProveedorService(adaptadorProveedor); 

        // Servicio de Adquisiciones
        MySQLProductoProveedorRepositoryAdapter adaptadorAdquisicion = new MySQLProductoProveedorRepositoryAdapter();
        ProductoProveedorServicePort servicioAdquisicion = new ProductoProveedorService(adaptadorAdquisicion);
        
        Scanner scanner = new Scanner(System.in);
        int opcionPrincipal;

        do {
            mostrarMenuPrincipal();
            opcionPrincipal = solicitarOpcion(scanner);

            try {
                switch (opcionPrincipal) {
                    case 1:
                        gestionarProveedores(scanner, servicioProveedor);
                        break;
                    case 2:
                        gestionarAdquisiciones(scanner, servicioAdquisicion);
                        break;
                    case 3:
                        System.out.println("Saliendo de la prueba del Flujo 2...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("ERROR FATAL en el proceso: " + e.getMessage());
                if (scanner.hasNextLine()) {
                    scanner.nextLine(); 
                }
            }
        } while (opcionPrincipal != 3);
        
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=============================================");
        System.out.println("      MENÚ DE PRUEBA FLUJO 2 (HU2)      ");
        System.out.println("=============================================");
        System.out.println("1. Gestión CRUD de Proveedores");
        System.out.println("2. Gestión de Adquisiciones (M:M)");
        System.out.println("3. Salir");
        System.out.println("=============================================");
        System.out.print("Seleccione una opción: ");
    }
    
    
    //GESTIÓN DE PROVEEDORES
    private static void gestionarProveedores(Scanner scanner, ProveedorServicePort servicioProveedor) {
        int opcion;
        do {
            System.out.println("\n--- CRUD DE PROVEEDORES ---");
            System.out.println("1. Agregar Nuevo Proveedor");
            System.out.println("2. Actualizar Proveedor por ID");
            System.out.println("3. Consultar Proveedor por ID");
            System.out.println("4. Listar Todos los Proveedores");
            System.out.println("5. Eliminar Proveedor por ID");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = solicitarOpcion(scanner);

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
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }
    
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
            } else {
                System.err.println("Error al registrar el proveedor.");
            }
        } catch (IllegalArgumentException e) {
             System.err.println("Error de Validación: " + e.getMessage());
        }
    }
    
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
    
    private static void consultarProveedorPorId(Scanner scanner, ProveedorServicePort servicio) {
        System.out.print("Ingrese el ID del proveedor a consultar: ");
        int id = solicitarOpcion(scanner); 
        
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
            System.out.println("Proveedor no encontrado.");
        }
    }
    
    private static void actualizarProveedor(Scanner scanner, ProveedorServicePort servicio) {
        System.out.print("Ingrese el ID del proveedor a actualizar: ");
        int id = solicitarOpcion(scanner);
        
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
        
        if (servicio.actualizarProveedor(p)) {
            System.out.println("Proveedor y Dirección actualizados exitosamente.");
        } else {
             System.err.println("Error al actualizar el proveedor.");
        }
    }
    
    private static void eliminarProveedor(Scanner scanner, ProveedorServicePort servicio) {
        System.out.print("Ingrese el ID del proveedor a eliminar: ");
        int id = solicitarOpcion(scanner);
        
        if (servicio.eliminarProveedor(id)) {
            System.out.println("Proveedor con ID " + id + " eliminado exitosamente.");
        } else {
            System.err.println("Error al eliminar el proveedor. Podría tener adquisiciones asociadas.");
        }
    }
    

    // GESTIÓN DE ADQUISICIONES
    private static void gestionarAdquisiciones(Scanner scanner, ProductoProveedorServicePort servicioAdquisicion) {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE ADQUISICIONES (M:M) ---");
            System.out.println("1. Registrar Nueva Adquisición");
            System.out.println("2. Consultar Historial por Proveedor");
            System.out.println("3. Consultar Conteo de Productos por Proveedor");
            System.out.println("4. Consultar Adquisiciones por Fecha");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = solicitarOpcion(scanner);

            switch (opcion) {
                case 1:
                    registrarAdquisicion(scanner, servicioAdquisicion);
                    break;
                case 2:
                    consultarHistorialPorProveedor(scanner, servicioAdquisicion);
                    break;
                case 3:
                    contarProductosPorProveedor(scanner, servicioAdquisicion);
                    break;
                case 4:
                    consultarAdquisicionesPorFecha(scanner, servicioAdquisicion);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }
    

    // REGISTRAR ADQUISICIÓN
    private static void registrarAdquisicion(Scanner scanner, ProductoProveedorServicePort servicio) {
        System.out.print("Ingrese ID del Producto (Existente): ");
        int idProducto = solicitarOpcion(scanner);
        
        System.out.print("Ingrese ID del Proveedor (Existente): ");
        int idProveedor = solicitarOpcion(scanner);
        
        System.out.print("Ingrese Fecha de Adquisición (DD/MM/YYYY o ENTER para hoy): ");
        String fechaStr = scanner.nextLine();
        
        LocalDate fecha = null;
        if (!fechaStr.trim().isEmpty()) {
            try {
                fecha = LocalDate.parse(fechaStr, DATE_FORMATTER);
            } catch (Exception e) {
                System.err.println("Formato de fecha incorrecto. Usando fecha actual.");
                fecha = LocalDate.now();
            }
        } else {
            fecha = LocalDate.now();
        }

        try {
            int id = servicio.registrarNuevaAdquisicion(idProducto, idProveedor, fecha);
            if (id > 0) {
                System.out.println("Adquisición registrada exitosamente con ID: " + id);
            } else {
                System.err.println("Error al registrar adquisición. Verifique que los IDs existan.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de Validación: " + e.getMessage());
        }
    }
    
    // CONSULTAR HISTORIAL POR PROVEEDOR
    private static void consultarHistorialPorProveedor(Scanner scanner, ProductoProveedorServicePort servicio) {
        System.out.print("Ingrese ID del Proveedor para ver su historial: ");
        int idProveedor = solicitarOpcion(scanner);
        
        try {
            List<ProductoProveedor> historial = servicio.obtenerHistorialPorProveedor(idProveedor);
            
            System.out.println("\n--- HISTORIAL DE ADQUISICIONES DEL PROVEEDOR ID " + idProveedor + " ---");
            if (historial.isEmpty()) {
                System.out.println("No se encontraron adquisiciones para este proveedor.");
                return;
            }
            
            for (ProductoProveedor pp : historial) {
                System.out.printf("ID Adquisición: %-4d | Producto ID: %-4d | Fecha: %s%n",
                                  pp.getIdProductoProveedor(), 
                                  pp.getProductoIdProducto(), 
                                  pp.getFechaAdquisicion().format(DATE_FORMATTER));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    // CONTAR PRODUCTOS POR PROVEEDOR

    private static void contarProductosPorProveedor(Scanner scanner, ProductoProveedorServicePort servicio) {
        System.out.print("Ingrese ID del Proveedor para contar productos suministrados: ");
        int idProveedor = solicitarOpcion(scanner);
        
        try {
            int conteo = servicio.contarProductosSuministrados(idProveedor);
            System.out.println("El Proveedor ID " + idProveedor + " ha suministrado un total de " + conteo + " productos.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // 4. CONSULTAR ADQUISICIONES POR FECHA

    private static void consultarAdquisicionesPorFecha(Scanner scanner, ProductoProveedorServicePort servicio) {
        System.out.print("Ingrese Fecha a consultar (DD/MM/YYYY): ");
        String fechaStr = scanner.nextLine();
        
        try {
            LocalDate fecha = LocalDate.parse(fechaStr, DATE_FORMATTER);
            List<ProductoProveedor> adquisiciones = servicio.obtenerAdquisicionesPorFecha(fecha);
            
            System.out.println("\n--- ADQUISICIONES REGISTRADAS EL " + fecha.format(DATE_FORMATTER) + " ---");
            if (adquisiciones.isEmpty()) {
                System.out.println("No se encontraron adquisiciones en esa fecha.");
                return;
            }
            
            for (ProductoProveedor pp : adquisiciones) {
                System.out.printf("ID Adquisición: %-4d | Producto ID: %-4d | Proveedor ID: %d%n",
                                  pp.getIdProductoProveedor(), 
                                  pp.getProductoIdProducto(),
                                  pp.getProveedorIdProveedor());
            }
        } catch (Exception e) {
            System.err.println("ERROR: El formato de fecha debe ser DD/MM/YYYY.");
        }
    }

    // UTILIDADES
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
}