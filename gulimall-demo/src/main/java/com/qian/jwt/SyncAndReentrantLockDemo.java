package com.qian.jwt;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * SyncAndReentrantLockDemo is
 * 题目：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
 * AA打印5次，BB打印10次，CC打印15次
 * 紧接着
 * AA打印5次，BB打印10次，CC打印15次
 * I
 * 来10轮
 *
 * @author QIAN
 * Date 2020/05/03
 * Time 08:46
 */
public class SyncAndReentrantLockDemo {


    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

/*        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print5();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print10();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print15();
            }
        }, "CC").start();*/

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print(1, 5);
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print(2, 10);
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print(3, 15);
            }
        }, "CC").start();
    }

}

class ShareResource {
    private int number = 1; // A:1 B:2 C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print(int num, int times) {
        lock.lock();
        try {
            // 判断
            while (number != num) {
                if (num == 1) {
                    c1.await();
                } else if (num == 2) {
                    c2.await();
                } else if (num == 3) {
                    c3.await();
                }
            }

            // 干活
            for (int i = 1; i <= times; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 通知
            if (num == 1) {
                number = 2;
                c2.signal();
            } else if (num == 2) {
                number = 3;
                c3.signal();
            } else if (num == 3) {
                number = 1;
                c1.signal();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void print5() {

        lock.lock();
        try {
            // 判断
            while (number != 1) {
                c1.await();
            }

            // 干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 通知
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {

        lock.lock();
        try {
            // 判断
            while (number != 2) {
                c2.await();
            }

            // 干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 通知
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {

        lock.lock();
        try {
            // 判断
            while (number != 3) {
                c3.await();
            }

            // 干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 通知
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
