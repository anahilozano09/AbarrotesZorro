package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.unam.aragon.service.Empleado.EmpleadoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

@Entity(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emp", nullable = false)
    private Long id;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="telefono", nullable = false)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_rol",nullable = false)
    private RolEmpleadoEntity rolEmpleado;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rolEmpleado.getRol()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
