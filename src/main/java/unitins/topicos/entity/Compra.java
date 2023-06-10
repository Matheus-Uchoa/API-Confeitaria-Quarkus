package unitins.topicos.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Compra extends DefaultEntity {
    
    private double totalCompra = 0;

    @OneToMany
    @JoinColumn(name = "id_compra")
    private List<ItemCompra> listaDeItens;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private EnderecoEntity endereco;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @OneToOne
    @JoinColumn(name = "id_pagamento", unique = true)
    private MetodoPagamento pagamento;

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public List<ItemCompra> getListaDeItens() {
        return listaDeItens;
    }

    public void setListaDeItens(List<ItemCompra> listaDeItens) {
        this.listaDeItens = listaDeItens;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public MetodoPagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(MetodoPagamento pagamento) {
        this.pagamento = pagamento;
    }

    
}