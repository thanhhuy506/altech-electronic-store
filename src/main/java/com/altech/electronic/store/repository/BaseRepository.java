package com.altech.electronic.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.altech.electronic.store.model.BaseModel;

@NoRepositoryBean
public interface BaseRepository<T extends BaseModel> extends JpaRepository<T, Long> {
    
    Optional<T> findByIdAndIsDeletedFalse(Long id);
    
    List<T> findAllByIsDeletedFalse();
    
    List<T> findAllByIsDeletedFalse(Pageable pageable);
    
    default void softDelete(T entity) {
        entity.setIsDeleted(true);
        save(entity);
    }
    
    default void softDeleteById(Long id) {
        findById(id).ifPresent(this::softDelete);
    }
    
    boolean existsByIdAndIsDeletedFalse(Long id);
}