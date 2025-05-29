package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "detalle_compra_cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleCompraClienteEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_det", nullable = false)
    private Long id;

    @Column(name="cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_compra",nullable = false)
    private CompraClienteEntity compra;

    @ManyToOne
    @JoinColumn(name = "id_pro",nullable = false)
    private ProductoEntity producto;


}