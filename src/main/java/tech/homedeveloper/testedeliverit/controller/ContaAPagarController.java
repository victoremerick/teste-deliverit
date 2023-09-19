package tech.homedeveloper.testedeliverit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.homedeveloper.testedeliverit.controller.dto.ContaAPagarAtualizadaDTO;
import tech.homedeveloper.testedeliverit.controller.dto.ContaAPagarDTO;
import tech.homedeveloper.testedeliverit.database.model.ContaAPagar;
import tech.homedeveloper.testedeliverit.service.ContaAPagarService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contasapagar")
public class ContaAPagarController {

    private final ContaAPagarService contaAPagarService;

    @Autowired
    public ContaAPagarController(ContaAPagarService contaAPagarService) {
        this.contaAPagarService = contaAPagarService;
    }

    @PostMapping
    public ResponseEntity<ContaAPagarDTO> salvarContaAPagar(@RequestBody ContaAPagarDTO contaAPagarDTO) {
        contaAPagarService.salvarContaAPagar(contaAPagarDTO);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/listarcomdados")
    public ResponseEntity<List<ContaAPagarAtualizadaDTO>> listarContasComDados() {
        return ResponseEntity.ok(contaAPagarService.listarContasAPagarCalculadas());
    }


}


