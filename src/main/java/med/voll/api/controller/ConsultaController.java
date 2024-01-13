package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static med.voll.api.util.AplicationConstants.API_CONSULTAS_PATH;

@RestController
@RequestMapping(API_CONSULTAS_PATH)
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private final AgendaConsultaService agenda;

    public ConsultaController(AgendaConsultaService agenda) {
        this.agenda = agenda;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@Valid @RequestBody DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        return ResponseEntity.ok(agenda.agendar(dadosAgendamentoConsulta));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
