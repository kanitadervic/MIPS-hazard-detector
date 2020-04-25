package ba.unsa.etf.ra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgramTest {
    @BeforeEach
    public void reset() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("");
        fileWriter.close();
    }

    @Test
    public void slucajATest1() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("add r3,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "label1: addi r20,r20,20");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 1 ima instrukciju zadrške: add r3,r0,r1" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void slucajATest2() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("label1: add r3,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "label2: addi r20,r20,20\n" +
                "lw r1,0(r2)\n" +
                "beq r2,r5,label2\n" +
                "addi r20,r20,20");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 1 ima instrukciju zadrške: add r3,r0,r1" + System.lineSeparator() +
                "Instrukcija beq, r2, r5 na poziciji 4 ima instrukciju zadrške: lw r1,0(r2)" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    //Slucaj A nije ispunjen, te ce slucaj B biti ispunjen
    @Test
    public void slucajBTest1() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("add r2,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "lw r1,0(r2)\n" +
                "label1: addi r20,r20,20");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 1 ima instrukciju zadrške: addi r20,r20,20" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void slucajBTest2() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("add r2,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "label2: lw r1,0(r2)\n" +
                "label1: addi r20,r20,20\n" +
                "sub r2,r0,r1\n" +
                "beq r2,r0,label2\n" +
                "lw r1,0(r2)");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 1 ima instrukciju zadrške: addi r20,r20,20" + System.lineSeparator() +
                "Instrukcija beq, r2, r0 na poziciji 5 ima instrukciju zadrške: lw r1,0(r2)" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    //A i B slucajevi nisu ispunjeni, C slucaj ispunjen
    @Test
    public void slucajCTest1() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("add r2,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "lw r0,5(r5)\n" +
                "ori r13,r12,50\n" +
                "add r1,r2,r3\n" +
                "label1: addi r0,r20,20\n" +
                "sub r2,r0,r1");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 1 ima instrukciju zadrške: ori r13,r12,50" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void slucajCTest2() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("add r2,r0,r1\n" +
                "beq r2,r1,label2\n" +
                "and r13,r12,r11\n" +
                "add r2,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "lw r0,5(r5)\n" +
                "ori r13,r12,50\n" +
                "add r1,r2,r3\n" +
                "label1: addi r0,r20,20\n" +
                "label2: sub r2,r0,r1");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r1 na poziciji 1 ima instrukciju zadrške: and r13,r12,r11" + System.lineSeparator() +
                "Instrukcija beq, r2, r0 na poziciji 4 ima instrukciju zadrške: ori r13,r12,50" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void slucajeviAiBtest() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("label1: add r3,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "label2: addi r15,r20,20\n" +
                "lw r2,0(r2)\n" +
                "beq r2,r5,label2\n" +
                "addi r20,r20,20");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 1 ima instrukciju zadrške: add r3,r0,r1" + System.lineSeparator() +
                "Instrukcija beq, r2, r5 na poziciji 4 ima instrukciju zadrške: addi r15,r20,20" + System.lineSeparator();
        assertEquals(expected, actual);

    }

    @Test
    public void slucajeviAiCtest() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("label1: add r3,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "label2: addi r20,r20,20\n" +
                "lw r2,0(r2)\n" +
                "andi r6,r5,20\n" +
                "add r2,r2,r2\n" +
                "beq r2,r5,label2\n" +
                "addi r20,r20,20");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 1 ima instrukciju zadrške: add r3,r0,r1" + System.lineSeparator() +
                "Instrukcija beq, r2, r5 na poziciji 6 ima instrukciju zadrške: andi r6,r5,20" + System.lineSeparator();
        assertEquals(expected, actual);

    }

    @Test
    public void slucajeviBiCtest() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("label1: add r3,r0,r1\n" +
                "add r2,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "label2: addi r20,r20,20\n" +
                "lw r2,0(r2)\n" +
                "andi r6,r5,20\n" +
                "add r2,r2,r2\n" +
                "beq r2,r5,label2\n" +
                "addi r20,r20,20");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 2 ima instrukciju zadrške: add r3,r0,r1" + System.lineSeparator() +
                "Instrukcija beq, r2, r5 na poziciji 7 ima instrukciju zadrške: andi r6,r5,20" + System.lineSeparator();
        assertEquals(expected, actual);

    }

    @Test
    public void slucajeviAiBiCtest() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("label1: add r3,r0,r1\n" +
                "add r2,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "label2: addi r20,r20,20\n" +
                "lw r2,0(r2)\n" +
                "andi r6,r5,20\n" +
                "add r2,r2,r2\n" +
                "beq r2,r5,label2\n" +
                "add r1,r7,r8\n" +
                "beq r6,r4,label1\n" +
                "addi r20,r20,20");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 2 ima instrukciju zadrške: add r3,r0,r1" + System.lineSeparator() +
                "Instrukcija beq, r2, r5 na poziciji 7 ima instrukciju zadrške: andi r6,r5,20" + System.lineSeparator() +
                "Instrukcija beq, r6, r4 na poziciji 9 ima instrukciju zadrške: add r1,r7,r8" + System.lineSeparator();
        assertEquals(expected, actual);

    }

    @Test
    public void nemaZadrskeTest() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("label1: add r3,r0,r1\n" +
                "add r2,r0,r1\n" +
                "beq r2,r0,label1\n" +
                "label2: addi r20,r20,20\n" +
                "lw r2,0(r2)\n" +
                "andi r6,r5,20\n" +
                "add r2,r2,r2\n" +
                "beq r2,r5,label2\n" +
                "add r1,r7,r8\n" +
                "beq r6,r4,label1\n" +
                "add r20,r20,r1\n" +
                "add r20,r2,r1\n" +
                "sub r4,r3,r6");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 2 nema instrukciju zadrške."+ System.lineSeparator() +
                "Instrukcija beq, r2, r5 na poziciji 7 nema instrukciju zadrške."+ System.lineSeparator() +
                "Instrukcija beq, r6, r4 na poziciji 9 nema instrukciju zadrške."+ System.lineSeparator();
        assertEquals(expected, actual);

    }

    @Test
    public void test1() throws IOException {
        FileWriter fileWriter = new FileWriter("instrukcije.txt");
        fileWriter.write("lw r2,0(r1)\n" +
                "label1: beq r2,r0,label2\n" +
                "addi r20,r20,20\n" +
                "beq r3,r0,label1\n" +
                "lw r6,0(r8)\n" +
                "add r4,r3,r1\n" +
                "label2: lw r3,0(r8)\n" +
                "label3: add r1,r2,r6");
        fileWriter.close();
        Program program = new Program(); //Upisuje instrukcije zadrske u konacanRezultat
        String actual = Files.readString(Paths.get("konacanRezultat.txt"));
        String expected = "Instrukcija beq, r2, r0 na poziciji 1 ima instrukciju zadrške: addi r20,r20,20"+ System.lineSeparator() +
                "Instrukcija beq, r3, r0 na poziciji 3 ima instrukciju zadrške: addi r20,r20,20"+ System.lineSeparator();
        assertEquals(expected, actual);
    }
}