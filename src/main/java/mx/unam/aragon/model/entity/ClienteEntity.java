package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clien")
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Usuario")
    private String usuario;

    @Column(name="Password")
    private String contrasena;

    @Column(name="Email")
    private String email;

    @Column(name="Telefono")
    private Integer telefono;

}
