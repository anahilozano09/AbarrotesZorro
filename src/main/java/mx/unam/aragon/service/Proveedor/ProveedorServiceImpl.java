package mx.unam.aragon.service.Proveedor;

import mx.unam.aragon.model.entity.ProveedorEntity;
import mx.unam.aragon.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;
    @Override
    public ProveedorEntity save(ProveedorEntity proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public List<ProveedorEntity> findAll() {
        return proveedorRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }

    @Override
    public ProveedorEntity findById(Long id) {
        Optional<ProveedorEntity> op = proveedorRepository.findById(id);
        return op.orElse(null);
    }
}
