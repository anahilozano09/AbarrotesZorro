package mx.unam.aragon.service.Producto;

import mx.unam.aragon.model.entity.ProductoEntity;
import mx.unam.aragon.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    ProductoRepository productoRepository;
    @Override
    public ProductoEntity save(ProductoEntity producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<ProductoEntity> findAll() {
        return productoRepository.findAll();
    }
}
