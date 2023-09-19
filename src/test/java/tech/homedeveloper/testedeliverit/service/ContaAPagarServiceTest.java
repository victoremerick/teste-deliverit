package tech.homedeveloper.testedeliverit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.homedeveloper.testedeliverit.controller.dto.ContaAPagarAtualizadaDTO;
import tech.homedeveloper.testedeliverit.controller.dto.ContaAPagarDTO;
import tech.homedeveloper.testedeliverit.database.model.ContaAPagar;
import tech.homedeveloper.testedeliverit.database.model.RegraCobranca;
import tech.homedeveloper.testedeliverit.database.repository.ContaAPagarRepository;
import tech.homedeveloper.testedeliverit.enums.OperadorRegra;
import tech.homedeveloper.testedeliverit.service.ContaAPagarService;
import tech.homedeveloper.testedeliverit.service.RegraCobrancaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ContaAPagarServiceTest {

    @InjectMocks
    private ContaAPagarService contaAPagarService;

    @Mock
    private ContaAPagarRepository contaAPagarRepository;

    @Mock
    private RegraCobrancaService regraCobrancaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarContaAPagar() {
        ContaAPagarDTO contaAPagarDTO = new ContaAPagarDTO();
        contaAPagarDTO.setNome("Conta 1");
        contaAPagarDTO.setValor(BigDecimal.valueOf(100.00));
        contaAPagarDTO.setDataVencimento(LocalDate.now());
        contaAPagarDTO.setDataPagamento(LocalDate.now());

        ContaAPagar contaAPagar = new ContaAPagar();
        contaAPagar.setNome(contaAPagarDTO.getNome());
        contaAPagar.setValor(contaAPagarDTO.getValor());
        contaAPagar.setDataVencimento(contaAPagarDTO.getDataVencimento());
        contaAPagar.setDataPagamento(contaAPagarDTO.getDataPagamento());

        when(contaAPagarRepository.save(contaAPagar)).thenReturn(contaAPagar);

        ContaAPagar result = contaAPagarService.salvarContaAPagar(contaAPagarDTO);

        assertEquals(contaAPagar.getId(), result.getId());
        assertEquals(contaAPagar.getNome(), result.getNome());
        assertEquals(contaAPagar.getValor(), result.getValor());
        assertEquals(contaAPagar.getDataVencimento(), result.getDataVencimento());
        assertEquals(contaAPagar.getDataPagamento(), result.getDataPagamento());
    }

    @Test
    public void testCalcularValorCorrigido() {
        ContaAPagarAtualizadaDTO contaAPagar = new ContaAPagarAtualizadaDTO();
        contaAPagar.setValorOriginal(BigDecimal.valueOf(100.00));
        contaAPagar.setDiasEmAtraso(3);

        RegraCobranca regraCobranca = new RegraCobranca();
        regraCobranca.setOperador(OperadorRegra.INFERIOR);
        regraCobranca.setLimiteDias(3);
        regraCobranca.setMulta(BigDecimal.valueOf(0.02));
        regraCobranca.setTaxaAoDia(BigDecimal.valueOf(0.001));

        List<RegraCobranca> regrasCobranca = new ArrayList<>();
        regrasCobranca.add(regraCobranca);

        when(regraCobrancaService.listarRegrasCobranca()).thenReturn(regrasCobranca);

        BigDecimal resultadoEsperado = BigDecimal.valueOf(102.3); // Valor corrigido

        BigDecimal valorCorrigido = contaAPagarService.calcularValorCorrigido(contaAPagar);

        assertEquals(resultadoEsperado.doubleValue(), valorCorrigido.doubleValue());
    }
}

