package mx.unam.aragon.service.PedidoProveedor;


import mx.unam.aragon.model.entity.PedidoProveedorEntity;
import mx.unam.aragon.repository.PedidoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoProveedorServiceImpl implements PedidoProveedorService {
    @Autowired
    PedidoProveedorRepository pedidoProveedorRepository;
    @Override
    @Transactional
    public PedidoProveedorEntity save(PedidoProveedorEntity pedidoProveedor) {
        return pedidoProveedorRepository.save(pedidoProveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoProveedorEntity> findAll() {
        return pedidoProveedorRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        pedidoProveedorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoProveedorEntity findById(Long id) {
        Optional<PedidoProveedorEntity> op = pedidoProveedorRepository.findById(id);
        return op.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoProveedorEntity findByProductoId(Long productoId) {
        return pedidoProveedorRepository.findByProductoId(productoId);
    }
}
