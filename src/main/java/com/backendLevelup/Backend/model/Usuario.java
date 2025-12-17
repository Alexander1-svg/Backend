package com.backendLevelup.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@ToString
@Table(name = "Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo nombre de usuario es obligatorio")
    @Size(min=6, max=120)
    private String nombre;

    private Boolean enabled;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    @Column(unique = true,   nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "El campo Email es obligatorio")
    @Size(min=9, max=120)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "El campo contraseña es obligatorio")
    @Size(min=6, max=120)
    private String password;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private boolean tieneDescuentoDuoc = false;

    @Embedded
    private Audit audit = new Audit();

    @JsonIgnoreProperties({"Usuarios"})
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "rol_id"})}
    )
    private List<Rol> roles = new ArrayList<>();

    public Usuario(String nombre, String email, String password, LocalDate fechaNacimiento, boolean tieneDescuentoDuoc){
        this();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.tieneDescuentoDuoc = tieneDescuentoDuoc;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return new ArrayList<>();
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta nunca expira (puedes cambiar lógica si quieres)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta nunca se bloquea
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        return this.enabled != null && this.enabled; // Usamos tu campo 'enabled'
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", tieneDescuentoDuoc=" + tieneDescuentoDuoc +
                ", createdAt=" + audit.getCreatedAt() +
                ", updatedAt=" + audit.getUpdatedAt() +
                '}';
    }

    @PrePersist
    public void prePersist() {this.enabled = true;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nombre, usuario.nombre);
    }

    @Override
    public int hashCode(){return Objects.hash(id, nombre);}
}