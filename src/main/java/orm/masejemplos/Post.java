package orm.masejemplos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;
    private String texto;
    private String titulo;
    //	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL)
    private List<PostComment> comentarios;

    protected Post() {
    }

    public Post(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
        this.comentarios = new ArrayList<>();
    }

    public boolean hasComments() {
        return this.comentarios.size() > 0;
    }

    public void addComment(String comment) {
        this.comentarios.add(new PostComment(comment, this));
    }

    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private String getTexto() {
        return texto;
    }

    private void setTexto(String texto) {
        this.texto = texto;
    }

    private String getTitulo() {
        return titulo;
    }

    private void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    private List<PostComment> getComentarios() {
        return comentarios;
    }

    private void setComentarios(List<PostComment> comentarios) {
        this.comentarios = comentarios;
    }

}
