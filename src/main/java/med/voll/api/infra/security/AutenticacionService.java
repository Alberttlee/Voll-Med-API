package med.voll.api.infra.security;

import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//interfaz propia de spring que utiliza internamente para efectuar la autenticacion del user
public class AutenticacionService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    @Autowired //inyectar la dependencia
    public AutenticacionService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //de que forma yo voy a cargar ese usuario y de donde
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username); // metodo personalizado para hacer consultas SQL personalizado en spring data
    }
}
