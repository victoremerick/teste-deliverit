package tech.homedeveloper.testedeliverit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.homedeveloper.testedeliverit.database.model.RegraCobranca;
import tech.homedeveloper.testedeliverit.service.RegraCobrancaService;

import java.util.List;

@RestController
@RequestMapping("/regracobranca")
public class RegraCobrancaController {

    private final RegraCobrancaService regraCobrancaService;

    @Autowired
    public RegraCobrancaController(RegraCobrancaService regraCobrancaService) {
        this.regraCobrancaService = regraCobrancaService;
    }

    @PostMapping
    public RegraCobranca salvarRegraCobranca(@RequestBody RegraCobranca regraCobranca) {
        return regraCobrancaService.salvarRegraCobranca(regraCobranca);
    }

    @GetMapping
    public List<RegraCobranca> listarRegrasCobranca() {
        return regraCobrancaService.listarRegrasCobranca();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegraCobranca> getRegraCobranca(@PathVariable Long id) {
        var regraCobranca = regraCobrancaService.getRegraCobranca(id);
        if(regraCobranca == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(regraCobranca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegraCobranca> atualizarRegraCobranca(@PathVariable Long id, @RequestBody RegraCobranca regraCobranca) {
        if(regraCobranca.getId() == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(regraCobrancaService.atualizarRegraCobranca(id, regraCobranca));
    }

    @DeleteMapping("/{id}")
    public void excluirRegraCobranca(@PathVariable Long id) {
        regraCobrancaService.excluirRegraCobranca(id);
    }
}

