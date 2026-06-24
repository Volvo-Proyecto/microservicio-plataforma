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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/plataformas")
@RequiredArgsConstructor
public class PlataformaController {

    private final PlataformaService servicio;

    @Operation(
        summary = "Listar todas las plataformas",
        description = "Devuelve la lista completa de plataformas registradas en el catálogo"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(value = """
                [
                  { "id": 1, "nombre": "PC", "manufacturador": "Varios" },
                  { "id": 2, "nombre": "PlayStation 5", "manufacturador": "Sony" }
                ]
                """)
        )
    )
    @GetMapping
    public ResponseEntity<List<PlataformaRespuestaDTO>> obtenerTodas() {
        return ResponseEntity.ok(servicio.obtenerTodas());
    }

    @Operation(
        summary = "Buscar plataforma por ID",
        description = "Devuelve una plataforma específica según su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Plataforma encontrada",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "id": 1, "nombre": "PC", "manufacturador": "Varios" }
                    """)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlataformaRespuestaDTO> obtenerPorId(
            @Parameter(description = "ID de la plataforma a buscar", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @Operation(
        summary = "Crear una nueva plataforma",
        description = "Registra una plataforma nueva en el catálogo. El nombre debe ser único."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Plataforma creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "id": 6, "nombre": "Steam Deck", "manufacturador": "Valve" }
                    """)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "409", description = "Ya existe una plataforma con ese nombre")
    })
    @PostMapping
    public ResponseEntity<PlataformaRespuestaDTO> crear(@Valid @RequestBody PlataformaPedidoDTO pedido) {
        PlataformaRespuestaDTO creada = servicio.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(
        summary = "Actualizar una plataforma existente",
        description = "Modifica el nombre y/o manufacturador de una plataforma ya registrada"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plataforma actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlataformaRespuestaDTO> actualizar(
            @Parameter(description = "ID de la plataforma a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PlataformaPedidoDTO pedido) {
        return ResponseEntity.ok(servicio.actualizar(id, pedido));
    }

    @Operation(
        summary = "Eliminar una plataforma",
        description = "Elimina una plataforma del catálogo de forma permanente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Plataforma eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la plataforma a eliminar", example = "1")
            @PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}