package com.rubensrangel.BackendApp.resources;

import com.rubensrangel.BackendApp.domain.Cliente;
import com.rubensrangel.BackendApp.dto.ClienteDTO;
import com.rubensrangel.BackendApp.dto.ClienteNewDTO;
import com.rubensrangel.BackendApp.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {


    @Autowired
    private ClienteService clienteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente obj = clienteService.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/email", method=RequestMethod.GET)
    public ResponseEntity<Cliente> find(@RequestParam(value="value") String email) {
        Cliente obj = clienteService.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> clienteList = clienteService.findAll();
        List<ClienteDTO> clienteDTOList = clienteList.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(clienteDTOList);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                     @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                     @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Cliente> clienteList = clienteService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> clienteDTOListt = clienteList.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(clienteDTOListt);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO) {
        Cliente obj = clienteService.fromNewDTO(objDTO);
        obj = clienteService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
        Cliente obj = clienteService.fromDTO(objDto);
        obj.setId(id);
        obj = clienteService.update(obj);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseEntity<Void> uploadProfilePicture(@RequestBody MultipartFile file) {
        URI uri = clienteService.uploadProfilePicture(file);
        return ResponseEntity.created(uri).build();
    }
}
