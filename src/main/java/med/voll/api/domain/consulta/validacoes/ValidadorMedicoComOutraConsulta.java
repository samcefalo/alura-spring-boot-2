package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsulta implements ConsultaValidador {

    private final ConsultaRepository consultaRepository;

    public ValidadorMedicoComOutraConsulta(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        boolean medicoTemOutraConsulta = consultaRepository
                .existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(),
                        dadosAgendamentoConsulta.data());

        if (medicoTemOutraConsulta) {
            throw new ValidacaoException("Médico tem outra consulta agendada neste horário");
        }
    }

}
