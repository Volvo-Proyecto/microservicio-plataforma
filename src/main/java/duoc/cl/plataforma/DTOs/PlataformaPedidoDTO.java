package duoc.cl.plataforma.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlataformaPedidoDTO {
    @NotBlank(message = "El nombre de la plataforma es obligatorio")
    @Size(max = 100, message = "El nombre de la plataforma no puede exceder los 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "El manufacturador no puede exceder los 100 caracteres")
    private String manufacturador;
}
