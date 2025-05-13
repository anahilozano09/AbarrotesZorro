package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "compra_cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long id;

    @Column(name="total")
    private Double total;

    @Column(name="Fecha")
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = false)
    private RolEmpleadoEntity idCliente;

    @ManyToOne
    @JoinColumn(name = "id_emp",nullable = false)
    private RolEmpleadoEntity idEmp;

    @ManyToOne
    @JoinColumn(name = "id_prodcomp",nullable = false)
    private RolEmpleadoEntity idProdcomp;


}

