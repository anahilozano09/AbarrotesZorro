package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pro", nullable = false)
    private Long id;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="precio", nullable = false)
    private Double precio;

    @Column(name="img_pro", nullable = false)
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_tprd",nullable = false)
    private TipoProductoEntity tipoProducto;

}
