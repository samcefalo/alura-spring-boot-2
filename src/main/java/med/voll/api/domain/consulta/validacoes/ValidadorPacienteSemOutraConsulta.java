package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorPacienteSemOutraConsulta implements ConsultaValidador {

    private static final Integer HORA_INICIAL = 7;
    private static final Integer HORA_FINAL = 19;

    private final ConsultaRepository consultaRepository;

    public ValidadorPacienteSemOutraConsulta(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        LocalDateTime dataInicial = dadosAgendamentoConsulta.data().withHour(HORA_INICIAL);
        LocalDateTime dataFinal = dadosAgendamentoConsulta.data().withHour(HORA_FINAL);

        boolean pacienteTemConsulta = consultaRepository
                .existsByPacienteIdAndDataBetween(
                        dadosAgendamentoConsulta.idPaciente(),
                        dataInicial,
                        dataFinal
                );

        if (pacienteTemConsulta) {
            throw new ValidacaoException("Paciente já possui consulta agendada para o dia informado.");
        }

    }

}
