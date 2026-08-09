package orm.context;

public class Autor {
    private String nombre;
    private String apellido;

    public Autor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String nombreCompleto() {
        return nombre + " " + apellido;
    }
}
