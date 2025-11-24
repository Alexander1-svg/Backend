package com.backendLevelup.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "BlogPosts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenido;

    @Column(nullable = false)
    private LocalDateTime fechaPublicacion;

    private Boolean enabled;

    @Embedded
    private Audit audit = new Audit();

    @PrePersist
    public void prePersist() {this.enabled = true;}

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", autor=" + autor +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return id.equals(blog.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
