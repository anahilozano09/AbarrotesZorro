package mx.unam.aragon.service.Empleado;

import mx.unam.aragon.model.entity.EmpleadoEntity;
import mx.unam.aragon.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService, UserDetailsService {
    @Autowired
    EmpleadoRepository empleadoRepository;
    @Override
    public EmpleadoEntity save(EmpleadoEntity empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<EmpleadoEntity> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        empleadoRepository.deleteById(id);
    }

    @Override
    public EmpleadoEntity findById(Long id) {
        Optional<EmpleadoEntity> op = empleadoRepository.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return empleadoRepository.existsByUsername(username);
    }

    @Override
    public Optional<EmpleadoEntity> findByUsername(String username) {
        return empleadoRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🛠 Buscando usuario: '" + username + "'");

        EmpleadoEntity empleado = empleadoRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.err.println("❌ Usuario '" + username + "' no existe en la BD");
                    return new UsernameNotFoundException("Usuario no encontrado");
                });

        System.out.println("🔑 Usuario encontrado: " + empleado.getUsername());
        System.out.println("🔐 Contraseña almacenada: " + empleado.getPassword());
        System.out.println("🎚️ Roles: " + empleado.getAuthorities());

        return empleado;
    }

}
