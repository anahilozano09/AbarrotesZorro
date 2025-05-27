package mx.unam.aragon.config;

import mx.unam.aragon.model.entity.EmpleadoEntity;
import mx.unam.aragon.model.entity.RolEmpleadoEntity;
import mx.unam.aragon.service.Empleado.EmpleadoService;
import mx.unam.aragon.service.RolEmpleado.RolEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login",
                                "/css/**", "/js/**", "/image/**",  "/bootstrap/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_Administrador")
                        .requestMatchers("/caja/**").hasAuthority("ROLE_Cajero")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/admin", true)  // Redirige aquí por defecto
                        .successHandler((request, response, authentication) -> {
                            if (authentication.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_Administrador"))) {
                                response.sendRedirect("/admin");
                            } else if (authentication.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_Cajero"))) {
                                response.sendRedirect("/cajero");
                            }
                        })
                        .failureUrl("/?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/denegado")
                )
                .build();
    }

    @Bean
    CommandLineRunner initUsers(EmpleadoService empleadoService,
                                RolEmpleadoService rolEmpleadoService,
                                PasswordEncoder encoder) {
        return args -> {
            RolEmpleadoEntity rolAdmin = rolEmpleadoService.findByRol("Administrador")
                    .orElseThrow(() -> new IllegalStateException("Rol Administrador no encontrado"));

            RolEmpleadoEntity rolCajero = rolEmpleadoService.findByRol("Cajero")
                    .orElseThrow(() -> new IllegalStateException("Rol Cajero no encontrado"));

            if (!empleadoService.existsByUsername("admin")) {
                empleadoService.save(
                        new EmpleadoEntity(null, "Admin Principal", "admin",
                                encoder.encode("Admin123"),
                                "anatrikilozano@gmail.com","5580104964", rolAdmin)
                );
            }

            if (!empleadoService.existsByUsername("cajero")) {
                empleadoService.save(
                        new EmpleadoEntity(null, "Cajero Principal", "cajero",
                                encoder.encode("Cajero123"),
                                "diego23arreola@gmail.com","5577540590", rolCajero)
                );
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

