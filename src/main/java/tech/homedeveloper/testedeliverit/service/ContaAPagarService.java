package tech.homedeveloper.testedeliverit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.homedeveloper.testedeliverit.controller.dto.ContaAPagarAtualizadaDTO;
import tech.homedeveloper.testedeliverit.controller.dto.ContaAPagarDTO;
import tech.homedeveloper.testedeliverit.database.model.ContaAPagar;
import tech.homedeveloper.testedeliverit.database.model.RegraCobranca;
import tech.homedeveloper.testedeliverit.database.repository.ContaAPagarRepository;
import tech.homedeveloper.testedeliverit.enums.OperadorRegra;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaAPagarService {

    private final ContaAPagarRepository contaAPagarRepository;

    private final RegraCobrancaService regraCobrancaService;

    @Autowired
    public ContaAPagarService(ContaAPagarRepository contaAPagarRepository, RegraCobrancaService regraCobrancaService) {
        this.contaAPagarRepository = contaAPagarRepository;
        this.regraCobrancaService = regraCobrancaService;
    }

    public ContaAPagar salvarContaAPagar(ContaAPagarDTO contaAPagarDTO) {
        ContaAPagar contaAPagar = new ContaAPagar();
        contaAPagar.setNome(contaAPagarDTO.getNome());
        contaAPagar.setValor(contaAPagarDTO.getValor());
        contaAPagar.setDataVencimento(contaAPagarDTO.getDataVencimento());
        contaAPagar.setDataPagamento(contaAPagarDTO.getDataPagamento());

        return contaAPagarRepository.save(contaAPagar);
    }

    public List<ContaAPagar> listarContasAPagar(){
        return contaAPagarRepository.findAll();
    }

    public List<ContaAPagarAtualizadaDTO> listarContasAPagarCalculadas(){
        List<ContaAPagarAtualizadaDTO> contasComDados = new ArrayList<>();
        List<ContaAPagar> contasAPagar = listarContasAPagar();

        for (ContaAPagar contaAPagar : contasAPagar) {
            ContaAPagarAtualizadaDTO contaComDados = new ContaAPagarAtualizadaDTO();
            contaComDados.setNome(contaAPagar.getNome());
            contaComDados.setValorOriginal(contaAPagar.getValor());
            long days = ChronoUnit.DAYS.between(contaAPagar.getDataVencimento(), contaAPagar.getDataPagamento());
            if(days < 0){
                days = 0;
            }
            contaComDados.setDiasEmAtraso(Long.valueOf(days).intValue());
            contaComDados.setDataPagamento(contaAPagar.getDataPagamento());

            if(days > 0) {
                BigDecimal valorCorrigido = calcularValorCorrigido(contaComDados);
                contaComDados.setValorCorrigido(valorCorrigido);
            }

            contasComDados.add(contaComDados);
        }

        return contasComDados;
    }

    BigDecimal calcularValorCorrigido(ContaAPagarAtualizadaDTO contaAPagar) {
        List<RegraCobranca> regrasCobranca = regraCobrancaService.listarRegrasCobranca();
        BigDecimal valorCorrigido = contaAPagar.getValorOriginal();

        for (RegraCobranca regra : regrasCobranca) {
            if(regra.getOperador() == OperadorRegra.INFERIOR){
                if (contaAPagar.getDiasEmAtraso() <= regra.getLimiteDias()) {
                    BigDecimal multa = valorCorrigido.multiply(regra.getMulta());
                    BigDecimal taxaPorDia = valorCorrigido.multiply(regra.getTaxaAoDia());
                    valorCorrigido = valorCorrigido.add(multa).add(taxaPorDia.multiply(BigDecimal.valueOf(contaAPagar.getDiasEmAtraso())));
                    break;
                }
            }else{
                if (contaAPagar.getDiasEmAtraso() > regra.getLimiteDias()) {
                    BigDecimal multa = valorCorrigido.multiply(regra.getMulta());
                    BigDecimal taxaPorDia = valorCorrigido.multiply(regra.getTaxaAoDia());
                    valorCorrigido = valorCorrigido.add(multa).add(taxaPorDia.multiply(BigDecimal.valueOf(contaAPagar.getDiasEmAtraso())));
                    break;
                }
            }

        }

        return valorCorrigido;
    }
}

