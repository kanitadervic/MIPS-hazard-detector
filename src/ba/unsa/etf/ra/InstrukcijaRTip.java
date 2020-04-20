package ba.unsa.etf.ra;

public class InstrukcijaRTip extends Instrukcija{
    private String izvor1;
    private String izvor2;
    private String odrediste;

    public InstrukcijaRTip(String naziv, String izvor1, String izvor2, String odrediste) {
        super(naziv);
        this.izvor1 = izvor1;
        this.izvor2 = izvor2;
        this.odrediste = odrediste;
    }

    public String getIzvor1() {
        return izvor1;
    }

    public void setIzvor1(String izvor1) {
        this.izvor1 = izvor1;
    }

    public String getIzvor2() {
        return izvor2;
    }

    public void setIzvor2(String izvor2) {
        this.izvor2 = izvor2;
    }

    public String getOdrediste() {
        return odrediste;
    }

    public void setOdrediste(String odrediste) {
        this.odrediste = odrediste;
    }
}
