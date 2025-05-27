package mx.unam.aragon.service.CantidadProductoAlmacen;

import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import mx.unam.aragon.repository.CantidadProductoAlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CantidadProductoAlmacenServiceImpl implements CantidadProductoAlmacenService {

    @Autowired

    CantidadProductoAlmacenRepository cantidadProductoAlmacenRepository;
    @Override
    public CantidadProductoAlmacenEntity save(CantidadProductoAlmacenEntity cantidadProductoAlmacen) {
        return cantidadProductoAlmacenRepository.save(cantidadProductoAlmacen);
    }

    @Override
    public List<CantidadProductoAlmacenEntity> findAll() {
        return cantidadProductoAlmacenRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        cantidadProductoAlmacenRepository.deleteById(id);
    }

    @Override
    public CantidadProductoAlmacenEntity findById(Long id) {
        Optional<CantidadProductoAlmacenEntity> op = cantidadProductoAlmacenRepository.findById(id);
        return op.orElse(null);

    }

    @Override
    public List<CantidadProductoAlmacenEntity> findByTipoProducto(Long idTipoProducto) {
        return cantidadProductoAlmacenRepository.findByTipoProducto(idTipoProducto);
    }
}
