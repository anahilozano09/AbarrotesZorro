package mx.unam.aragon.service.CantidadProductoAlmacen;

import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import mx.unam.aragon.repository.CantidadProductoAlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
