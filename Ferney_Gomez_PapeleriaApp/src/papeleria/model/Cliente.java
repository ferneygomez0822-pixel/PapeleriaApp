package papeleria.model;

public class Cliente {

    private int id;
    private String nombre;
    private String cedulaRuc;
    private String telefono;
    private String correo;
    private String direccion;

    public Cliente() {
        // Constructor vacío funcional
    }

    public Cliente(int id, String nombre, String cedulaRuc, String telefono, String correo, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.cedulaRuc = cedulaRuc;
        this.telefono = telefono;
        this.correo = correo;
       	this.direccion = direccion;
    }

    public Cliente(String nombre, String cedulaRuc, String telefono, String correo, String direccion) {
        this.nombre = nombre;
        this.cedulaRuc = cedulaRuc;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    // GETTERS
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCedulaRuc() { return cedulaRuc; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCedulaRuc(String cedulaRuc) { this.cedulaRuc = cedulaRuc; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
