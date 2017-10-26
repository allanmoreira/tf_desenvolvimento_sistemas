package persistencia.modelos;

import java.math.BigDecimal;

public class PeriodoHistorico {
    private String descricao;
    private BigDecimal valorFechamento, valorVolume;

    public PeriodoHistorico(String descricao, BigDecimal valorFechamento, BigDecimal valorVolume) {
        this.descricao = descricao;
        this.valorFechamento = valorFechamento;
        this.valorVolume = valorVolume;
    }

    public BigDecimal getValorVolume() {
        return valorVolume;
    }

    public void setValorVolume(BigDecimal valorVolume) {
        this.valorVolume = valorVolume;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorFechamento() {
        return valorFechamento;
    }

    public void setValorFechamento(BigDecimal valorFechamento) {
        this.valorFechamento = valorFechamento;
    }
}
