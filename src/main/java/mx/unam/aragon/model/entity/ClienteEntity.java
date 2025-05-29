package mx.unam.aragon.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clien", nullable = false)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name="nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Column(name="num_cuenta", nullable = false)
    private String numCuenta;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Column(name="email", nullable = false)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{10}$", message = "El número de teléfono debe contener exactamente 10 dígitos")
    @Column(name="telefono", nullable = false)
    private String telefono;
}
