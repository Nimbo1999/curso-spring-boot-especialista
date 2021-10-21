package io.github.nimbo1999.domain.repository;

import io.github.nimbo1999.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto,Integer> {
}
