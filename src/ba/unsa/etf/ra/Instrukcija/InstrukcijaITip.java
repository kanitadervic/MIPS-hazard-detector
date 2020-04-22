package ba.unsa.etf.ra.Instrukcija;

public class InstrukcijaITip extends Instrukcija {
    private String izvor;
    private String odrediste;
    private short neposrednaVrijednost;
    private String zapis;

    public InstrukcijaITip(String naziv, String izvor, String odrediste, short neposrednaVrijednost, String zapis) {
        super(naziv);
        this.izvor = izvor;
        this.odrediste = odrediste;
        this.neposrednaVrijednost = neposrednaVrijednost;
        this.zapis = zapis;
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

    public String getZapis() {
        return zapis;
    }

    public void setZapis(String zapis) {
        this.zapis = zapis;
    }
}
