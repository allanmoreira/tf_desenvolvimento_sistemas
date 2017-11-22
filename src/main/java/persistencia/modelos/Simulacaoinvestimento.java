package persistencia.modelos;

import java.math.BigDecimal;

public class Simulacaoinvestimento {
    private BigDecimal mediaPeriodo;
    private boolean aceitavel;

    public Simulacaoinvestimento(BigDecimal mediaPeriodo, boolean aceitavel) {
        this.mediaPeriodo = mediaPeriodo;
        this.aceitavel = aceitavel;
    }

    public BigDecimal getMediaPeriodo() {
        return mediaPeriodo;
    }

    public void setMediaPeriodo(BigDecimal mediaPeriodo) {
        this.mediaPeriodo = mediaPeriodo;
    }

    public boolean isAceitavel() {
        return aceitavel;
    }

    public void setAceitavel(boolean aceitavel) {
        this.aceitavel = aceitavel;
    }
}
