package com.figure.core.db;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class LevelDBManager {

    public static final Object lock = new Object();

    private final String levelDBPath;
    private DB levelDB;

    public LevelDBManager(@Value("${leveldb.path}") String levelDBPath) {
        this.levelDBPath = levelDBPath;
    }

    @PostConstruct
    public void init() throws IOException {
        Options options = new Options();
        options.createIfMissing(true);
        levelDB = Iq80DBFactory.factory.open(new File(levelDBPath), options);
        clearData(); // 在初始化之前清除 LevelDB 数据
    }

    public void remove(String key) {
        levelDB.delete(key.getBytes(StandardCharsets.UTF_8));
    }

    public void clearData() {
        try {
            String[] keys = getAllKeys();
            for (String key : keys) {
                levelDB.delete(key.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            // 处理异常
        }
    }

    public String[] getAllKeys() throws IOException {
        List<String> keys = new ArrayList<>();
        try (DBIterator iterator = levelDB.iterator()) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                keys.add(new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8));
            }
        }
        return keys.toArray(new String[0]);
    }

    public String[] getAllKeys(String startWith) throws IOException {
        List<String> keys = new ArrayList<>();
        try (DBIterator iterator = levelDB.iterator()) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                String key= new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8);
                if(key.startsWith(startWith)){
                    keys.add(key);
                }
            }
        }
        return keys.toArray(new String[0]);
    }

    public <T> void putObjectList(String key, List<T> objectList) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(objectList);
        oos.close();

        byte[] bytes = baos.toByteArray();
        levelDB.put(key.getBytes(), bytes);
    }

    public <T> List<T> getObjectList(String key) throws IOException, ClassNotFoundException {
        byte[] bytes = levelDB.get(key.getBytes());
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        List<T> objectList = (List<T>) ois.readObject();
        ois.close();
        return objectList;
    }

}