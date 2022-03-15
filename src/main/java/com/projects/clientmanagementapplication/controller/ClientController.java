package com.projects.clientmanagementapplication.controller;

import com.projects.clientmanagementapplication.exception.ResourceNotFoundException;
import com.projects.clientmanagementapplication.model.Client;
import com.projects.clientmanagementapplication.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + id + " does not exist"));
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClientById(@PathVariable long id, @RequestBody Client updatedClient){
        Client previousClient= clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + id + " does not exist"));
        previousClient.setFirstName(updatedClient.getFirstName());
        previousClient.setLastName(updatedClient.getLastName());
        previousClient.setEmail(updatedClient.getEmail());
        clientRepository.save(previousClient);
        return ResponseEntity.ok(previousClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClientById(@PathVariable long id) {
        Client client= clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + id + " does not exist"));
        clientRepository.delete(client);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
