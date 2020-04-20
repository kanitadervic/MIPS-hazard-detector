package ba.unsa.etf.ra.Instrukcija;

public class InstrukcijaJTip extends Instrukcija{
    private String izvor1;
    private String izvor2;
    private Integer indeksDestinacije; //indeks u listi instrukcija na koju skace

    public InstrukcijaJTip(String naziv, String izvor1, String izvor2, Integer indeksDestinacije) {
        super(naziv);
        this.izvor1 = izvor1;
        this.izvor2 = izvor2;
        this.indeksDestinacije = indeksDestinacije;
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

    public Integer getIndeksDestinacije() {
        return indeksDestinacije;
    }

    public void setIndeksDestinacije(Integer indeksDestinacije) {
        this.indeksDestinacije = indeksDestinacije;
    }
}
