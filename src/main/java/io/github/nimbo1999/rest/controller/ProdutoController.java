package io.github.nimbo1999.rest.controller;

import io.github.nimbo1999.domain.entity.Produto;
import io.github.nimbo1999.domain.repository.Produtos;
import io.github.nimbo1999.utils.InstantUtils;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos repository;

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody @Valid Produto produto) {
        Instant now = InstantUtils.instantNow();
        produto.setCreatedAt(now);
        produto.setUpdatedAt(now);
        return repository.save(produto);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
        repository
                .findById(id)
                .map( p -> {
                   produto.setId(p.getId());
                   produto.setUpdatedAt(InstantUtils.instantNow());
                   return repository.save(produto);
                }).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repository
                .findById(id)
                .map( p -> {
                    repository.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado."));
    }

    @GetMapping("{id}")
    public Produto getById(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado."));
    }

    @GetMapping
    public List<Produto> find(Produto filtro ) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example<Produto> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }
}
