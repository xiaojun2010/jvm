package com.tuling.jvm;

public class User1 {
    int id = 1;
    String name;

    public void sout() {
        System.out.println(" 自己的加载器加载类调用方法： id = " + id + " , name = " + name);
    }

    @Override
    protected void finalize() throws Throwable {

//        OOMTest.list.add(this);
        System.out.println("关闭资源 ： user " + id + " 即将被回收");
    }
}
