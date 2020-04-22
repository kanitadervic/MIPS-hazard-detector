package ba.unsa.etf.ra;

import ba.unsa.etf.ra.Instrukcija.Instrukcija;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaITip;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaJTip;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaRTip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
                    Instrukcija instrukcija = instrukcije.get(i - 1);
                    nasaoZadrsku = provjeriIiRTipZadrsku(jTip, instrukcija, i, i);
                    if (nasaoZadrsku) {
                        konacneInstrukcije.add(instrukcija);
                    }
                }
                //drugi slucaj
                if (!nasaoZadrsku) {
                    Instrukcija instrukcija = instrukcije.get(jTip.getIndeksDestinacije());
                    nasaoZadrsku = provjeriIiRTipZadrsku(jTip, instrukcija, i, instrukcije.size());
                    if (nasaoZadrsku) {
                        konacneInstrukcije.add(instrukcija);
                    }
                }
                //treci
                if (!nasaoZadrsku) {
                    int pocetak, kraj;
                    if (jTip.getIndeksDestinacije() > i) {
                        pocetak = i + 1;
                        kraj = jTip.getIndeksDestinacije();
                    } else {
                        pocetak = jTip.getIndeksDestinacije() + 1;
                        kraj = i;
                    }
                    for (int j = pocetak; j < kraj; j++) {
                        Instrukcija instrukcija = instrukcije.get(j);
                        nasaoZadrsku = provjeriIiRTipZadrsku(jTip, instrukcija, pocetak, j) && provjeriIiRTipZadrsku(jTip, instrukcija, kraj, instrukcije.size());
                        if (nasaoZadrsku) {
                            konacneInstrukcije.add(instrukcija);
                            break;
                        }
                    }
                }
                if (!nasaoZadrsku) konacneInstrukcije.add(null);
            }
        }
        try {
            FileWriter fileWriter = new FileWriter("konacanRezultat.txt");
            fileWriter.write("");
            for (int i = 0; i < konacneInstrukcije.size(); i += 2) {
                InstrukcijaJTip jTipInsturkcija = (InstrukcijaJTip) konacneInstrukcije.get(i);
                if (konacneInstrukcije.get(i + 1) == null) {
                    fileWriter.append(jTipInsturkcija.toString()).append(" nema instrukciju zadrske.").append(System.lineSeparator());
                } else {
                    fileWriter.append(jTipInsturkcija.toString()).append(" ima instrukciju zadrske: ");
                    Instrukcija instrukcija = konacneInstrukcije.get(i + 1);
                    if (instrukcija instanceof InstrukcijaITip) {
                        InstrukcijaITip instrukcijaITip = (InstrukcijaITip) instrukcija;
                        fileWriter.append(instrukcijaITip.getZapis()).append(System.lineSeparator());
                    } else if (instrukcija instanceof InstrukcijaRTip) {
                        InstrukcijaRTip instrukcijaRTip = (InstrukcijaRTip) instrukcija;
                        fileWriter.append(instrukcijaRTip.toString()).append(System.lineSeparator());
                    }
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    private boolean provjeriIiRTipZadrsku(InstrukcijaJTip jTip, Instrukcija instrukcija, int pocetak, int kraj) {
        if (instrukcija instanceof InstrukcijaITip) {
            InstrukcijaITip iTip = (InstrukcijaITip) instrukcija;
            if (!iTip.getOdrediste().equals(jTip.getIzvor1()) && !iTip.getOdrediste().equals(jTip.getIzvor2()) && !seKoristiUBloku(iTip, pocetak, kraj)) {
                return true;
            }
            return false;
        } else if (instrukcija instanceof InstrukcijaRTip) {
            InstrukcijaRTip rTip = (InstrukcijaRTip) instrukcija;
            if (!rTip.getOdrediste().equals(jTip.getIzvor1()) && !rTip.getOdrediste().equals(jTip.getIzvor2()) && !seKoristiUBloku(rTip, pocetak, kraj)) {
                return true;
            }
            return false;
        }
        return false;
    }
}
