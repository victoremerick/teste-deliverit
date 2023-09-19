package tech.homedeveloper.testedeliverit.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class ContaAPagarDTO {

    private String nome;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
}

