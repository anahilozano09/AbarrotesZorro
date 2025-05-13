package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emp")
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Usuario")
    private String usuario;

    @Column(name="Password")
    private String contrasena;

    @Column(name="Email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_rol",nullable = false)
    private RolEmpleadoEntity idRol;

}
