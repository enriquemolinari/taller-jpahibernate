package orm.masejemplos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Dni {

    @Id
    @GeneratedValue
    private Long id;

    private String numero;

    protected Dni() {

    }

    public Dni(String numero) {
        // TODO: validar

        this.numero = numero;
    }

    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private String getNumero() {
        return numero;
    }

    private void setNumero(String numero) {
        this.numero = numero;
    }

}
