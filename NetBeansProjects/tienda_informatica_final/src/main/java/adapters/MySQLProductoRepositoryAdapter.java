package adapters;

import model.CategoriaProducto;
import model.DetalleAltaTecnologia;
import model.EmpresaFabricante;
import model.Producto;
import ports.ProductoRepositoryPort;

import java.sql.*;
import java.util.List;

public class MySQLProductoRepositoryAdapter implements ProductoRepositoryPort {


    private static final String JDBC_URL = "jdbc:mysql://25.50.109.111:3306/tienda_informatica_final?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root2";
    private static final String PASSWORD = ""; 

    @Override
    public int guardarProducto(Producto producto) {
        int idDetalleAltaTecnologia = 0;
        int idProductoGenerado = 0;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            conn.setAutoCommit(false); 

            // PASO 1: INSERTAR FABRICANTE Y DETALLE (Si es Producto de Alta Tecnología)

            DetalleAltaTecnologia detalle = producto.getDetalleAltaTecnologia();
            
            if (detalle != null) {
                // Insertar Empresa Fabricante
                EmpresaFabricante fabricante = detalle.getFabricante();
                int idFabricanteGenerado = 0;
                
                String sqlFabricante = "INSERT INTO empresa_fabricante (nombre, numero_empleados) VALUES (?, ?)";
                try (PreparedStatement psFab = conn.prepareStatement(sqlFabricante, Statement.RETURN_GENERATED_KEYS)) {
                    psFab.setString(1, fabricante.getNombre());
                    psFab.setInt(2, fabricante.getNumeroEmpleados());
                    psFab.executeUpdate();
                    
                    try (ResultSet rs = psFab.getGeneratedKeys()) {
                        if (rs.next()) {
                            idFabricanteGenerado = rs.getInt(1);
                        }
                    }
                }
                
                // Insertar Detalle de Alta Tecnología, usando el ID del fabricante
                String sqlDetalle = "INSERT INTO detalle_alta_tecnologia (pais_origen, fecha_fabricacion, Empresa_fabricante_idEmpresa_fabricante) VALUES (?, ?, ?)";
                try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle, Statement.RETURN_GENERATED_KEYS)) {
                    psDetalle.setString(1, detalle.getPaisOrigen());
                    psDetalle.setDate(2, Date.valueOf(detalle.getFechaFabricacion())); 
                    psDetalle.setInt(3, idFabricanteGenerado); 
                    psDetalle.executeUpdate();
                    
                    try (ResultSet rs = psDetalle.getGeneratedKeys()) {
                        if (rs.next()) {
                            idDetalleAltaTecnologia = rs.getInt(1); // Guardamos el ID para el Producto
                        }
                    }
                }
            }


            // -------------------------------------------------------------------
            // PASO 2: INSERTAR el Producto
            // -------------------------------------------------------------------
            String sqlProducto = "INSERT INTO producto (nombre_producto, modelo, descripcion, Detalle_alta_tecnologia_idDetalle_alta_tecnologia, categoria_producto_idcategoria) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psProducto = conn.prepareStatement(sqlProducto, Statement.RETURN_GENERATED_KEYS)) {

                psProducto.setString(1, producto.getNombreProducto());
                psProducto.setString(2, producto.getModelo());
                psProducto.setString(3, producto.getDescripcion());
                
                // Si es Alta Tecnología (idDetalleAltaTecnologia > 0), se usa el ID generado.
                if (idDetalleAltaTecnologia > 0) {
                     psProducto.setInt(4, idDetalleAltaTecnologia); 
                } else {
                     psProducto.setNull(4, java.sql.Types.INTEGER);
                }
                
                psProducto.setInt(5, producto.getCategoriaProductoId()); 

                if (psProducto.executeUpdate() > 0) {
                    try (ResultSet rs = psProducto.getGeneratedKeys()) {
                        if (rs.next()) {
                            idProductoGenerado = rs.getInt(1);
                        }
                    }
                }
            }

            conn.commit();
            return idProductoGenerado;

        } catch (SQLException e) {
             try {

                Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {

            }
           
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public Producto buscarProductoPorId(int id) {
String sql = "SELECT " +
                 "p.idProducto, p.nombre_producto, p.modelo, p.descripcion, " +
                 "p.categoria_producto_idcategoria, p.Detalle_alta_tecnologia_idDetalle_alta_tecnologia, " +
                 "cp.nombre AS nombre_categoria, " +
                 "dat.idDetalle_alta_tecnologia, dat.pais_origen, dat.fecha_fabricacion, " +
                 "ef.idEmpresa_fabricante, ef.nombre AS nombre_fabricante, ef.numero_empleados " +
                 "FROM producto p " +
                 "JOIN categoria_producto cp ON p.categoria_producto_idcategoria = cp.idcategoria " +
                 "LEFT JOIN detalle_alta_tecnologia dat ON p.Detalle_alta_tecnologia_idDetalle_alta_tecnologia = dat.idDetalle_alta_tecnologia " +
                 "LEFT JOIN empresa_fabricante ef ON dat.Empresa_fabricante_idEmpresa_fabricante = ef.idEmpresa_fabricante " +
                 "WHERE p.idProducto = ?";

    Producto producto = null;
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            
            // 2. Procesar el resultado
            if (rs.next()) {
                
                //Mapear el Producto base (p)
                producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setModelo(rs.getString("modelo"));
                producto.setDescripcion(rs.getString("descripcion"));
                
                // Mapear FKs
                producto.setCategoriaProductoId(rs.getInt("categoria_producto_idcategoria"));
                
                // Usamos rs.getObject para manejar el valor NULL correctamente en la columna FK
                Integer detalleIdFK = (Integer) rs.getObject("Detalle_alta_tecnologia_idDetalle_alta_tecnologia");
                producto.setDetalleAltaTecnologiaId(detalleIdFK);
                
                // Mapear la Categoria
                CategoriaProducto categoria = new CategoriaProducto(
                    producto.getCategoriaProductoId(), 
                    rs.getString("nombre_categoria")
                );
                producto.setCategoria(categoria);

                
                //Mapear DetalleAltaTecnologia y Fabricante (Solo si existe el ID del Detalle)
                if (detalleIdFK != null) {
                    
                    // Mapear la Empresa Fabricante (ef)
                    EmpresaFabricante fabricante = new EmpresaFabricante(
                        rs.getInt("idEmpresa_fabricante"), 
                        rs.getString("nombre_fabricante"), 
                        rs.getInt("numero_empleados")
                    );
                    
                    // Mapear el Detalle de Alta Tecnología (dat)
                    DetalleAltaTecnologia detalle = new DetalleAltaTecnologia(
                        rs.getInt("idDetalle_alta_tecnologia"), 
                        rs.getString("pais_origen"), 
                        rs.getDate("fecha_fabricacion").toLocalDate(), // Convertir java.sql.Date a LocalDate
                        fabricante.getIdEmpresaFabricante()
                    );
                    
                    detalle.setFabricante(fabricante); // Establecer la relación de objeto
                    producto.setDetalleAltaTecnologia(detalle); // Establecer la relación en Producto
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("❌ ERROR al buscar producto por ID: " + e.getMessage());
        e.printStackTrace();
    }
    return producto;
}
    
@Override
public List<Producto> obtenerTodosLosProductos() {
    List<Producto> productos = new java.util.ArrayList<>();

    // La misma consulta SQL que en buscarProductoPorId, pero sin la cláusula WHERE
    String sql = "SELECT " +
                 "p.idProducto, p.nombre_producto, p.modelo, p.descripcion, " +
                 "p.categoria_producto_idcategoria, p.Detalle_alta_tecnologia_idDetalle_alta_tecnologia, " +
                 "cp.nombre AS nombre_categoria, " +
                 "dat.idDetalle_alta_tecnologia, dat.pais_origen, dat.fecha_fabricacion, " +
                 "ef.idEmpresa_fabricante, ef.nombre AS nombre_fabricante, ef.numero_empleados " +
                 "FROM producto p " +
                 "JOIN categoria_producto cp ON p.categoria_producto_idcategoria = cp.idcategoria " +
                 "LEFT JOIN detalle_alta_tecnologia dat ON p.Detalle_alta_tecnologia_idDetalle_alta_tecnologia = dat.idDetalle_alta_tecnologia " +
                 "LEFT JOIN empresa_fabricante ef ON dat.Empresa_fabricante_idEmpresa_fabricante = ef.idEmpresa_fabricante";

    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            
            Producto producto = new Producto();
            producto.setIdProducto(rs.getInt("idProducto"));
            producto.setNombreProducto(rs.getString("nombre_producto"));
            producto.setModelo(rs.getString("modelo"));
            producto.setDescripcion(rs.getString("descripcion"));
            
            // Mapear Categoría
            producto.setCategoriaProductoId(rs.getInt("categoria_producto_idcategoria"));
            //Asume que tienes la clase CategoriaProducto
            CategoriaProducto categoria = new CategoriaProducto(
                producto.getCategoriaProductoId(), 
                rs.getString("nombre_categoria")
            );
            producto.setCategoria(categoria);
            
            Integer detalleIdFK = (Integer) rs.getObject("Detalle_alta_tecnologia_idDetalle_alta_tecnologia");
            producto.setDetalleAltaTecnologiaId(detalleIdFK);
            
            // Mapear DetalleAltaTecnologia y Fabricante
            if (detalleIdFK != null) {
                // Asume que tienes las clases EmpresaFabricante y DetalleAltaTecnologia
                EmpresaFabricante fabricante = new EmpresaFabricante(
                    rs.getInt("idEmpresa_fabricante"), 
                    rs.getString("nombre_fabricante"), 
                    rs.getInt("numero_empleados")
                );
                
                DetalleAltaTecnologia detalle = new DetalleAltaTecnologia(
                    rs.getInt("idDetalle_alta_tecnologia"), 
                    rs.getString("pais_origen"), 
                    rs.getDate("fecha_fabricacion").toLocalDate(),
                    fabricante.getIdEmpresaFabricante()
                );
                
                detalle.setFabricante(fabricante);
                producto.setDetalleAltaTecnologia(detalle);
            }
            productos.add(producto);
        }
    } catch (SQLException e) {
        System.err.println("❌ ERROR al listar productos: " + e.getMessage());
        e.printStackTrace();
    }
    return productos;
}


    @Override
    public boolean actualizarProducto(Producto producto) {
        boolean exito = false;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            DetalleAltaTecnologia detalle = producto.getDetalleAltaTecnologia();
            

            //ACTUALIZAR FABRICANTE Y DETALLE (Si es Producto de Alta Tecnología)

            if (detalle != null) {
                
                // Asegúrate de que el producto *ya exista* y tenga IDs asignados.
                if (producto.getDetalleAltaTecnologiaId() == null || producto.getDetalleAltaTecnologiaId() == 0) {
                    // Esto no debería suceder si el producto se insertó correctamente.
                    System.err.println("Error: Producto de Alta Tecnología sin ID de Detalle.");
                    conn.rollback();
                    return false;
                }
                
                //Actualizar Empresa Fabricante
                EmpresaFabricante fabricante = detalle.getFabricante();
                
                String sqlUpdateFabricante = "UPDATE empresa_fabricante SET nombre = ?, numero_empleados = ? WHERE idEmpresa_fabricante = ?";
                try (PreparedStatement psFab = conn.prepareStatement(sqlUpdateFabricante)) {
                    psFab.setString(1, fabricante.getNombre());
                    psFab.setInt(2, fabricante.getNumeroEmpleados());
                    psFab.setInt(3, fabricante.getIdEmpresaFabricante()); // Se actualiza por ID
                    psFab.executeUpdate();
                }
                
                //Actualizar Detalle de Alta Tecnología
                String sqlUpdateDetalle = "UPDATE detalle_alta_tecnologia SET pais_origen = ?, fecha_fabricacion = ? WHERE idDetalle_alta_tecnologia = ?";
                try (PreparedStatement psDetalle = conn.prepareStatement(sqlUpdateDetalle)) {
                    psDetalle.setString(1, detalle.getPaisOrigen());
                    psDetalle.setDate(2, Date.valueOf(detalle.getFechaFabricacion()));
                    psDetalle.setInt(3, producto.getDetalleAltaTecnologiaId());
                    psDetalle.executeUpdate();
                }
            }



            // ACTUALIZAR el Producto

            String sqlUpdateProducto = "UPDATE producto SET nombre_producto = ?, modelo = ?, descripcion = ?, categoria_producto_idcategoria = ? WHERE idProducto = ?";
            try (PreparedStatement psProducto = conn.prepareStatement(sqlUpdateProducto)) {

                psProducto.setString(1, producto.getNombreProducto());
                psProducto.setString(2, producto.getModelo());
                psProducto.setString(3, producto.getDescripcion());
                psProducto.setInt(4, producto.getCategoriaProductoId()); 
                psProducto.setInt(5, producto.getIdProducto()); // Cláusula WHERE
                
                if (psProducto.executeUpdate() > 0) {
                    exito = true;
                }
            }

            if (exito) {
                conn.commit(); 
            } else {
                conn.rollback();
            }
            return exito;

        } catch (SQLException e) {
            System.err.println("❌ ERROR FATAL AL ACTUALIZAR PRODUCTO (ROLLBACK EJECUTADO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(int id) {
    
    // Primero, recuperamos el producto para saber si tiene detalle de alta tecnología.
    // Usamos el método que ya implementaste.
    Producto productoAEliminar = buscarProductoPorId(id); 

    if (productoAEliminar == null) {
        System.out.println("ADVERTENCIA: Producto con ID " + id + " no encontrado. No se necesita eliminar.");
        return true; // Consideramos éxito si no existe
    }

    // Obtenemos los IDs de las tablas relacionadas (si existen)
    Integer detalleId = productoAEliminar.getDetalleAltaTecnologiaId();
    Integer fabricanteId = null;
    if (detalleId != null && productoAEliminar.getDetalleAltaTecnologia() != null) {
        fabricanteId = productoAEliminar.getDetalleAltaTecnologia().getFabricante().getIdEmpresaFabricante();
    }
    
    // Conexión y Transacción
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
        conn.setAutoCommit(false); // 🚀 Iniciar Transacción

        // -------------------------------------------------------------------
        // PASO 1: ELIMINAR el Producto Principal (para liberar la FK)
        // -------------------------------------------------------------------
        String sqlDeleteProducto = "DELETE FROM producto WHERE idProducto = ?";
        try (PreparedStatement psProducto = conn.prepareStatement(sqlDeleteProducto)) {
            psProducto.setInt(1, id);
            if (psProducto.executeUpdate() == 0) {
                System.err.println("Error: No se eliminó el producto con ID " + id);
                conn.rollback();
                return false;
            }
        }
        
        // -------------------------------------------------------------------
        // PASO 2: ELIMINAR DETALLE_ALTA_TECNOLOGIA (Si existía)
        // -------------------------------------------------------------------
        if (detalleId != null) {
            String sqlDeleteDetalle = "DELETE FROM detalle_alta_tecnologia WHERE idDetalle_alta_tecnologia = ?";
            try (PreparedStatement psDetalle = conn.prepareStatement(sqlDeleteDetalle)) {
                psDetalle.setInt(1, detalleId);
                // No verificamos el resultado, ya que podría ser referenciado por otro producto.
                // Si la FK de Fabricante es ON DELETE NO ACTION, la eliminación del detalle fallará si el fabricante es compartido.
                psDetalle.executeUpdate();
            }
        }
        
        // -------------------------------------------------------------------
        // PASO 3: ELIMINAR EMPRESA_FABRICANTE (Si existía y si no hay más referencias)
        // -------------------------------------------------------------------
        // Nota: Solo se debe eliminar el fabricante si ya no está referenciado por *ningún* otro DetalleAltaTecnologia.
        // Asumimos que la BD maneja la restricción de FK aquí.
        if (fabricanteId != null) {
            String sqlDeleteFabricante = "DELETE FROM empresa_fabricante WHERE idEmpresa_fabricante = ?";
            try (PreparedStatement psFab = conn.prepareStatement(sqlDeleteFabricante)) {
                psFab.setInt(1, fabricanteId);
                psFab.executeUpdate();
            }
        }
        
        conn.commit(); // ✅ Confirmar la transacción
        return true;

    } catch (SQLException e) {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            // Ignorar error al hacer rollback
        }
        System.err.println("❌ ERROR FATAL AL ELIMINAR PRODUCTO (ROLLBACK EJECUTADO): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
        }
}