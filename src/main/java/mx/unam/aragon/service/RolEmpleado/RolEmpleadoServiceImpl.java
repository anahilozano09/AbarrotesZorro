package mx.unam.aragon.service.RolEmpleado;

import mx.unam.aragon.model.entity.RolEmpleadoEntity;
import mx.unam.aragon.repository.RolEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolEmpleadoServiceImpl implements RolEmpleadoService {
    @Autowired
    RolEmpleadoRepository rolEmpleadoRepository;
    @Override
    public RolEmpleadoEntity save(RolEmpleadoEntity rolEmpleado) {
        return rolEmpleadoRepository.save(rolEmpleado);
    }

    @Override
    public List<RolEmpleadoEntity> findAll() {
        return rolEmpleadoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        rolEmpleadoRepository.deleteById(id);
    }

    @Override
    public RolEmpleadoEntity findById(Long id) {
        Optional<RolEmpleadoEntity> op = rolEmpleadoRepository.findById(id);
        return op.orElse(null);
    }
}
