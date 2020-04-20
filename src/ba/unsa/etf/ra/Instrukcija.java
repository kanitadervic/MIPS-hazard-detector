package ba.unsa.etf.ra;

public abstract class Instrukcija {
    private String naziv;

    public Instrukcija(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
