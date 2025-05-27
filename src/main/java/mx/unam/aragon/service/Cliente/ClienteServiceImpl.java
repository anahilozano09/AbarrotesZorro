package mx.unam.aragon.service.Cliente;

import mx.unam.aragon.model.entity.ClienteEntity;

import mx.unam.aragon.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean existsByTelefono(String telefono) {
        return clienteRepository.existsByTelefono(telefono);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNumCuenta(String numCuenta) {
        return clienteRepository.existsByNumCuenta(numCuenta);
    }

    @Override
    public ClienteEntity save(ClienteEntity cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<ClienteEntity> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteEntity findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Optional<ClienteEntity> findByNumCuenta(String numCuenta) {
        return clienteRepository.findByNumCuenta(numCuenta.trim());
    }

}
