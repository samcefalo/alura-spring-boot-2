package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.especialidade = :especialidade
            AND m.ativo = true
            and m.id not in (
                select c.medico.id from Consulta c
                where c.data = :dateTime
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Medico findMedicoByEspecialidadeAndSpare(Especialidade especialidade, LocalDateTime dateTime);

    @Query("""
            SELECT m.ativo FROM Medico m
            WHERE m.id = :id
            """)
    Boolean findAtivoById(Long id);

}
