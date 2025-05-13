package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emp")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="usuario")
    private String usuario;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_rol",nullable = false)
    private RolEmpleadoEntity idRol;

}
