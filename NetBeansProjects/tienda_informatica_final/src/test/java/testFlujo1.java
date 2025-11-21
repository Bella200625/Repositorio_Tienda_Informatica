

import adapters.MySQLProductoRepositoryAdapter; 
import Services.ProductoService;              
import ports.ProductoRepositoryPort;         
import Services.ProductoServicePort;           


import model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class testFlujo1 {

    public static void main(String[] args) {

        ProductoRepositoryPort adaptadorBD = new MySQLProductoRepositoryAdapter();

        ProductoServicePort servicioProducto = new ProductoService(adaptadorBD);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = solicitarOpcion(scanner); 

            try {
                switch (opcion) {
                    case 1:
                        agregarProducto(scanner, servicioProducto);
                        break;
                    case 2:
                        actualizarProducto(scanner, servicioProducto);
                        break;
                    case 3:
                        consultarProductoPorId(scanner, servicioProducto);
                        break;
                    case 4:
                        listarTodosLosProductos(servicioProducto);
                        break;
                    case 5:
                        eliminarProducto(scanner, servicioProducto);
                        break;
                    case 6:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("Ocurrió un error en la operación. Revise la entrada: " + e.getMessage());
            }
        } while (opcion != 6);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=============================================");
        System.out.println("  SISTEMA DE GESTIÓN DE PRODUCTOS (CRUD)  ");
        System.out.println("=============================================");
        System.out.println("1. Agregar Nuevo Producto");
        System.out.println("2. Actualizar Producto por ID");
        System.out.println("3. Consultar Producto por ID");
        System.out.println("4. Listar Todos los Productos");
        System.out.println("5. Eliminar Producto por ID");
        System.out.println("6. Salir");
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

    // AGREGAR PRODUCTO

    private static void agregarProducto(Scanner scanner, ProductoServicePort servicio) {
        System.out.println("\n--- REGISTRO DE NUEVO PRODUCTO ---");
        
        System.out.print("Ingrese nombre del producto: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese modelo del producto: ");
        String modelo = scanner.nextLine();
        
        System.out.print("Ingrese descripción del producto: ");
        String descripcion = scanner.nextLine();
        
        // Selección de Categoría
        System.out.println("\n--- SELECCIÓN DE CATEGORÍA ---");
        System.out.println("1. CPU | 2. Impresora | 3. Monitor | 4. Disco Duro | 5. Otros");
        System.out.print("Ingrese el ID de la categoría (1-5): ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine(); 

        if (idCategoria < 1 || idCategoria > 5) {
            System.err.println("ID de categoría no válido. Operación cancelada.");
            return;
        }

        Producto nuevoProducto = new Producto(0, nombre, modelo, descripcion, null, idCategoria);

        // Lógica de Alta Tecnología Para CPU y Discos Duros
        if (idCategoria == 1 || idCategoria == 4) {
            System.out.println("\n--- DETALLES DE ALTA TECNOLOGÍA ---");
            
            // Datos del Fabricante
            System.out.print("Nombre del Fabricante: ");
            String nombreFabricante = scanner.nextLine();
            System.out.print("Número de empleados del Fabricante: ");
            int numEmpleados = scanner.nextInt();
            scanner.nextLine();
            
            EmpresaFabricante fabricante = new EmpresaFabricante(0, nombreFabricante, numEmpleados);
            
            // Datos del Detalle
            System.out.print("País de Origen: ");
            String paisOrigen = scanner.nextLine();
            
            LocalDate fechaFabricacion = LocalDate.now();
            
            DetalleAltaTecnologia detalle = new DetalleAltaTecnologia(0, paisOrigen, fechaFabricacion, 0);
            detalle.setFabricante(fabricante);
            
            nuevoProducto.setDetalleAltaTecnologia(detalle);
            
            if (idCategoria == 1) {
                System.out.println("CPU's: Memoria principal no guardada, requiere extensión de la BD.");
            }
            if (idCategoria == 4) {
                System.out.println("Discos Duros: Capacidad de almacenamiento no guardada, requiere extensión de la BD.");
            }

        } else if (idCategoria == 2) { // Impresora
            System.out.print("Velocidad de impresión (ppm): ");
            String velocidad = scanner.nextLine();
            System.out.println("NOTA: Velocidad de impresión no guardada, requiere extensión de la BD.");
        } else if (idCategoria == 3) { // Monitor
            System.out.print("Resolución máxima (ej. 4K): ");
            String resolucion = scanner.nextLine();
            System.out.println("NOTA: Resolución máxima no guardada, requiere extensión de la BD.");
        }

        // Guardar el Producto a través del Servicio (Capa de Aplicación)
        System.out.println("\nGuardando producto en la base de datos...");
        int id = servicio.registrarNuevoProducto(nuevoProducto);

        if (id > 0) {
            System.out.println("Producto registrado exitosamente con ID: " + id);
        } else {
            System.err.println("Error al registrar el producto. Revise la consola para detalles SQL.");
        }
    }
    

    // 2. CONSULTAR PRODUCTO POR ID

    private static void consultarProductoPorId(Scanner scanner, ProductoServicePort servicio) {
        System.out.print("Ingrese el ID del producto a consultar: ");
        int id = scanner.nextInt(); 
        scanner.nextLine(); 
        
        Producto p = servicio.consultarProductoPorId(id);
        
        if (p != null) {
            System.out.println("--- DETALLES DEL PRODUCTO ID: " + p.getIdProducto() + " ---");
            System.out.println("Nombre: " + p.getNombreProducto() + " (" + p.getModelo() + ")");
            //lógica de búsqueda que mapea la categoría
            System.out.println("Categoría: " + (p.getCategoria() != null ? p.getCategoria().getNombre() : "N/A"));
            
            if (p.getDetalleAltaTecnologia() != null) {
                System.out.println("País Origen: " + p.getDetalleAltaTecnologia().getPaisOrigen());
                System.out.println("Fecha Fabricación: " + p.getDetalleAltaTecnologia().getFechaFabricacion());
                System.out.println("Fabricante: " + p.getDetalleAltaTecnologia().getFabricante().getNombre());
            } else {
                 System.out.println("Tipo: Producto Estándar");
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    //ACTUALIZAR PRODUCTO

    private static void actualizarProducto(Scanner scanner, ProductoServicePort servicio) {
        System.out.print("Ingrese el ID del producto a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Producto p = servicio.consultarProductoPorId(id);
        
        if (p == null) {
            System.out.println("Producto no encontrado. No se puede actualizar.");
            return;
        }
        
        System.out.println("Producto actual: " + p.getNombreProducto() + ". Ingrese nuevo nombre (o Enter para mantener):");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.trim().isEmpty()) {
            p.setNombreProducto(nuevoNombre);
        }
        
        // Solo actualizamos el fabricante si es Alta Tecnología
        if (p.getDetalleAltaTecnologia() != null) {
             System.out.println("Fabricante actual: " + p.getDetalleAltaTecnologia().getFabricante().getNombre() + ". Ingrese nuevo nombre de fabricante (o Enter para mantener):");
             String nuevoFabricante = scanner.nextLine();
             if (!nuevoFabricante.trim().isEmpty()) {
                 p.getDetalleAltaTecnologia().getFabricante().setNombre(nuevoFabricante);
             }
        }
        

        boolean actualizado = servicio.actualizarProducto(p); 

        if (actualizado) {
            System.out.println("Producto actualizado exitosamente.");
        } else {
             System.err.println("Error al actualizar el producto.");
        }
    }


    //LISTAR TODOS LOS PRODUCTOS
    private static void listarTodosLosProductos(ProductoServicePort servicio) {
        System.out.println("\n--- LISTADO COMPLETO DE PRODUCTOS ---");


        List<Producto> productos = servicio.obtenerTodosLosProductos();

        if (productos.isEmpty()) {
            System.out.println("Inventario vacío.");
            return;
        }

        for (Producto p : productos) {
            String altaTec = (p.getDetalleAltaTecnologia() != null) ? 
                             " [Alta Tec: " + p.getDetalleAltaTecnologia().getFabricante().getNombre() + "]" : "";
            String categoria = (p.getCategoria() != null) ? p.getCategoria().getNombre() : "Sin Categoría";
            
            System.out.printf("ID: %-4d | Nombre: %-30s | Modelo: %-15s | Categoría: %-15s %s%n",
                               p.getIdProducto(), p.getNombreProducto(), p.getModelo(), categoria, altaTec);
        }
    }

    //ELIMINAR PRODUCTO
    private static void eliminarProducto(Scanner scanner, ProductoServicePort servicio) {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        

        boolean eliminado = servicio.eliminarProducto(id);

        if (eliminado) {
            System.out.println("Producto con ID " + id + " eliminado exitosamente.");
        } else {
            System.err.println("Error al eliminar el producto. Podría estar referenciado en otra tabla o el ID no existe.");
        }
    }
}