package com.study.jvm;

import java.util.ArrayList;
import java.util.Arrays;

public class HeapTest {

    byte[] a = new byte[1024 * 100]; //100 kb

    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapTest> heapTests = new ArrayList<>();
        while (true) {
            heapTests.add(new HeapTest());
            Thread.sleep(10);
        }

    }
}
