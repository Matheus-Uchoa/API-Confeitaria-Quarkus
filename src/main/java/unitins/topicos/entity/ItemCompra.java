package unitins.topicos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemCompra extends DefaultEntity {
    
    private int quantidade;
    private double totalItem;
    private boolean idComprado = false;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ConfeitariaEntity produto;

    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quant) {
        this.quantidade = quant;
    }
    public double getTotalItem() {
        return totalItem;
    }
    public void setTotalItem(double totalItem) {
        this.totalItem = totalItem;
    }
    public UsuarioEntity getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    public ConfeitariaEntity getProduto() {
        return produto;
    }
    public void setProduto(ConfeitariaEntity produto) {
        this.produto = produto;
    }
    public boolean isIdComprado() {
        return idComprado;
    }
    public void setIdComprado(boolean idComprado) {
        this.idComprado = idComprado;
    }
    
    

    
}
