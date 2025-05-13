package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "historico_productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoProductosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Histo")
    private Long id;

    @Column(name="Cantidad")
    private String cantidad;

    @Column(name="Fecha")
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_cad",nullable = false)
    private RolEmpleadoEntity idCad;

    @ManyToOne
    @JoinColumn(name = "id_prodcomp",nullable = false)
    private RolEmpleadoEntity idProdcomp;

}


