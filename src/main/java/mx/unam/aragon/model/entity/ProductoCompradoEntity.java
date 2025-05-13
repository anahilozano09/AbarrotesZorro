package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "producto_comprado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoCompradoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prodcomp")
    private Long id;

    @Column(name="cantidad")
    private String cantidad;

    @Column(name="precio_unit")
    private Double precioUnit;

    @ManyToOne
    @JoinColumn(name = "id_pro",nullable = false)
    private RolEmpleadoEntity idPro;


}



