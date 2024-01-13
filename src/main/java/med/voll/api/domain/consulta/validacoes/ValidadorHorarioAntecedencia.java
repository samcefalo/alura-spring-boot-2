package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ValidadorHorarioAntecedencia implements ConsultaValidador {

    private static final Integer ANTECEDENCIA_VALUE = 30;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        LocalDateTime dataConsulta = dadosAgendamentoConsulta.data();
        LocalDateTime dataAtual = LocalDateTime.now();

        long diff = Duration.between(dataAtual, dataConsulta).toMinutes();

        if (diff < ANTECEDENCIA_VALUE) {
            throw new ValidacaoException("A consulta deve ser agendada com no mínimo " + ANTECEDENCIA_VALUE + " minutos de antecedência");
        }

    }

}
