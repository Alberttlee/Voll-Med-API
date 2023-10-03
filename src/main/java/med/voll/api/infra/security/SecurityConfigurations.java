package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Archivo de configuracion
@EnableWebSecurity//Habilita web security, Para decir que los métodos sobreescriban el comportamiento de actenticacion que queremos
public class SecurityConfigurations { //clase con la configuración de seguridad de la API: sobreesciribir la configuracionpor defecto de Spring


    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //politica de creacion es state less
//            .and().authorizeHttpRequests() //cada request que haga match tipo post y va para login, permite todddo
//            .requestMatchers(HttpMethod.POST, "/login").permitAll()
//            .anyRequest().authenticated()//toddddos los request tienen que ser autenticados
//            .and().build();//como va a ser configuracion  via token no tengo que tener proteccion CSRF


//        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(
//                        httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build(); //le indicamos a sprign el tipo de sesion


        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers(
                                                HttpMethod.POST,"/login").permitAll().anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //agregar un filtro antes que el de spring valida al usuario si existe y esta autenticado
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
