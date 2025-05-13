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
    @Column(name = "id_cad", nullable = false)
    private Long id;

    @Column(name="cantidad", nullable = false)
    private String cantidad;

    @ManyToOne
    @JoinColumn(name = "id_pro",nullable = false)
    private ProductoEntity producto;

}


