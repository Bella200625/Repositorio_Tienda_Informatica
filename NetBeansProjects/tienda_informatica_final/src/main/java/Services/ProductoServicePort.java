package Services;

import java.util.List;

// Importamos la entidad Producto de la capa de Dominio
// NOTA: Usamos 'model.newpackage.Producto' basado en tu estructura de carpetas
import model.Producto; 

// Nota: No se requiere importar ProductoRepositoryPort aquí, ya que este es el puerto de entrada 
// y no necesita conocer los detalles del puerto de salida.

public interface ProductoServicePort {

    /**
     * Registra un nuevo producto complejo (incluyendo Detalle y Fabricante si aplica).
     * @param producto El objeto Producto a guardar.
     * @return El ID autogenerado del producto guardado, o 0 si falla.
     */
    int registrarNuevoProducto(Producto producto);

    /**
     * Consulta los detalles de un producto por su ID, incluyendo sus relaciones.
     * @param id El ID del producto a buscar.
     * @return El objeto Producto completo o null si no se encuentra.
     */
    Producto consultarProductoPorId(int id);
    
    // --------------------------------------------------------------------
    // MÉTODOS AÑADIDOS PARA COMPLETAR EL CRUD Y EL MENÚ
    // --------------------------------------------------------------------

    /**
     * Obtiene una lista completa de todos los productos en el inventario.
     * @return Una lista de objetos Producto.
     */
    List<Producto> obtenerTodosLosProductos();

    /**
     * Actualiza los datos de un producto existente.
     * @param producto El objeto Producto con los datos modificados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean actualizarProducto(Producto producto);

    /**
     * Elimina un producto por su ID, manejando las relaciones de alta tecnología.
     * @param id El ID del producto a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    boolean eliminarProducto(int id);
    
    // Nota: El método 'Producto obtenerProductoPorId(int id);' que tenías fue eliminado 
    // por ser redundante con 'consultarProductoPorId(int id);'
}