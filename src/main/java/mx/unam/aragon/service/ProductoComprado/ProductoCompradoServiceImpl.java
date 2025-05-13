package mx.unam.aragon.service.ProductoComprado;

import mx.unam.aragon.model.entity.ProductoCompradoEntity;
import mx.unam.aragon.repository.ProductoCompradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoCompradoServiceImpl implements ProductoCompradoService {
    @Autowired
    ProductoCompradoRepository productoCompradoRepository;
    @Override
    public ProductoCompradoEntity save(ProductoCompradoEntity productoComprado) {
        return productoCompradoRepository.save(productoComprado);
    }

    @Override
    public List<ProductoCompradoEntity> findAll() {
        return productoCompradoRepository.findAll();
    }
}
