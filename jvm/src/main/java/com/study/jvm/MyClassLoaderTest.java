package com.study.jvm;

import java.io.FileInputStream;
import java.lang.reflect.Method;

public class MyClassLoaderTest {

    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fileInputStream = new FileInputStream(classPath + "/" + name + ".class");
            int len = fileInputStream.available();
            byte[] data = new byte[len];
            fileInputStream.read(data);
            fileInputStream.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass 将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节数组
                return defineClass(name, data, 0, data.length);

            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

        /**
         * 重写类加载方法，实现自己的加载逻辑，不委派给双亲加载
         * @param name
         * @param resolve
         * @return
         * @throws ClassNotFoundException
         */
        @Override
        protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException
        {
            synchronized (getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    long t0 = System.nanoTime();


                    if (c == null) {
                        // If still not found, then invoke findClass in order
                        // to find the class.
                        long t1 = System.nanoTime();

                        if (!name.startsWith("com.tuling.jvm")){
                            c = this.getParent().loadClass(name);
                        }
                        else {
                            c = findClass(name);
                        }

                        // this is the defining class loader; record the stats
                        sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                        sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                        sun.misc.PerfCounter.getFindClasses().increment();
                    }
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }

        public static void main(String args[]) throws Exception {
            //初始化自定义类加载器，会先初始化父类ClassLoader，其中会把自定义类加载器的父加载 器设置为应用程序类加载器AppClassLoader
            MyClassLoader classLoader = new MyClassLoader("/Users/zhangxiaojun/work/study/github/jvm/jvm/src/test/clazz");
            //D盘创建 test/com/tuling/jvm 几级目录，将User类的复制类User1.class丢入该目录
            Class clazz = classLoader.loadClass("com.tuling.jvm.User1");
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("sout", null);
            method.invoke(obj, null);
            System.out.println(clazz.getClassLoader().getClass().getName());

            System.out.println();

            MyClassLoader classLoader1 = new MyClassLoader("/Users/zhangxiaojun/work/study/github/jvm/jvm/src/test/clazz1");
            //D盘创建 test/com/tuling/jvm 几级目录，将User类的复制类User1.class丢入该目录
            Class clazz1 = classLoader1.loadClass("com.tuling.jvm.User1");
            Object obj1 = clazz1.newInstance();
            Method method1 = clazz1.getDeclaredMethod("sout", null);
            method1.invoke(obj1, null);
            System.out.println(clazz1.getClassLoader().getClass().getName());


        }

    }
}
