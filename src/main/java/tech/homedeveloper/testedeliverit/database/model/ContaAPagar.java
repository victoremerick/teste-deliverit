package tech.homedeveloper.testedeliverit.database.model;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "contas_a_pagar")
@Data
public class ContaAPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
}

