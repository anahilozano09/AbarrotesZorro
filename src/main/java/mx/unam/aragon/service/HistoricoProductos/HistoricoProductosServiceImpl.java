package mx.unam.aragon.service.HistoricoProductos;

import mx.unam.aragon.model.entity.HistoricoProductosEntity;
import mx.unam.aragon.repository.HistoricoProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoProductosServiceImpl implements HistoricoProductosService {
    @Autowired
    HistoricoProductosRepository historicoProductosRepository;
    @Override
    public HistoricoProductosEntity save(HistoricoProductosEntity historicoProductos) {
        return historicoProductosRepository.save(historicoProductos);
    }

    @Override
    public List<HistoricoProductosEntity> findAll() {
        return historicoProductosRepository.findAll();
    }
}
