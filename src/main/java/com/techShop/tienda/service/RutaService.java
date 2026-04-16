package com.techShop.tienda.service;

import com.techShop.tienda.domain.Ruta;
import com.techShop.tienda.repository.RutaRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    @Transactional(readOnly = true)
    public List<Ruta> getRutas() {
        return rutaRepository.findAllByOrderByRequiereRolAsc();
    }

    @Transactional
    public void save(Ruta ruta) {
        rutaRepository.save(ruta);
    }

    @Transactional(readOnly = true)
    public Ruta getRuta(Integer idRuta) {
        return rutaRepository.findById(idRuta)
                .orElseThrow(() -> new NoSuchElementException("Ruta no encontrada"));
    }

    @Transactional
    public void delete(Integer idRuta) {
        if (!rutaRepository.existsById(idRuta)) {
            throw new IllegalArgumentException("Ruta no existe");
        }
        rutaRepository.deleteById(idRuta);
    }
}