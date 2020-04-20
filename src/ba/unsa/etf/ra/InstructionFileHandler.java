package ba.unsa.etf.ra;

import ba.unsa.etf.ra.Instrukcija.Instrukcija;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaITip;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaJTip;
import ba.unsa.etf.ra.Instrukcija.InstrukcijaRTip;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InstructionFileHandler {
    private final List<Instrukcija> instrukcije = new ArrayList<>();
    private final Map<String, Integer> labele = new HashMap<>();

    public InstructionFileHandler(String putanja) {
        try {
            izdvojiLabele(putanja);
            izdvojiInstrukcije(putanja);
        } catch (FileNotFoundException e) {
            System.out.println(String.format("Fajl %s nije pronadjen", putanja));
        }
    }

    private void izdvojiInstrukcije(String putanja) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(putanja));
        while (scanner.hasNextLine()) {
            Scanner linija = new Scanner(scanner.nextLine());
            while (linija.hasNext()) {
                String instrukcija = linija.nextLine();
                if (instrukcija.contains(":")) {
                    instrukcija = instrukcija.substring(instrukcija.indexOf(":") + 2);
                }
                String tempInstrukcija = instrukcija.replace(" ", ",");
                tempInstrukcija = tempInstrukcija.replace("(", ",");
                tempInstrukcija = tempInstrukcija.replace(")", "");
                List<String> dijeloviInstrukcije = Arrays.asList(tempInstrukcija.split(","));
                boolean imaNeposrednu = false;
                int imm = 0;
                try {
                    imm = Integer.parseInt(dijeloviInstrukcije.get(dijeloviInstrukcije.size() - 1));
                    imaNeposrednu = true;
                } catch (Exception ignored) {
                }
                String naziv = dijeloviInstrukcije.get(0);
                if (instrukcija.contains("(") || imaNeposrednu) {
                    dodajITipInstrukciju(dijeloviInstrukcije, imm);
                } else if (naziv.toLowerCase().equals("bne") || naziv.toLowerCase().equals("beq")) {
                    dodajJTipInstrukciju(dijeloviInstrukcije, naziv);
                } else {
                    dodajRTipInstrukciju(dijeloviInstrukcije, naziv);
                }
            }
        }

    }

    private void dodajRTipInstrukciju(List<String> dijeloviInstrukcije, String naziv) {
        instrukcije.add(new InstrukcijaRTip(
                naziv,
                dijeloviInstrukcije.get(2),
                dijeloviInstrukcije.get(3),
                dijeloviInstrukcije.get(1)));
    }

    private boolean dodajJTipInstrukciju(List<String> dijeloviInstrukcije, String naziv) {
        return instrukcije.add(new InstrukcijaJTip(
                naziv,
                dijeloviInstrukcije.get(1),
                dijeloviInstrukcije.get(2),
                labele.get(dijeloviInstrukcije.get(3))));
    }

    private void dodajITipInstrukciju(List<String> dijeloviInstrukcije, Integer imm) {
        if (dijeloviInstrukcije.size() == 3) {
            imm = Integer.parseInt(dijeloviInstrukcije.get(2));
            instrukcije.add(new InstrukcijaITip(
                    dijeloviInstrukcije.get(0),
                    dijeloviInstrukcije.get(3),
                    dijeloviInstrukcije.get(1),
                    imm.shortValue()));
        } else {
            instrukcije.add(new InstrukcijaITip(
                    dijeloviInstrukcije.get(0),
                    dijeloviInstrukcije.get(2),
                    dijeloviInstrukcije.get(1),
                    imm.shortValue()));
        }
    }

    private void izdvojiLabele(String putanja) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(putanja));
        int i = 0;
        while (scanner.hasNextLine()) {
            Scanner linija = new Scanner(scanner.nextLine());
            while (linija.hasNext()) {
                String rijec = linija.next();
                if (rijec.contains(":")) {
                    rijec = rijec.substring(0, rijec.length() - 1);
                    labele.put(rijec, i);
                }
            }
            i++;
        }
    }

    public List<Instrukcija> getInstrukcije() {
        return instrukcije;
    }
}
