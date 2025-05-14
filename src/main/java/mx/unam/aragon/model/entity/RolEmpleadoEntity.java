package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "rol_empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolEmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false)
    private Long id;

    @Column(name="rol", nullable = false)
    private String rol;

}
