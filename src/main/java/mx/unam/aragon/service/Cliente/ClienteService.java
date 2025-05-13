package mx.unam.aragon.service.Cliente;

import mx.unam.aragon.model.entity.ClienteEntity;

import java.util.List;

public interface ClienteService {
    ClienteEntity save(ClienteEntity cliente);
    List<ClienteEntity> findAll();
}
