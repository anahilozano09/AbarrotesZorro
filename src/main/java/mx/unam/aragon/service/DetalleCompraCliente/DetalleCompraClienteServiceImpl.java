package mx.unam.aragon.service.DetalleCompraCliente;

import mx.unam.aragon.model.entity.DetalleCompraClienteEntity;
import mx.unam.aragon.model.entity.HistoricoProductosEntity;
import mx.unam.aragon.repository.DetalleCompraClienteRepository;
import mx.unam.aragon.repository.HistoricoProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleCompraClienteServiceImpl implements DetalleCompraClienteService {
    @Autowired
    DetalleCompraClienteRepository detalleCompraClienteRepository;
    @Override
    @Transactional
    public DetalleCompraClienteEntity save(DetalleCompraClienteEntity detalleCompraCliente) {
        return detalleCompraClienteRepository.save(detalleCompraCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleCompraClienteEntity> findAll() {
        return detalleCompraClienteRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        detalleCompraClienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleCompraClienteEntity findById(Long id) {
        Optional<DetalleCompraClienteEntity> op = detalleCompraClienteRepository.findById(id);
        return op.orElse(null);
    }
}
