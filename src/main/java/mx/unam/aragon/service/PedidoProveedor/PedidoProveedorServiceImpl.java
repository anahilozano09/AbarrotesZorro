package mx.unam.aragon.service.PedidoProveedor;


import mx.unam.aragon.model.entity.PedidoProveedorEntity;
import mx.unam.aragon.repository.PedidoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoProveedorServiceImpl implements PedidoProveedorService {
    @Autowired
    PedidoProveedorRepository pedidoProveedorRepository;
    @Override
    public PedidoProveedorEntity save(PedidoProveedorEntity pedidoProveedor) {
        return pedidoProveedorRepository.save(pedidoProveedor);
    }

    @Override
    public List<PedidoProveedorEntity> findAll() {
        return pedidoProveedorRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        pedidoProveedorRepository.deleteById(id);
    }

    @Override
    public PedidoProveedorEntity findById(Long id) {
        Optional<PedidoProveedorEntity> op = pedidoProveedorRepository.findById(id);
        return op.orElse(null);
    }
}
