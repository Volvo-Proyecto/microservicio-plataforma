package duoc.cl.plataforma;

import duoc.cl.plataforma.DTOs.*;

import duoc.cl.plataforma.Model.Plataforma;
import duoc.cl.plataforma.Repository.PlataformaRepository;
import duoc.cl.plataforma.Service.PlataformaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlataformaServiceTest {

    @Mock
    private PlataformaRepository repositorio;

    @InjectMocks
    private PlataformaService servicio;

    private Plataforma plataforma;
    private PlataformaPedidoDTO pedido;

    @BeforeEach
    void setUp() {
        plataforma = new Plataforma();
        plataforma.setId(1L);
        plataforma.setNombre("PC");
        plataforma.setManufacturador("Varios");

        pedido = new PlataformaPedidoDTO();
        pedido.setNombre("PC");
        pedido.setManufacturador("Varios");
    }

    @Test
    void deberiaObtenerTodasLasPlataformas() {
       
        when(repositorio.findAll()).thenReturn(List.of(plataforma));

       
        List<PlataformaRespuestaDTO> resultado = servicio.obtenerTodas();

       
        assertEquals(1, resultado.size());
        assertEquals("PC", resultado.get(0).getNombre());
    }

    @Test
    void deberiaObtenerPlataformaPorId() {
       
        when(repositorio.findById(1L)).thenReturn(Optional.of(plataforma));

        
        PlataformaRespuestaDTO resultado = servicio.obtenerPorId(1L);

      
        assertNotNull(resultado);
        assertEquals("PC", resultado.getNombre());
    }

    @Test
    void deberiaLanzarExcepcionSiPlataformaNoExiste() {
        
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

       
        assertThrows(ResponseStatusException.class, () -> servicio.obtenerPorId(99L));
    }

    @Test
    void deberiaCrearPlataformaCorrectamente() {
       
        when(repositorio.findByNombre("PC")).thenReturn(Optional.empty());
        when(repositorio.save(any(Plataforma.class))).thenReturn(plataforma);

        
        PlataformaRespuestaDTO resultado = servicio.crear(pedido);

       
        assertNotNull(resultado);
        assertEquals("PC", resultado.getNombre());
        verify(repositorio, times(1)).save(any(Plataforma.class));
    }

    @Test
    void noDeberiaCrearPlataformaDuplicada() {
        
        when(repositorio.findByNombre("PC")).thenReturn(Optional.of(plataforma));

      
        assertThrows(ResponseStatusException.class, () -> servicio.crear(pedido));
        verify(repositorio, never()).save(any());
    }

    @Test
    void deberiaActualizarPlataformaExistente() {
       
        PlataformaPedidoDTO pedidoActualizado = new PlataformaPedidoDTO();
        pedidoActualizado.setNombre("PC Gaming");
        pedidoActualizado.setManufacturador("Varios");

        when(repositorio.findById(1L)).thenReturn(Optional.of(plataforma));
        when(repositorio.save(any(Plataforma.class))).thenReturn(plataforma);

       
        PlataformaRespuestaDTO resultado = servicio.actualizar(1L, pedidoActualizado);

      
        assertNotNull(resultado);
        verify(repositorio, times(1)).save(plataforma);
    }

    @Test
    void deberiaEliminarPlataformaExistente() {
       
        when(repositorio.existsById(1L)).thenReturn(true);

     
        servicio.eliminar(1L);

      
        verify(repositorio, times(1)).deleteById(1L);
    }

    @Test
    void noDeberiaEliminarPlataformaInexistente() {
       
        when(repositorio.existsById(99L)).thenReturn(false);

    
        assertThrows(ResponseStatusException.class, () -> servicio.eliminar(99L));
    }
}