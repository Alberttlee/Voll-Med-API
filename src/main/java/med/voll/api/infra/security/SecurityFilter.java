package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Estereotipo mas generico de Spring para definir un component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtener el token del header que por estandar se llama Authorization
        var authHeader = request.getHeader("Authorization");//.replace("Bearer ", ""); //retorna header de authorization y el bearer por vacio, solo retorna token
        if (authHeader != null){
            var token = authHeader.replace("Bearer ", ""); //retorna header de authorization y el bearer por vacio, solo retorna token
            var nombreUsuario= tokenService.getSubject(token);
            if (nombreUsuario != null){
                //toquen valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());//forzamos inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response); //filtro ejecuta esto y mandale el request y response que esta llegando del html
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        var tokenJWT = recuperarToken(request);
//
//        if (tokenJWT != null) {
//            var subject = tokenService.getSubject(tokenJWT);
//            var usuario = usuarioRepository.findByLogin(subject);
//
//            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String recuperarToken(HttpServletRequest request) {
//        var authorizationHeader = request.getHeader("Authorization");
//        if (authorizationHeader != null) {
//            return authorizationHeader.replace("Bearer ", "");
//        }
//
//        return null;
//    }



}
