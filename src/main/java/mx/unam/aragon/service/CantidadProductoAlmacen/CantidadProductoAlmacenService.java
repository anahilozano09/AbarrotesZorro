package mx.unam.aragon.service.CantidadProductoAlmacen;

import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;

import java.util.List;

public interface CantidadProductoAlmacenService {
    CantidadProductoAlmacenEntity save(CantidadProductoAlmacenEntity cantidadProductoAlmacen);
    List<CantidadProductoAlmacenEntity> findAll();
    void deleteById(Long id);
    CantidadProductoAlmacenEntity findById(Long id);
}


