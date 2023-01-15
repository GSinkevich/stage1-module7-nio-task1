package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;



public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();


        try {
            RandomAccessFile aFile = new RandomAccessFile(file.getPath(), "r");
            FileChannel inChanel = aFile.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(60);
            String line = null;

            while (inChanel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i <buffer.limit(); i++) {
                    var currenctchar = (char) buffer.get();
                    line += currenctchar;
                }
                buffer.clear();
            }
            inChanel.close();

            String[] keyValuePair = line.split(System.lineSeparator());

            for (int i = 0; i < keyValuePair.length; i++) {
                keyValuePair[i] = keyValuePair[i].replaceAll("\\s", "");
                String[] mas = keyValuePair[i].split(":", 2);
                if (mas.length >= 1) {
                    keyValuePair[i] = mas[1];
                }
            }
            int age = Integer.parseInt(keyValuePair[1]);
            long phone = Long.parseLong(keyValuePair[3]);

            profile = new Profile(keyValuePair[0],age,keyValuePair[2],phone);

        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            e.getMessage();
        }


        return profile;
    }
}
