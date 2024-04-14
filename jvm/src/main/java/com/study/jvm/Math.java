package com.study.jvm;

/**
 * javap -v Math.class > Math.txt
 */
public class Math {
    public static final int initData = 666;

    public int compute() { //一个方法对应一块栈帧内存区域
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    /**
     * 运行此代码，cpu会飙高
     * @param args
     */
    public static void main(String[] args) {
        Math math = new Math();
        while (true){
            math.compute();
        }
    }

    public static void main0(String[] args) {
        Math math = new Math();
        math.compute();

        Class<? extends Math> mathClass = math.getClass();



    }
}