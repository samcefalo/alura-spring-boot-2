package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.Endereco;

public record DadosDetalhamentoMedico(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade,
        String telefone,
        Endereco endereco) {

    public DadosDetalhamentoMedico(@NotNull Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getTelefone(), medico.getEndereco());
    }
}
