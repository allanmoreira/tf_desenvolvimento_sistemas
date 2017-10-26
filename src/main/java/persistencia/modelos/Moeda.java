package persistencia.modelos;

import java.math.BigDecimal;

public class Moeda {
    private int idMoeda;
    private String nome, sigla;
    private BigDecimal valor;

    public Moeda(int idMoeda) {
        this.idMoeda = idMoeda;
    }

    public Moeda(int idMoeda, String nome, String sigla, BigDecimal valor) {
        this.idMoeda = idMoeda;
        this.nome = nome;
        this.sigla = sigla;
        this.valor = valor;
    }

    public int getIdMoeda() {
        return idMoeda;
    }

    public void setIdMoeda(int idMoeda) {
        this.idMoeda = idMoeda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
