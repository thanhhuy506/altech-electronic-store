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

    @Override
    @Transactional
    public T create(T entity) {
        return getRepository().save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> getById(Long id) {
        return getRepository().findByIdAndIsDeletedFalse(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return getRepository().findAllByIsDeletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> readAll(Pageable pageable) {
        return (Page<T>) getRepository().findAllByIsDeletedFalse(pageable);
    }

    @Override
    @Transactional
    public T update(Long id, T entity) {
        if (getRepository().existsByIdAndIsDeletedFalse(id)) {
            entity.setId(id);
            return getRepository().save(entity);
        }
        return null;
    }
    
    @Override
    public void delete(Long id) {
    	T entity = getRepository().findById(id).orElseThrow();
        entity.setIsDeleted(true);
        getRepository().save(entity);
    }

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