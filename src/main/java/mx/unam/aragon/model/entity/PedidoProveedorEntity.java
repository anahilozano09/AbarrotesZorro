package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "pedido_proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedprov", nullable = false)
    private Long id;

    @Column(name="cantidad", nullable = false)
    private Integer cantidad;

    @OneToOne
    @JoinColumn(name = "id_pro",nullable = false)
    private ProductoEntity producto;

}
