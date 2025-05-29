package mx.unam.aragon.service.HistoricoProductos;

import mx.unam.aragon.model.entity.HistoricoProductosEntity;
import mx.unam.aragon.repository.HistoricoProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoProductosServiceImpl implements HistoricoProductosService {
    @Autowired
    HistoricoProductosRepository historicoProductosRepository;
    @Override
    @Transactional
    public HistoricoProductosEntity save(HistoricoProductosEntity historicoProductos) {
        return historicoProductosRepository.save(historicoProductos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricoProductosEntity> findAll() {
        return historicoProductosRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        historicoProductosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public HistoricoProductosEntity findById(Long id) {
        Optional<HistoricoProductosEntity> op = historicoProductosRepository.findById(id);
        return op.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricoProductosEntity> findByCantidadProductoAlmacenId(Long cantidadProductoAlmacenId) {
        return historicoProductosRepository.findByCantidadProductoAlmacenId(cantidadProductoAlmacenId);
    }

}
