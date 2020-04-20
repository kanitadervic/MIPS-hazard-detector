package ba.unsa.etf.ra;

import ba.unsa.etf.ra.Instrukcija.Instrukcija;

import java.util.List;

public class Program {
    public Program() {
        InstructionFileHandler instructionFileHandler = new InstructionFileHandler("instrukcije.txt");
        List<Instrukcija> instrukcije = instructionFileHandler.getInstrukcije();

    }
}
