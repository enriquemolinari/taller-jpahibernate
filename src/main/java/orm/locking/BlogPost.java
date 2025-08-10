package orm.locking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class BlogPost {

	@Id
	@GeneratedValue
	private long id;

	private String title;

	private String text;

	@Version
	private int version;

	public BlogPost(String title, String text) {
		this.title = title;
		this.text = text;
	}

	public BlogPost(long id, String title, String text, int currentVersion) {
		this(title, text);
		this.id = id;
		this.version = currentVersion;
	}

	public void nuevoTexto(String texto) {
		this.text = texto;
	}

	public void nuevoTitulo(String nuevoTitulo) {
		this.title = nuevoTitulo;
	}

	protected BlogPost() {

	}

	private long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	private String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	private String getText() {
		return text;
	}

	private void setText(String text) {
		this.text = text;
	}

	private int getVersion() {
		return version;
	}

	private void setVersion(int version) {
		this.version = version;
	}

	public boolean sameVersion(int versionReadInPreviousTx) {
		return this.version == versionReadInPreviousTx;
	}
}
