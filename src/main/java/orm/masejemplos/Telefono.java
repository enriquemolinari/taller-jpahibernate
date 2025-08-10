package orm.masejemplos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Telefono {

    @Id
    @GeneratedValue
    private int id;

    private String nro;

    public Telefono() {
    }

    public Telefono(String nro) {
        this.nro = nro;
    }

    @Override
    public String toString() {
        return "Telefono [id=" + id + ", nro=" + nro + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nro == null) ? 0 : nro.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Telefono other = (Telefono) obj;
        if (nro == null) {
            if (other.nro != null)
                return false;
        } else if (!nro.equals(other.nro))
            return false;
        return true;
    }

    private String getNro() {
        return nro;
    }

    private void setNro(String nro) {
        this.nro = nro;
    }

    private int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public boolean is(String unTelefono) {
        return this.nro.equals(unTelefono);
    }
}
