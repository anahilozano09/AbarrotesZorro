package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cantidad_producto_almacen")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CantidadProductoAlmacenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cad")
    private Long id;

    @Column(name="cantidad")
    private String cantidad;

    @Column(name="precio_unit")
    private String precioUnit;

    @ManyToOne
    @JoinColumn(name = "id_pro",nullable = false)
    private RolEmpleadoEntity idPro;

}


