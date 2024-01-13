package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ValidadorPacienteAtivo implements ConsultaValidador {

    private final PacienteRepository pacienteRepository;

    public ValidadorPacienteAtivo(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }


    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        boolean isPacienteAtivo = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());

        if(!isPacienteAtivo) {
            throw new ValidacaoException("Paciente não está ativo no sistema");
        }
    }

}
