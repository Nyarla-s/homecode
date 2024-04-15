package com.example.usercontrol.service;

import com.example.usercontrol.model.MyUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;


import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class MyDataService implements InitializingBean, DisposableBean {

    private final Gson gson = new Gson();

    private final ConcurrentHashMap<Integer, MyUser> idUserMap = new ConcurrentHashMap<>();
    private File file;
    private final AtomicInteger modifyCounts = new AtomicInteger();
    private final ReentrantLock lock = new ReentrantLock();

    {
        try {
            file = ResourceUtils.getFile("classpath:userData.json");
        } catch (FileNotFoundException e) {
            URL resource = this.getClass().getClassLoader().getResource("application.properties");
            assert resource != null;
            file = new File(resource.getFile().replace("application.properties", "userData.json"));
            try {
                file.createNewFile();
            } catch (IOException ex) {
                log.error("create data file failed error is " + e.getMessage());
            }
        }
    }

    public MyUser getUser(Integer key) {
        return idUserMap.get(key);
    }

    public void addUser(MyUser tar) throws IOException {
        lock.lock();
        addUserIntoCache(tar);
        try {
            saveData();
        } catch (IOException e) {
            throw e;
        } finally {
            lock.unlock();
        }
    }

    public boolean delUser(MyUser tar) throws IOException {
        lock.lock();
        delUserFromCache(tar);
        try {
            saveData();
        } catch (IOException e) {
            throw e;
        } finally {
            lock.unlock();
        }
        return true;
    }

    public boolean dellAllUser() throws IOException {
        lock.lock();
        idUserMap.clear();
        try {
            saveData();
        } catch (IOException e) {
            throw e;
        } finally {
            lock.unlock();
        }

        return true;
    }

    private void delUserFromCache(MyUser tar) {
        modifyCounts.incrementAndGet();
        idUserMap.remove(tar.getUserId());
    }

    private void addUserIntoCache(MyUser add) {
        modifyCounts.incrementAndGet();
        idUserMap.put(add.getUserId(), add);
    }

    private void saveData() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
        modifyCounts.set(0);
        ArrayList<MyUser> myUsers = new ArrayList<>(idUserMap.values());
        for (MyUser myUser : myUsers) {
            bufferedWriter.write(gson.toJson(myUser));
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
    }

    @Override
    public void afterPropertiesSet() {
        if (file.exists()) {
            try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    MyUser myUser = gson.fromJson(line, MyUser.class);
                    if (myUser == null) {
                        continue;
                    }
                    idUserMap.put(myUser.getUserId(), myUser);
                }

            } catch (IOException e) {
                log.error("init data failed {}", e.getMessage());
            }
        } else {
            throw new RuntimeException("data not exits,init failed");
        }
    }

    @Override
    public void destroy() throws Exception {
        if (modifyCounts.get() > 0) {
            saveData();
        }
    }

}
