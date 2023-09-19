package tech.homedeveloper.testedeliverit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.homedeveloper.testedeliverit.controller.RegraCobrancaController;
import tech.homedeveloper.testedeliverit.database.model.RegraCobranca;
import tech.homedeveloper.testedeliverit.service.RegraCobrancaService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegraCobrancaController.class)
@AutoConfigureMockMvc
public class RegraCobrancaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegraCobrancaService regraCobrancaService;

    @Test
    public void testSalvarRegraCobranca() throws Exception {
        RegraCobranca regraCobranca = new RegraCobranca();
        regraCobranca.setId(1L);
        regraCobranca.setLimiteDias(3);
        regraCobranca.setMulta(BigDecimal.valueOf(0.02));

        when(regraCobrancaService.salvarRegraCobranca(any())).thenReturn(regraCobranca);

        mockMvc.perform(post("/regracobranca")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regraCobranca)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.limiteDias").value(3))
                .andExpect(jsonPath("$.multa").value(0.02));
    }

    @Test
    public void testListarRegrasCobranca() throws Exception {
        List<RegraCobranca> regrasCobranca = new ArrayList<>();
        RegraCobranca regra1 = new RegraCobranca();
        regra1.setId(1L);
        regra1.setLimiteDias(3);
        regra1.setMulta(BigDecimal.valueOf(0.02));
        RegraCobranca regra2 = new RegraCobranca();
        regra2.setId(2L);
        regra2.setLimiteDias(5);
        regra2.setMulta(BigDecimal.valueOf(0.03));
        regrasCobranca.add(regra1);
        regrasCobranca.add(regra2);

        when(regraCobrancaService.listarRegrasCobranca()).thenReturn(regrasCobranca);

        mockMvc.perform(get("/regracobranca"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].limiteDias").value(3))
                .andExpect(jsonPath("$[0].multa").value(0.02))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].limiteDias").value(5))
                .andExpect(jsonPath("$[1].multa").value(0.03));
    }

    @Test
    public void testGetRegraCobranca() throws Exception {
        Long id = 1L;
        RegraCobranca regraCobranca = new RegraCobranca();
        regraCobranca.setId(id);
        regraCobranca.setLimiteDias(3);
        regraCobranca.setMulta(BigDecimal.valueOf(0.02));

        when(regraCobrancaService.getRegraCobranca(id)).thenReturn(regraCobranca);

        mockMvc.perform(get("/regracobranca/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.limiteDias").value(3))
                .andExpect(jsonPath("$.multa").value(0.02));
    }

    @Test
    public void testGetRegraCobrancaNaoExistente() throws Exception {
        Long id = 1L;
        when(regraCobrancaService.getRegraCobranca(id)).thenReturn(null);

        mockMvc.perform(get("/regracobranca/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAtualizarRegraCobranca() throws Exception {
        Long id = 1L;
        RegraCobranca regraCobranca = new RegraCobranca();
        regraCobranca.setId(id);
        regraCobranca.setLimiteDias(3);
        regraCobranca.setMulta(BigDecimal.valueOf(0.02));

        when(regraCobrancaService.atualizarRegraCobranca(eq(id), any())).thenReturn(regraCobranca);

        mockMvc.perform(put("/regracobranca/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regraCobranca)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.limiteDias").value(3))
                .andExpect(jsonPath("$.multa").value(0.02));
    }

    @Test
    public void testAtualizarRegraCobrancaNaoExistente() throws Exception {
        Long id = 1L;
        RegraCobranca regraCobranca = new RegraCobranca();
        when(regraCobrancaService.atualizarRegraCobranca(eq(id), any())).thenReturn(null);

        mockMvc.perform(put("/regracobranca/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regraCobranca)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testExcluirRegraCobranca() throws Exception {
        Long id = 1L;
        doNothing().when(regraCobrancaService).excluirRegraCobranca(id);

        mockMvc.perform(delete("/regracobranca/{id}", id))
                .andExpect(status().isOk());
    }
}
