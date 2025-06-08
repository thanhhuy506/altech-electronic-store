package com.altech.electronic.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altech.electronic.store.model.BaseModel;

public interface BaseService<T extends BaseModel> {
	
	T create(T entity);

    Optional<T> getById(Long id);

    List<T> getAll();

    Page<T> readAll(Pageable pageable);

    T update(Long id, T entity);
    
    void delete(Long id);
    
    Long getCurrentUserId();
}
