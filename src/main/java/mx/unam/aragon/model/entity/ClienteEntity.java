package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clien", nullable = false)
    private Long id;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="num_cuenta", nullable = false)
    private String numCuenta;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="telefono", nullable = false)
    private String telefono;

}
