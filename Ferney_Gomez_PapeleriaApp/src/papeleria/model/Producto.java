package papeleria.model;

public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private int idCategoria;
    private double precioVenta;
    private int stock;

    // Constructor vacío NECESARIO para listar(), actualizar(), etc.
    public Producto() {
    }

    // Constructor completo (6 parámetros)
    public Producto(int id, String nombre, String descripcion, int idCategoria, double precioVenta, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    // Constructor que estabas tratando de usar (5 parámetros)
    public Producto(int id, String nombre, int idCategoria, double precioVenta, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = ""; // valor por defecto
        this.idCategoria = idCategoria;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    public Producto(int aInt, String string, double aDouble, int aInt0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // GETTERS & SETTERS...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public void setPrecio(double precio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getPrecio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
