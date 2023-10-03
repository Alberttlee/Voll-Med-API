package med.voll.api.domain.consulta;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //instanciada y gerenciada con los componentes de Spring Framework
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {



}
