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
    @Column(name = "id_prodcomp", nullable = false)
    private Long id;

    @Column(name="cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_pro",nullable = false)
    private ProductoEntity producto;


}



