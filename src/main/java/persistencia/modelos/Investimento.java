package persistencia.modelos;

import java.math.BigDecimal;
import java.sql.Date;

public class Investimento {

    private Moeda moeda;
    private Usuario usuario;
    private String descricao;
    private Date dataInicial, dataFinal;
    private BigDecimal quantidade;
    private int idInvestimento;

    public Investimento(int idInvestimento, Moeda moeda, Usuario usuario, String descricao, Date dataInicial, Date dataFinal, BigDecimal quantidade) {
        this.idInvestimento = idInvestimento;
        this.moeda = moeda;
        this.usuario = usuario;
        this.descricao = descricao;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.quantidade = quantidade;
    }

    public Investimento(Moeda moeda, Usuario usuario, String descricao, Date dataInicial, Date dataFinal, BigDecimal quantidade) {
        this.moeda = moeda;
        this.usuario = usuario;
        this.descricao = descricao;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.quantidade = quantidade;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}
