package io.github.CaicoSantos1998.libraryapi.validator;

import io.github.CaicoSantos1998.libraryapi.model.Client;
import io.github.CaicoSantos1998.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidatorClient {

    private final ClientRepository repository;

    public void validate(Client client) {
        if(existsClient(client)) {
            throw new IllegalArgumentException("The Client with this ID already exists!");
        }
    }

    private boolean existsClient(Client client) {
        Optional<Client> clientFounded = repository.findById(client.getId());

        if(client.getId()==null){
            return clientFounded.isPresent();
        }

        if(clientFounded.isEmpty()) {
            return false;
        }

        return !client.getId().equals(clientFounded.get().getId());
    }
}
