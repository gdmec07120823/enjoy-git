package com.lgd.home.study.learn3cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用引用类型原子操作类
 */
public class UseAtomicRef {
    static AtomicReference<UserInfo> userInfoAtomicReference=new AtomicReference<>();

    public static void main(String[] args) {
        UserInfo user=new UserInfo(15,"lgd");
        userInfoAtomicReference.set(user);
        user.setAge(18);
        UserInfo updater=new UserInfo(25,"laogandie");
        userInfoAtomicReference.compareAndSet(user,updater);
        System.out.println(userInfoAtomicReference.get().toString());
        System.out.println(user.toString());
    }

    static class UserInfo{
        private int age;
        private String name;

        public UserInfo(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "age='" + age + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
