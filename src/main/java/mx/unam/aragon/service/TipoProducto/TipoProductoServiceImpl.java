package mx.unam.aragon.service.TipoProducto;

import mx.unam.aragon.model.entity.TipoProductoEntity;
import mx.unam.aragon.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {
    @Autowired
    TipoProductoRepository tipoProductoRepository;
    @Override
    public TipoProductoEntity save(TipoProductoEntity tipoProducto) {
        return tipoProductoRepository.save(tipoProducto);
    }

    @Override
    public List<TipoProductoEntity> findAll() {
        return tipoProductoRepository.findAll();
    }
}
