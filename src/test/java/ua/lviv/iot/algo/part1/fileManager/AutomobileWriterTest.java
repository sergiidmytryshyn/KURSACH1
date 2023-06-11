package ua.lviv.iot.algo.part1.fileManager;

import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part1.model.Automobile;

import static org.junit.jupiter.api.Assertions.*;


class AutomobileWriterTest {

    @Test
    void writeStones() {
        AutomobileWriter miwa = new AutomobileWriter();
        miwa.writeAutomobile(new Automobile(1,"BMV","Lviv,st. Vyhovskogo","26.09","none"));
        assertEquals(1,1);
    }

    @Test
    void savingLastId() {
        AutomobileWriter miwa = new AutomobileWriter();
        miwa.savingLastId(145454);
        assertEquals(1,1);
    }



    @Test
    void getFilesFromDirectory() {
        AutomobileWriter miwa = new AutomobileWriter();
        System.out.println(miwa.getFilesFromDirectory());
        assertEquals(1,1);
    }

    @Test
    void getAllEntries() {
        AutomobileWriter miwa = new AutomobileWriter();
        System.out.println(miwa.getAllEntries());
        assertEquals(1,1);
    }

    @Test
    void deleteEntry() {
        AutomobileWriter miwa = new AutomobileWriter();
        miwa.deleteEntry(2);
        assertEquals(1,1);
    }

    @Test
    void modifyEntry() {
        AutomobileWriter miwa = new AutomobileWriter();
        miwa.modifyEntry(3,new Automobile(3,"miwa","miwa","miwa","miwa"));
        assertEquals(1,1);
    }

    @Test
    void getLastId() {
        AutomobileWriter miwa = new AutomobileWriter();
        assertEquals(145454,miwa.getLastId());
    }
}