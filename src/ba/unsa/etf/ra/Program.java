package ba.unsa.etf.ra;

import ba.unsa.etf.ra.Instrukcija.Instrukcija;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaITip;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaJTip;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaRTip;

import java.util.ArrayList;
import java.util.List;


public class Program {
    private List<Instrukcija> instrukcije;

    public Program() {
        InstructionFileHandler instructionFileHandler = new InstructionFileHandler("instrukcije.txt");
        instrukcije = instructionFileHandler.getInstrukcije();
        List<Instrukcija> konacneInstrukcije = new ArrayList<>();

        for (int i = 0; i < instrukcije.size(); i++) {
            if (instrukcije.get(i) instanceof InstrukcijaJTip) {
                InstrukcijaJTip jTip = (InstrukcijaJTip) instrukcije.get(i);
                konacneInstrukcije.add(jTip);
                Boolean nasaoZadrsku = false;
                //prvi slucaj
                if (i != 0) {
                    if (instrukcije.get(i - 1) instanceof InstrukcijaRTip) {
                        InstrukcijaRTip rTip = (InstrukcijaRTip) instrukcije.get(i - 1);
                        nasaoZadrsku = provjeriRTipZadrsku(konacneInstrukcije, jTip, rTip, i, i);
                    } else if (instrukcije.get(i - 1) instanceof InstrukcijaITip) {
                        InstrukcijaITip iTip = (InstrukcijaITip) instrukcije.get(i - 1);
                        nasaoZadrsku = provjeriITipZadrsku(konacneInstrukcije, jTip, iTip, i, i);
                    }
                }
                //drugi slucaj
                if (!nasaoZadrsku) {
                    if (instrukcije.get(jTip.getIndeksDestinacije()) instanceof InstrukcijaRTip) {
                        InstrukcijaRTip rTip = (InstrukcijaRTip) instrukcije.get(jTip.getIndeksDestinacije());
                        nasaoZadrsku = provjeriRTipZadrsku(konacneInstrukcije, jTip, rTip, i, instrukcije.size());
                    } else if (instrukcije.get(jTip.getIndeksDestinacije()) instanceof InstrukcijaITip) {
                        InstrukcijaITip iTip = (InstrukcijaITip) instrukcije.get(jTip.getIndeksDestinacije());
                        nasaoZadrsku = provjeriITipZadrsku(konacneInstrukcije, jTip, iTip, i, instrukcije.size());
                    }
                }
                //treci
//                if (!nasaoZadrsku) {
//
//                }
                if(!nasaoZadrsku) konacneInstrukcije.add(null);
            }
        }
        //        for(Instrukcija i: konacneInstrukcije) System.out.println(i.getNaziv());
        for (int i = 0; i < konacneInstrukcije.size(); i += 2) {
            if(instrukcije.get(i+1)==null) System.out.println(konacneInstrukcije.get(i).getNaziv() + " nema instrukciju zadrske");
            else System.out.println(konacneInstrukcije.get(i).getNaziv() + " " + konacneInstrukcije.get(i + 1).getNaziv());
        }
    }


    private boolean seKoristiUBloku(Instrukcija instrukcija, int pocetak, int kraj) {
        String odrediste = "";
        if (instrukcija instanceof InstrukcijaRTip) odrediste = ((InstrukcijaRTip) instrukcija).getOdrediste();
        else if (instrukcija instanceof InstrukcijaITip) odrediste = ((InstrukcijaITip) instrukcija).getOdrediste();
        else return true;

        int pozicijaInstrukcije = instrukcije.indexOf(instrukcija);

        for (int i = pocetak; i < kraj; i++) {
            if (i != pozicijaInstrukcije) {
                if (instrukcije.get(i) instanceof InstrukcijaRTip) {
                    InstrukcijaRTip rTip = ((InstrukcijaRTip) instrukcije.get(i));
                    if (rTip.getIzvor1().equals(odrediste) || rTip.getIzvor2().equals(odrediste)) return true;
                } else if (instrukcije.get(i) instanceof InstrukcijaITip) {
                    InstrukcijaITip iTip = ((InstrukcijaITip) instrukcije.get(i));
                    if (iTip.getIzvor().equals(odrediste)) return true;
                }
            }
        }
        return false;
    }

    private boolean provjeriRTipZadrsku(List<Instrukcija> konacneInstrukcije, InstrukcijaJTip jTip, InstrukcijaRTip rTip, int pocetak, int kraj) {
        if (!rTip.getOdrediste().equals(jTip.getIzvor1()) && !rTip.getOdrediste().equals(jTip.getIzvor2()) && !seKoristiUBloku(rTip, pocetak, kraj)) {
            konacneInstrukcije.add(rTip);
            return true;
        }
        return false;
    }

    private boolean provjeriITipZadrsku(List<Instrukcija> konacneInstrukcije, InstrukcijaJTip jTip, InstrukcijaITip iTip, int pocetak, int kraj) {
        if (!iTip.getOdrediste().equals(jTip.getIzvor1()) && !iTip.getOdrediste().equals(jTip.getIzvor2()) && !seKoristiUBloku(iTip, pocetak, kraj)) {
            konacneInstrukcije.add(iTip);
            return true;
        }
        return false;
    }
}
