package mx.unam.aragon.service.CompraCliente;

import mx.unam.aragon.model.entity.CompraClienteEntity;
import mx.unam.aragon.repository.CompraClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraClienteServiceImpl implements CompraClienteService {
    @Autowired
    CompraClienteRepository compraClienteRepository;
    @Override
    public CompraClienteEntity save(CompraClienteEntity compraCliente) {
        return compraClienteRepository.save(compraCliente);
    }

    @Override
    public List<CompraClienteEntity> findAll() {
        return compraClienteRepository.findAll();
    }
}
