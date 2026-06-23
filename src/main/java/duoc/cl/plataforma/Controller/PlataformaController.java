package duoc.cl.plataforma.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.cl.plataforma.DTOs.PlataformaPedidoDTO;
import duoc.cl.plataforma.DTOs.PlataformaRespuestaDTO;
import duoc.cl.plataforma.Service.PlataformaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/plataformas")
@RequiredArgsConstructor
public class PlataformaController {

    private final PlataformaService servicio;

    @GetMapping
    public ResponseEntity<List<PlataformaRespuestaDTO>> obtenerTodas() {
        return ResponseEntity.ok(servicio.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlataformaRespuestaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlataformaRespuestaDTO> crear(@Valid @RequestBody PlataformaPedidoDTO pedido) {
        PlataformaRespuestaDTO creada = servicio.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlataformaRespuestaDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlataformaPedidoDTO pedido) {
        return ResponseEntity.ok(servicio.actualizar(id, pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}