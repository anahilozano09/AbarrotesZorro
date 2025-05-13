package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tipo_producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tprod", nullable = false)
    private Long id;

    @Column(name="tipo", nullable = false)
    private String tipo;

}
