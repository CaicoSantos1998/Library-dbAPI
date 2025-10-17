package io.github.CaicoSantos1998.libraryapi.service;

import io.github.CaicoSantos1998.libraryapi.model.Client;
import io.github.CaicoSantos1998.libraryapi.repository.ClientRepository;
import io.github.CaicoSantos1998.libraryapi.validator.ValidatorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ValidatorClient validator;
    private final PasswordEncoder encoder;

    public Client save(Client client) {
        validator.validate(client);
        String passwordEncrypted = encoder.encode(client.getClientSecret());
        client.setClientSecret(passwordEncrypted);
        return repository.save(client);
    }

    public Client getByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }
}
