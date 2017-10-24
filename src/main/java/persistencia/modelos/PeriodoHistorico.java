package persistencia.modelos;

public enum PeriodoHistorico {
    SEMANA("s"),
    MES("m"),
    ANO("a"),
    ;

    private String nome;

    PeriodoHistorico(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
