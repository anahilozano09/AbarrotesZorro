package mx.unam.aragon.service.CompraCliente;

import mx.unam.aragon.model.entity.CompraClienteEntity;
import mx.unam.aragon.repository.CompraClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompraClienteServiceImpl implements CompraClienteService {
    @Autowired
    CompraClienteRepository compraClienteRepository;
    @Override
    @Transactional
    public CompraClienteEntity save(CompraClienteEntity compraCliente) {
        return compraClienteRepository.save(compraCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompraClienteEntity> findAll() {
        return compraClienteRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        compraClienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CompraClienteEntity findById(Long id) {
        Optional<CompraClienteEntity> op = compraClienteRepository.findById(id);
        return op.orElse(null);
    }
}
