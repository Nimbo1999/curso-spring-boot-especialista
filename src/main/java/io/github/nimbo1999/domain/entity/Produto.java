package io.github.nimbo1999.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    @NotEmpty(message = "A descrição do produto é obrigatória!")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "É obrigatório informar o preço do produto!")
    private BigDecimal preco;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}
