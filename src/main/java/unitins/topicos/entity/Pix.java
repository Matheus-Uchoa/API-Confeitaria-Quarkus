package unitins.topicos.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Pix extends MetodoPagamento {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    private LocalDate dataExpiracaoTokenPix;

    public Pix(Double valor, String nome, String cpf) {

        super(valor);

        this.nome = nome;
        this.cpf = cpf;
        this.dataExpiracaoTokenPix = LocalDate.now().plusDays(1);
    }

    public Pix() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataExpiracaoTokenPix() {
        return dataExpiracaoTokenPix;
    }

    public void setDataExpiracaoTokenPix(LocalDate dataExpiracaoTokenPix) {
        this.dataExpiracaoTokenPix = dataExpiracaoTokenPix;
    }
}

