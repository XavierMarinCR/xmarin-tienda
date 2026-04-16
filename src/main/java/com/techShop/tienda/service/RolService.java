package com.techShop.tienda.service;

import com.techShop.tienda.domain.Rol;
import com.techShop.tienda.repository.RolRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public List<Rol> getRoles() {
        var lista = rolRepository.findAll();
        return lista;
    }

    @Transactional(readOnly = true)
    public Rol getRol(Integer idRol) {
        return rolRepository.findById(idRol).orElseThrow(
            () -> new NoSuchElementException("Rol con ID " + idRol+ " no encontrado."));
    }

    @Transactional
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

    @Transactional
    public void delete(Integer idRol) {
        // Verifica si el rol existe antes de intentar eliminarlo
        if (!rolRepository.existsById(idRol)) {
            // Lanza una excepción para indicar que el rol no fue encontrado
            throw new IllegalArgumentException("El Rol con ID " + idRol + " no existe.");
        }
        try {
            rolRepository.deleteById(idRol);
        } catch (DataIntegrityViolationException e) {
            // Lanza una nueva excepción para encapsular el problema de integridad de datos
            throw new IllegalStateException("No se puede eliminar el rol. Tiene datos asociados.", e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Rol> findByNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }
}
