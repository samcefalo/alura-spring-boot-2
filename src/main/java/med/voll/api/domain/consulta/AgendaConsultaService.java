package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validacoes.ConsultaValidador;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class AgendaConsultaService {
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ConsultaValidador> validadores;

    public AgendaConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, List<ConsultaValidador> validadores) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        Paciente paciente = pacienteRepository.getReferenceById(dadosAgendamentoConsulta.idPaciente());

        if (isNull(paciente)) {
            throw new ValidacaoException("Paciente não encontrado");
        }

        Medico medico = getMedico(dadosAgendamentoConsulta);

        if(isNull(medico)) {
            throw new ValidacaoException("Médico não encontrado");
        }

        validadores.forEach(validador -> validador.validar(dadosAgendamentoConsulta));

        Consulta consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.data(), null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico getMedico(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (nonNull(dadosAgendamentoConsulta.idMedico())) {
            return medicoRepository.getReferenceById(dadosAgendamentoConsulta.idMedico());
        }

        if (isNull(dadosAgendamentoConsulta.especialidade())) {
            throw new ValidacaoException("Especialidade não informada");
        }

        return medicoRepository.findMedicoByEspecialidadeAndSpare(dadosAgendamentoConsulta.especialidade(), dadosAgendamentoConsulta.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

}
