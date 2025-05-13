package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "compra_cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra", nullable = false)
    private Long id;

    @Column(name="total", nullable = false)
    private Double total;

    @Column(name="fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = false)
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "id_emp",nullable = false)
    private EmpleadoEntity empleado;

    @ManyToOne
    @JoinColumn(name = "id_prodcomp",nullable = false)
    private ProductoCompradoEntity productoComprado;


}

