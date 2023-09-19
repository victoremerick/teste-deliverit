package tech.homedeveloper.testedeliverit.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class ContaAPagarAtualizadaDTO {

    private String nome;
    private BigDecimal valorOriginal;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valorCorrigido;
    private int diasEmAtraso;
}

