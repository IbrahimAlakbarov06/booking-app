package org.booking.util;

import org.booking.exception.FlightBookingException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static <T extends Serializable> void saveToFile(String filePath, List<T> data) {
        File file = new File(filePath);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(data));
        } catch (IOException e) {
            throw new FlightBookingException("Error saving data to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FlightBookingException("Error loading data from file: " + e.getMessage());
        }
    }

    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    public static void createBackup(String filePath) {
        if (!fileExists(filePath)) {
            return;
        }

        String backupPath = filePath + ".bak";
        File originalFile = new File(filePath);
        File backupFile = new File(backupPath);

        try (FileInputStream fis = new FileInputStream(originalFile);
             FileOutputStream fos = new FileOutputStream(backupFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new FlightBookingException("Error creating backup: " + e.getMessage());
        }
    }
}