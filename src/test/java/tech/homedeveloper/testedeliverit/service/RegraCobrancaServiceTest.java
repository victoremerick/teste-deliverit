package tech.homedeveloper.testedeliverit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.homedeveloper.testedeliverit.database.model.RegraCobranca;
import tech.homedeveloper.testedeliverit.database.repository.RegraCobrancaRepository;
import tech.homedeveloper.testedeliverit.service.RegraCobrancaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegraCobrancaServiceTest {

    @InjectMocks
    private RegraCobrancaService regraCobrancaService;

    @Mock
    private RegraCobrancaRepository regraCobrancaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarRegrasCobranca() {
        List<RegraCobranca> regrasCobranca = new ArrayList<>();
        when(regraCobrancaRepository.findAll()).thenReturn(regrasCobranca);

        List<RegraCobranca> result = regraCobrancaService.listarRegrasCobranca();

        assertNotNull(result);
        assertEquals(regrasCobranca, result);
    }

    @Test
    public void testGetRegraCobrancaExistente() {
        Long id = 1L;
        RegraCobranca regraCobranca = new RegraCobranca();
        regraCobranca.setId(id);
        when(regraCobrancaRepository.findById(id)).thenReturn(Optional.of(regraCobranca));

        RegraCobranca result = regraCobrancaService.getRegraCobranca(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testGetRegraCobrancaNaoExistente() {
        Long id = 1L;
        when(regraCobrancaRepository.findById(id)).thenReturn(Optional.empty());

        RegraCobranca result = regraCobrancaService.getRegraCobranca(id);

        assertNull(result);
    }
    @Test
    public void testSalvarRegraCobranca() {
        RegraCobranca regraCobranca = new RegraCobranca();
        when(regraCobrancaRepository.save(regraCobranca)).thenReturn(regraCobranca);

        RegraCobranca result = regraCobrancaService.salvarRegraCobranca(regraCobranca);

        assertNotNull(result);
        assertEquals(regraCobranca, result);
    }

    @Test
    public void testAtualizarRegraCobrancaExistente() {
        Long id = 1L;
        RegraCobranca regraCobranca = new RegraCobranca();
        regraCobranca.setId(id);
        when(regraCobrancaRepository.existsById(id)).thenReturn(true);
        when(regraCobrancaRepository.save(regraCobranca)).thenReturn(regraCobranca);

        RegraCobranca result = regraCobrancaService.atualizarRegraCobranca(id, regraCobranca);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testAtualizarRegraCobrancaNaoExistente() {
        Long id = 1L;
        RegraCobranca regraCobranca = new RegraCobranca();
        when(regraCobrancaRepository.existsById(id)).thenReturn(false);

        RegraCobranca result = regraCobrancaService.atualizarRegraCobranca(id, regraCobranca);

        assertNull(result);
    }

    @Test
    public void testExcluirRegraCobranca() {
        Long id = 1L;
        doNothing().when(regraCobrancaRepository).deleteById(id);

        assertDoesNotThrow(() -> regraCobrancaService.excluirRegraCobranca(id));
    }
}
