package mx.unam.aragon.service.CantidadProductoAlmacen;

import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import mx.unam.aragon.model.entity.HistoricoProductosEntity;
import mx.unam.aragon.repository.CantidadProductoAlmacenRepository;
import mx.unam.aragon.service.HistoricoProductos.HistoricoProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CantidadProductoAlmacenServiceImpl implements CantidadProductoAlmacenService {

    @Autowired
    CantidadProductoAlmacenRepository cantidadProductoAlmacenRepository;

    @Autowired
    HistoricoProductosService historicoProductosService;

    @Override
    @Transactional
    public CantidadProductoAlmacenEntity save(CantidadProductoAlmacenEntity cantidadProductoAlmacen) {
        return cantidadProductoAlmacenRepository.save(cantidadProductoAlmacen);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CantidadProductoAlmacenEntity> findAll() {
        return cantidadProductoAlmacenRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cantidadProductoAlmacenRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CantidadProductoAlmacenEntity findById(Long id) {
        Optional<CantidadProductoAlmacenEntity> op = cantidadProductoAlmacenRepository.findById(id);
        return op.orElse(null);

    }

    @Override
    @Transactional(readOnly = true)
    public List<CantidadProductoAlmacenEntity> findByTipoProducto(Long idTipoProducto) {
        return cantidadProductoAlmacenRepository.findByTipoProducto(idTipoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public CantidadProductoAlmacenEntity findByProductoId(Long productoId) {
        return cantidadProductoAlmacenRepository.findByProductoId(productoId);
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Override
    public void actualizarCantidadProductoAlmacen() {
        LocalDate ayer = LocalDate.now().minusDays(1);

        List<HistoricoProductosEntity> listaHistorico = historicoProductosService.findAll()
                .stream().filter(h -> {
                    LocalDate fechaCompra = h.getCompraCliente().getFecha().toLocalDate();
                    return fechaCompra.equals(ayer);
                })
                .toList();

        for(HistoricoProductosEntity historico : listaHistorico){
            CantidadProductoAlmacenEntity cantidadProductoAlmacen = historico.getCantidadProductoAlmacen();
            cantidadProductoAlmacen.setCantidad(historico.getCantidadAct());
            cantidadProductoAlmacenRepository.save(cantidadProductoAlmacen);
        }

    }


}
