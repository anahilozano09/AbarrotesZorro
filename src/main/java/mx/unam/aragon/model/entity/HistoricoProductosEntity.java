package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "historico_productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoProductosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_histo", nullable = false)
    private Long id;

    @Column(name="cantidad_act", nullable = false)
    private Integer cantidadAct;

    @ManyToOne
    @JoinColumn(name = "id_cad",nullable = false)
    private CantidadProductoAlmacenEntity cantidadProductoAlmacen;

    @ManyToOne
    @JoinColumn(name = "id_compra",nullable = false)
    private CompraClienteEntity compraCliente;

}


