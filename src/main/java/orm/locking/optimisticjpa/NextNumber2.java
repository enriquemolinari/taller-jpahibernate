package orm.locking.optimisticjpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "nextnumber_con_version")
public class NextNumber2 {

	@Id
	private long id;

	private int anio;
	private int actual;

	@Version
	private int version;

	public NextNumber2(int anio, int actual) {
		this.anio = anio;
		this.actual = actual;
	}

	protected NextNumber2() {
	}

	public int recuperarSiguiente() {
		this.actual += 1;
		return this.actual;
	}

	public int anio() {
		return this.anio;
	}

	public int actual() {
		return this.actual;
	}

	private long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	private int getAnio() {
		return anio;
	}

	private void setAnio(int anio) {
		this.anio = anio;
	}

	private int getActual() {
		return actual;
	}

	private void setActual(int actual) {
		this.actual = actual;
	}

}
