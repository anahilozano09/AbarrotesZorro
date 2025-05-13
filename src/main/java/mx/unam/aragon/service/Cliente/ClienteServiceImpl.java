package mx.unam.aragon.service.Cliente;

import mx.unam.aragon.model.entity.ClienteEntity;
import mx.unam.aragon.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Override
    public ClienteEntity save(ClienteEntity cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<ClienteEntity> findAll() {
        return clienteRepository.findAll();
    }
}
