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
    @Column(name = "id_pro")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="precio")
    private Double precio;

    @Column(name="Img_pro")
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_tprd",nullable = false)
    private RolEmpleadoEntity idtProd;

}
