package com.techShop.tienda.service;

import com.techShop.tienda.domain.Categoria;
import com.techShop.tienda.repository.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {
    
    //El repositorio es final para asegurar la inmutabilidad
    private final CategoriaRepository categoriaRepository;
    
    //Inyeccion por constructor (No requiere @Autowired en Spring moderno)
    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;   
    }
    
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activo){
        if(activo){ //Sólo activos...
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();
    }
}
