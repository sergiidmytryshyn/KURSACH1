package ua.lviv.iot.algo.part1.fileManager;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import ua.lviv.iot.algo.part1.model.Automobile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AutomobileWriter {

    public static final String PATH = "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator;

    public void writeAutomobile(final Automobile automobile) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yy");
        Date date = new Date();
        String file = PATH + "AutomobilesDB" + File.separator
                + "automobile_" + dateFormat.format(date) + ".scv";
        boolean exists = new File(file).exists();
        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
            if (!exists) {
                writer.writeNext(automobile.getHeaders().split(";"));
            }
            writer.writeNext(automobile.toSCV().split(";"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savingLastId(final int id) {
        try (CSVWriter writer = new CSVWriter(new
                FileWriter(PATH + "id.txt", false))) {
            writer.writeNext(String.valueOf(id).split(","));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLastId() {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(
                PATH + "id.txt")).build()) {
            List<String[]> allData = csvReader.readAll();
            return Integer.parseInt(allData.get(0)[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public HashMap<Integer, Automobile> readEntries(
            final String file) {
        HashMap<Integer, Automobile> data = new HashMap<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
                .withSkipLines(1).build()) {
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                data.put(Integer.parseInt(row[0]), new Automobile(
                        Integer.parseInt(row[0]), row[1], row[2], row[3], row[4]
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public List<String> getFilesFromDirectory() {
        File folder = new File(PATH + "AutomobilesDB" + File.separator);
        String[] fileNames = folder.list();
        List<String> files = new LinkedList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM_yy");
        Date date = new Date();
        String pattern = "automobile_" + "\\d{2}"
                + "_" + dateFormat.format(date) + ".scv";
        for (String file : fileNames) {
            if (file.matches(pattern)) {
                files.add(file);
            }
        }
        return files;
    }

    public HashMap<Integer, Automobile> getAllEntries() {
        HashMap<Integer, Automobile> finalMap = new HashMap<>();
        for (String file : getFilesFromDirectory()) {
            HashMap<Integer, Automobile> tmpMap = readEntries(
                    PATH + "AutomobilesDB" + File.separator + file);
            finalMap.putAll(tmpMap);
        }
        return finalMap;
    }

    public String findId(final int id) {
        String idLocation = null;
        for (String file : getFilesFromDirectory()) {
            HashMap<Integer, Automobile> tmpMap = readEntries(
                    PATH + "AutomobilesDB" + File.separator + file);
            if (tmpMap.containsKey(id)) {
                idLocation = file;
                break;
            }
        }
        return idLocation;
    }

    public void rewriteFile(final String fullPath,
                            final HashMap<Integer, Automobile> data) {
        try {
            File file = new File(fullPath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(fullPath, true))) {
            boolean forHeaders = false;
            for (Automobile automobile : data.values()) {
                if (!forHeaders) {
                    writer.writeNext(automobile.getHeaders().split(";"));
                    forHeaders = true;
                }
                writer.writeNext(automobile.toSCV().split(";"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEntry(final int id) {
        String fileName = findId(id);
        String fullPath = PATH + "AutomobilesDB" + File.separator + fileName;
        HashMap<Integer, Automobile> tmpMap = readEntries(fullPath);
        tmpMap.remove(id);
        rewriteFile(fullPath, tmpMap);
    }

    public void modifyEntry(final int id,
                            final Automobile newAutomobile) {
        String fileName = findId(id);
        String fullPath = PATH + "AutomobilesDB" + File.separator + fileName;
        HashMap<Integer, Automobile> tmpMap = readEntries(fullPath);
        tmpMap.put(id, newAutomobile);
        rewriteFile(fullPath, tmpMap);
    }
}

