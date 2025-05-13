package mx.unam.aragon.service.RolEmpleado;

import mx.unam.aragon.model.entity.RolEmpleadoEntity;
import mx.unam.aragon.repository.RolEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
