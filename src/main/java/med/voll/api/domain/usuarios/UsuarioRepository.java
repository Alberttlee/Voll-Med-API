package med.voll.api.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //busca un usario de tipo UserDetails
    UserDetails findByLogin(String login); //metodo personalizado para hacer consultas SQL personalizado de spring data
}
