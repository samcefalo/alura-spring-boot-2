package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ConsultaValidador {

    private static final Integer HORA_ABERTURA = 7;
    private static final Integer HORA_FECHAMENTO = 19;
    private static final List<DayOfWeek> DIAS_FUNCIONAMENTO = List.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
    );

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        LocalDateTime dataConsulta = dadosAgendamentoConsulta.data();

        boolean isValidDay = DIAS_FUNCIONAMENTO.contains(dataConsulta.getDayOfWeek());
        boolean isBeforeOpen = dataConsulta.getHour() < HORA_ABERTURA;
        boolean isAfterClose = dataConsulta.getHour() > HORA_FECHAMENTO;

        if (!isValidDay) {
            throw new ValidacaoException("A clínica não funciona aos domingos");
        }

        if (isValidDay && (isBeforeOpen || isAfterClose)) {
            throw new ValidacaoException("Horário de funcionamento da clínica: " + HORA_ABERTURA + "h às " + HORA_FECHAMENTO + "h");
        }
    }

}
