package persistencia.modelos;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DadosBitTrex {
    private String O, H, L, C, V, T, BV;

    public BigDecimal getO() {
        return new BigDecimal(O);
    }

    public void setO(String o) {
        O = o;
    }

    public BigDecimal getH() {
        return new BigDecimal(H);
    }

    public void setH(String h) {
        H = h;
    }

    public BigDecimal getL() {
        return new BigDecimal(L);
    }

    public void setL(String l) {
        L = l;
    }

    public BigDecimal getC() {
        return new BigDecimal(C);
    }

    public void setC(String c) {
        C = c;
    }

    public BigDecimal getV() {
        return new BigDecimal(V);
    }

    public void setV(String v) {
        V = v;
    }

    public Date getT() {
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return new Date(formatoData.parse(this.T).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setT(String t) {
        T = t;
    }

    public BigDecimal getBV() {
        return new BigDecimal(BV);
    }

    public void setBV(String BV) {
        this.BV = BV;
    }
}
