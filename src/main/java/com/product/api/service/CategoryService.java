package com.product.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;

public interface CategoryService {

    /**
     * Obtiene todas las categorías registradas
     * 
     * @return arreglo de las categorías
     */
    public ResponseEntity<List<Category>> findAll();

    /**
     * Obtiene las categorías activas
     * 
     * @return arreglo con categorías activas
     */
    public ResponseEntity<List<Category>> findActive();

    /**
     * Obtiene las categorías hijas a partir del id
     * 
     * @param id
     */
    public ResponseEntity<List<Category>> findChilds(Integer id);

    /**
     * Crea una categoría nueva
     * @param dto
     */
    public void create(DtoCategoryIn dto);

    /**
     * Actualiza una categoría via id
     * @param dto
     * @param id
     */
    public void update(DtoCategoryIn dto, Integer id);

    /**
     * Activa de nuevo una categoría desactivada
     * @param id
     */
    public void enable(Integer id);

    /**
     * Desactiva una categoría sólo si se encuentra activa
     * @param id
     */
    public void disable(Integer id);

}
