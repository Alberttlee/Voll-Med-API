package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> { //tipo de dato a manejar y la llave primaria

    Page<Medico> findByActivoTrue(Pageable paginacion);//metodo personalizado para hacer consultas SQL personalizado de spring data

    @Query("""
            select m from Medico m
            where m.activo=1 and
            m.especialidad=:especialidad and
            m.id not in(
            select c.medico.id from Consulta c
            c.data=:fecha
            )
            order by rand()
            limit 1 
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}