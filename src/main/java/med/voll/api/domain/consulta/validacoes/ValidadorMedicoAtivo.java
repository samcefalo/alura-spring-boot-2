package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Component
public class ValidadorMedicoAtivo implements ConsultaValidador {

    private final MedicoRepository medicoRepository;

    public ValidadorMedicoAtivo(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if(isNull(dadosAgendamentoConsulta.idMedico())) {
            return;
        }

        boolean isMedicoAtivo = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());

        if(!isMedicoAtivo) {
            throw new ValidacaoException("Médico não está ativo no sistema");
        }
    }

}
