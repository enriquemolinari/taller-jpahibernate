package orm.masejemplos;

import jakarta.persistence.*;

@Entity
public class PostComment {

    @Id
    @GeneratedValue
    private Long id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "id_post")
    private Post post;

    protected PostComment() {
    }

    public PostComment(String comment, Post post) {
        this.comment = comment;
        this.post = post;
    }

    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private String getComment() {
        return comment;
    }

    private void setComment(String comment) {
        this.comment = comment;
    }

    private Post getPost() {
        return post;
    }

    private void setPost(Post post) {
        this.post = post;
    }
}
