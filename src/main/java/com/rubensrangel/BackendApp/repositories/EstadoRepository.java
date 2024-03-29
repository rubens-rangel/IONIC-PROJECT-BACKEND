package com.rubensrangel.BackendApp.repositories;

import com.rubensrangel.BackendApp.domain.Estado;
import com.rubensrangel.BackendApp.dto.EstadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Transactional(readOnly=true)
    public List<Estado> findAllByOrderByNome();
}
