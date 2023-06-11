package ua.lviv.iot.algo.part1.service;


import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part1.fileManager.AutomobileWriter;
import ua.lviv.iot.algo.part1.model.Automobile;

import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.HashMap;

import java.util.concurrent.atomic.AtomicInteger;

@Service

public class AutomobileService {

    private final AutomobileWriter automobileWriter = new AutomobileWriter();

    private HashMap<Integer, Automobile> automobiles
            = automobileWriter.getAllEntries();

    private AtomicInteger lastId
            = new AtomicInteger(automobileWriter.getLastId());

    public Automobile deleteAutomobile(final int id) {
        automobileWriter.deleteEntry(id);
        return automobiles.remove(id);
    }

    public void replaceAutomobile(final Automobile automobile, final int id) {
        automobile.setId(id);
        automobileWriter.modifyEntry(id, automobile);
        automobiles.put(id, automobile);
    }

    public Automobile addAutomobile(final Automobile automobile) {
        automobile.setId(lastId.incrementAndGet());
        automobileWriter.writeAutomobile(automobile);
        automobiles.put(automobile.getId(), automobile);
        return automobile;
    }

    public Collection<Automobile> giveAll() {
        return automobiles.values();
    }

    public Automobile giveAutomobile(final int id) {
        return automobiles.get(id);
    }

    public boolean hasAutomobileWith(final int id) {
        return automobiles.containsKey(id);
    }

    @PreDestroy
    public void onApplicationShutdown() {
        automobileWriter.savingLastId(lastId.intValue());
    }
}
