package com.tuling.jvm;

import com.study.jvm.OOMTest;

public class User {
    int id = 1;
    String name;

    public User() {

    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sout() {
        System.out.println(" 自己的加载器加载类调用方法： id = " + id + " , name = " + name);
    }

    //User类需要重写finalize方法，一个对象的 finalize 只会执行一次
    //jvm底层有实现 finalize 底层对象集合，触发GC时会从集合里 找到对象执行 finalize ，执行后，会从集合里删除掉这个对象，下一次就不会触发
    @Override
    protected void finalize() throws Throwable {
        OOMTest.list.add(this);
        System.out.println("关闭资源，userid=" + id + "即将被回收");
    }
}
