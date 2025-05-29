package mx.unam.aragon.service.Cliente;

import mx.unam.aragon.model.entity.ClienteEntity;

import mx.unam.aragon.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ClienteEntity save(ClienteEntity cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteEntity> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteEntity findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Optional<ClienteEntity> findByNumCuenta(String numCuenta) {
        return clienteRepository.findByNumCuenta(numCuenta.trim());
    }

    @Transactional(readOnly = true)
    public Optional<ClienteEntity> findByEmail(String email) {
        return clienteRepository.findByEmail(email.trim());
    }

    @Transactional(readOnly = true)
    public Optional<ClienteEntity> findByTelefono(String telefono) {
        return clienteRepository.findByTelefono(telefono.trim());
    }

}
