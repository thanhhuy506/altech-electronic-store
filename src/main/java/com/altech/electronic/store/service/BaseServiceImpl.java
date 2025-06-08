package com.altech.electronic.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altech.electronic.store.model.BaseModel;
import com.altech.electronic.store.repository.BaseRepository;
import com.altech.electronic.store.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    @Autowired
    JwtService jwtService;

    protected abstract BaseRepository<T> getRepository();

    /**
     * Create a new entity.
     *
     * @param entity
     * @return T
     */
    @Override
    @Transactional
    public T create(T entity) {
        return getRepository().save(entity);
    }

    /**
     * Get entity by id.
     *
     * @param id
     * @return Optional<T>
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<T> getById(Long id) {
        return getRepository().findByIdAndIsDeletedFalse(id);
    }

    /**
     * Get all entities.
     *
     * @return List<T>
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return getRepository().findAllByIsDeletedFalse();
    }

    /**
     * Get all entities with pagination.
     *
     * @param pageable
     * @return Page<T>
     */
    @Override
    @Transactional(readOnly = true)
    public Page<T> readAll(Pageable pageable) {
        return (Page<T>) getRepository().findAllByIsDeletedFalse(pageable);
    }

    /**
     * Update entity by id.
     *
     * @param id
     * @param entity
     * @return T
     */
    @Override
    @Transactional
    public T update(Long id, T entity) {
        if (getRepository().existsByIdAndIsDeletedFalse(id)) {
            entity.setId(id);
            return getRepository().save(entity);
        }
        return null;
    }

    /**
     * Delete entity by id.
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        T entity = getRepository().findById(id).orElseThrow();
        entity.setIsDeleted(true);
        getRepository().save(entity);
    }

    /**
     * Get the current user from the security context.
     *
     * @return UserDetails
     */
    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        Object credentials = authentication.getCredentials();
        if (credentials instanceof String jwtToken) {
            return jwtService.extractUserId(jwtToken);
        }
        return null;
    }
}