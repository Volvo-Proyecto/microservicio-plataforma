package duoc.cl.plataforma.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import duoc.cl.plataforma.DTOs.PlataformaPedidoDTO;
import duoc.cl.plataforma.DTOs.PlataformaRespuestaDTO;
import duoc.cl.plataforma.Model.Plataforma;
import duoc.cl.plataforma.Repository.PlataformaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlataformaService {
private final PlataformaRepository repositorio;

    // ── Listar todas ──────────────────────────────────────────
    public List<PlataformaRespuestaDTO> obtenerTodas() {
        return repositorio.findAll()
                .stream()
                .map(this::mapearRespuesta)
                .collect(Collectors.toList());
    }

    // ── Obtener por ID ────────────────────────────────────────
    public PlataformaRespuestaDTO obtenerPorId(Long id) {
        Plataforma plataforma = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Plataforma no encontrada con ID: " + id));
        return mapearRespuesta(plataforma);
    }

    // ── Crear ─────────────────────────────────────────────────
    public PlataformaRespuestaDTO crear(PlataformaPedidoDTO pedido) {

        if (repositorio.findByNombre(pedido.getNombre()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una plataforma con el nombre: " + pedido.getNombre());
        }

        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(pedido.getNombre());
        plataforma.setManufacturador(pedido.getManufacturador());

        Plataforma guardada = repositorio.save(plataforma);
        return mapearRespuesta(guardada);
    }

    // ── Actualizar ────────────────────────────────────────────
    public PlataformaRespuestaDTO actualizar(Long id, PlataformaPedidoDTO pedido) {
        Plataforma plataforma = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Plataforma no encontrada con ID: " + id));

        plataforma.setNombre(pedido.getNombre());
        plataforma.setManufacturador(pedido.getManufacturador());

        Plataforma actualizada = repositorio.save(plataforma);
        return mapearRespuesta(actualizada);
    }

    // ── Eliminar ──────────────────────────────────────────────
    public void eliminar(Long id) {
        if (!repositorio.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Plataforma no encontrada con ID: " + id);
        }
        repositorio.deleteById(id);
    }

    // ── Mapeo entidad → DTO de respuesta ───────────────────────
    private PlataformaRespuestaDTO mapearRespuesta(Plataforma plataforma) {
        PlataformaRespuestaDTO respuesta = new PlataformaRespuestaDTO();
        respuesta.setId(plataforma.getId());
        respuesta.setNombre(plataforma.getNombre());
        respuesta.setManufacturador(plataforma.getManufacturador());
        return respuesta;
    }
}