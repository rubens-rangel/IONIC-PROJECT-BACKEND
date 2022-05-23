package com.rubensrangel.BackendApp.domain;

import com.rubensrangel.BackendApp.domain.enums.EstadoPagamento;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

   private Integer numeroDeParcelas;


    public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedio, Integer numeroDeParcelas) {
        super(id, estado, pedio);
        this.numeroDeParcelas = numeroDeParcelas;
    }
}