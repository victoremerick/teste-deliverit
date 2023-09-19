package tech.homedeveloper.testedeliverit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.homedeveloper.testedeliverit.controller.ContaAPagarController;
import tech.homedeveloper.testedeliverit.controller.dto.ContaAPagarAtualizadaDTO;
import tech.homedeveloper.testedeliverit.controller.dto.ContaAPagarDTO;
import tech.homedeveloper.testedeliverit.service.ContaAPagarService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContaAPagarController.class)
@AutoConfigureMockMvc
public class ContaAPagarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContaAPagarService contaAPagarService;

    @Test
    public void testSalvarContaAPagar() throws Exception {
        ContaAPagarDTO contaAPagarDTO = new ContaAPagarDTO();
        contaAPagarDTO.setNome("Conta 1");
        contaAPagarDTO.setValor(BigDecimal.valueOf(100.00));
        contaAPagarDTO.setDataVencimento(LocalDate.now());
        contaAPagarDTO.setDataPagamento(LocalDate.now());

        mockMvc.perform(post("/contasapagar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contaAPagarDTO)))
                .andExpect(status().isAccepted());

        verify(contaAPagarService, times(1)).salvarContaAPagar(contaAPagarDTO);
    }

    @Test
    public void testListarContasComDados() throws Exception {
        List<ContaAPagarAtualizadaDTO> contasComDados = new ArrayList<>();
        ContaAPagarAtualizadaDTO contaComDados = new ContaAPagarAtualizadaDTO();
        contaComDados.setNome("Conta 1");
        contaComDados.setValorOriginal(BigDecimal.valueOf(100.00));
        contaComDados.setDiasEmAtraso(5);
        contaComDados.setDataPagamento(LocalDate.now());
        contaComDados.setValorCorrigido(BigDecimal.valueOf(102.015));
        contasComDados.add(contaComDados);

        when(contaAPagarService.listarContasAPagarCalculadas()).thenReturn(contasComDados);

        mockMvc.perform(get("/contasapagar/listarcomdados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value("Conta 1"))
                .andExpect(jsonPath("$[0].valorOriginal").value(100.00))
                .andExpect(jsonPath("$[0].diasEmAtraso").value(5))
                .andExpect(jsonPath("$[0].dataPagamento").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].valorCorrigido").value(102.015));
    }
}

