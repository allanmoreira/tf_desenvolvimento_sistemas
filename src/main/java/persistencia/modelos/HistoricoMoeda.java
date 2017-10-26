package persistencia.modelos;

import java.math.BigDecimal;
import java.sql.Date;

public class HistoricoMoeda {
    private int idMoeda;
    private BigDecimal valorAbertura, valorFechamento, valorAlta, valorBaixa, volumeMoeda, volumeBTC;
    private Date dataHistorico;

    public HistoricoMoeda(int idMoeda, BigDecimal valorAbertura, BigDecimal valorFechamento, BigDecimal valorAlta, BigDecimal valorBaixa, BigDecimal volumeMoeda, BigDecimal volumeBTC, Date dataHistorico) {
        this.idMoeda = idMoeda;
        this.valorAbertura = valorAbertura;
        this.valorFechamento = valorFechamento;
        this.valorAlta = valorAlta;
        this.valorBaixa = valorBaixa;
        this.volumeMoeda = volumeMoeda;
        this.volumeBTC = volumeBTC;
        this.dataHistorico = dataHistorico;
    }

    public int getIdMoeda() {
        return idMoeda;
    }

    public void setIdMoeda(int idMoeda) {
        this.idMoeda = idMoeda;
    }

    public BigDecimal getValorAbertura() {
        return valorAbertura;
    }

    public void setValorAbertura(BigDecimal valorAbertura) {
        this.valorAbertura = valorAbertura;
    }

    public BigDecimal getValorFechamento() {
        return valorFechamento;
    }

    public void setValorFechamento(BigDecimal valorFechamento) {
        this.valorFechamento = valorFechamento;
    }

    public BigDecimal getValorAlta() {
        return valorAlta;
    }

    public void setValorAlta(BigDecimal valorAlta) {
        this.valorAlta = valorAlta;
    }

    public BigDecimal getValorBaixa() {
        return valorBaixa;
    }

    public void setValorBaixa(BigDecimal valorBaixa) {
        this.valorBaixa = valorBaixa;
    }

    public BigDecimal getVolumeMoeda() {
        return volumeMoeda;
    }

    public void setVolumeMoeda(BigDecimal volumeMoeda) {
        this.volumeMoeda = volumeMoeda;
    }

    public BigDecimal getVolumeBTC() {
        return volumeBTC;
    }

    public void setVolumeBTC(BigDecimal volumeBTC) {
        this.volumeBTC = volumeBTC;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }
}
