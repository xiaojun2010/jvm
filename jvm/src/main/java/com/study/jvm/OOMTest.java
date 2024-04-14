package com.study.jvm;

import com.tuling.jvm.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OOMTest {

    public static List<Object> list = new ArrayList<>();

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (true) {
            list.add(new User(i++, UUID.randomUUID().toString()));
            new User(j--, UUID.randomUUID().toString());
        }
    }
}
