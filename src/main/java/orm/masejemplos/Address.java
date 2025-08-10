package orm.masejemplos;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String direccion;

    public Address(String direccion) {
        // TODO: validate !
        this.direccion = direccion;
    }

    protected Address() {

    }

    private String getDireccion() {
        return direccion;
    }

    private void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean is(String unaDireccion) {
        return this.direccion.equals(unaDireccion);
    }
}
