package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prov")
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Cantidad")
    private Integer cantidad;

    @Column(name="Email")
    private String email;

    @Column(name="Telefono")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_pro",nullable = false)
    private RolEmpleadoEntity idPro;

}
