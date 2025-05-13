package mx.unam.aragon.service.HistoricoProductos;

import mx.unam.aragon.model.entity.HistoricoProductosEntity;

import java.util.List;

public interface HistoricoProductosService {
    HistoricoProductosEntity save(HistoricoProductosEntity historicoProductos);
    List<HistoricoProductosEntity> findAll();
}
