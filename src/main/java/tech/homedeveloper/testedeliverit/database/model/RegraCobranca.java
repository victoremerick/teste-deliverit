package tech.homedeveloper.testedeliverit.database.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import tech.homedeveloper.testedeliverit.enums.OperadorRegra;

import java.math.BigDecimal;

@Entity
@Data
public class RegraCobranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OperadorRegra operador;
    private int limiteDias;
    private BigDecimal multa;
    private BigDecimal taxaAoDia;
}

