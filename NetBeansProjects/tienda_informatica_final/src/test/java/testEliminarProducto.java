

import adapters.*;
import model.Producto;
import ports.ProductoRepositoryPort;

public class testEliminarProducto {

    public static void main(String[] args) {
        
        ProductoRepositoryPort repository = new MySQLProductoRepositoryAdapter();

        //ID que vamos a eliminar
        final int ID_A_ELIMINAR = 2;
        
        System.out.println("--- 1. CONSULTANDO PRODUCTO ANTES DE ELIMINAR ---");
        Producto productoPrevio = repository.buscarProductoPorId(ID_A_ELIMINAR);
        
        if (productoPrevio == null) {
            System.err.println("El producto con ID " + ID_A_ELIMINAR + " no existe o ya fue eliminado. Insertar uno primero para probar.");
            return;
        }
        
        System.out.println("Producto encontrado antes de eliminar: " + productoPrevio.getNombreProducto());
        

        //EJECUTAR ELIMINACIÓN

        System.out.println("\n--- 2. EJECUTANDO ELIMINACIÓN ---");
        boolean eliminado = repository.eliminarProducto(ID_A_ELIMINAR);

        if (eliminado) {
            System.out.println("✅ ELIMINACIÓN EXITOSA. Verificando... ");


            //wwVERIFICACIÓN

            Producto productoPost = repository.buscarProductoPorId(ID_A_ELIMINAR);
            
            if (productoPost == null) {
                System.out.println("✅ VERIFICACIÓN EXITOSA: El producto con ID " + ID_A_ELIMINAR + " ya no existe en la base de datos.");
            } else {
                System.err.println("❌ VERIFICACIÓN FALLIDA: El producto todavía se puede recuperar.");
            }
            
        } else {
            System.err.println("❌ ELIMINACIÓN FALLIDA. Revise el error SQL detallado.");
        }
    }
}