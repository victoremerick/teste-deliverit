package tech.homedeveloper.testedeliverit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.homedeveloper.testedeliverit.database.model.RegraCobranca;
import tech.homedeveloper.testedeliverit.database.repository.RegraCobrancaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RegraCobrancaService {

    private final RegraCobrancaRepository regraCobrancaRepository;

    @Autowired
    public RegraCobrancaService(RegraCobrancaRepository regraCobrancaRepository) {
        this.regraCobrancaRepository = regraCobrancaRepository;
    }

    public RegraCobranca salvarRegraCobranca(RegraCobranca regraCobranca) {
        return regraCobrancaRepository.save(regraCobranca);
    }

    public List<RegraCobranca> listarRegrasCobranca() {
        return regraCobrancaRepository.findAll();
    }

    public RegraCobranca getRegraCobranca(Long id) {
        Optional<RegraCobranca> regraCobrancaOptional = regraCobrancaRepository.findById(id);
        return regraCobrancaOptional.orElse(null);
    }

    public RegraCobranca atualizarRegraCobranca(Long id, RegraCobranca regraCobranca) {
        if (regraCobrancaRepository.existsById(id)) {
            regraCobranca.setId(id);
            return regraCobrancaRepository.save(regraCobranca);
        }
        return null;
    }

    public void excluirRegraCobranca(Long id) {
        regraCobrancaRepository.deleteById(id);
    }
}

