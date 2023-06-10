package unitins.topicos.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MetodoPagamento extends DefaultEntity {

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Boolean statusPagamento;

    private LocalDate dataConfirmacaoPagamento;

    public MetodoPagamento(Double valor) {

        this.valor = valor;
        this.statusPagamento = true;
        this.dataConfirmacaoPagamento = LocalDate.now();
    }

    public MetodoPagamento() {

        this.statusPagamento = false;
    }

    public Boolean getConfirmacaoPagamento() {
        return statusPagamento;
    }

    public void setConfirmacaoPagamento(Boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataConfirmacaoPagamento() {
        return dataConfirmacaoPagamento;
    }

    public void setDataConfirmacaoPagamento(LocalDate dataConfirmacaoPagamento) {
        this.dataConfirmacaoPagamento = dataConfirmacaoPagamento;
    }
  }