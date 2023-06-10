package unitins.topicos.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class PagamentoCartaoCredito extends MetodoPagamento {

    @Column(nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String titular;

  @Column(nullable = false)
  private Date dataValidade;

    private BandeiraCartao bandeiraCartao;

    public PagamentoCartaoCredito(Double valor, String numeroCartao, String titular, BandeiraCartao bandeiraCartao, Date dataValidade) {

        super(valor);

        this.numeroCartao = numeroCartao;
        this.titular = titular;
        
        this.bandeiraCartao = bandeiraCartao;
        this.dataValidade = dataValidade;
    }

    public PagamentoCartaoCredito() {

    }

    public String getNumeroDoCartao() {
        return numeroCartao;
    }

    public void setNumeroDoCartao(String numeroDoCartao) {
        this.numeroCartao = numeroDoCartao;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

   

    public BandeiraCartao getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }
  }