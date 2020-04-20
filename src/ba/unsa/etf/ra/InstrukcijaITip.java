package ba.unsa.etf.ra;

public class InstrukcijaITip extends Instrukcija {
    private String izvor;
    private String odrediste;
    private short neposrednaVrijednost;

    public InstrukcijaITip(String naziv, String izvor, String odrediste, short neposrednaVrijednost) {
        super(naziv);
        this.izvor = izvor;
        this.odrediste = odrediste;
        this.neposrednaVrijednost = neposrednaVrijednost;
    }

    public String getIzvor() {
        return izvor;
    }

    public void setIzvor(String izvor) {
        this.izvor = izvor;
    }

    public String getOdrediste() {
        return odrediste;
    }

    public void setOdrediste(String odrediste) {
        this.odrediste = odrediste;
    }

    public short getNeposrednaVrijednost() {
        return neposrednaVrijednost;
    }

    public void setNeposrednaVrijednost(short neposrednaVrijednost) {
        this.neposrednaVrijednost = neposrednaVrijednost;
    }
}
