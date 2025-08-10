package orm.locking;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NextNumber {

	@Id
	private long id;

	private int anio;
	private int actual;

	public NextNumber(int anio, int actual) {
		this.anio = anio;
		this.actual = actual;
	}

	protected NextNumber() {
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
