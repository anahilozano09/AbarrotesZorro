package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prov", nullable = false)
    private Long id;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="cantidad", nullable = false)
    private Integer cantidad;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="telefono", nullable = false)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_pro",nullable = false)
    private ProductoEntity producto;

}
