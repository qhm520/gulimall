package com.qian.gulimall.search.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * ThreadTest is
 *
 * @author QIAN
 * Date 2020/07/30
 * Time 19:53
 */
public class ThreadTest {
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main...........start.......");


//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//            System.out.println("当前线程: " + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果：" + i);
//        }, executorService);
//        方法完成后感知
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程: " + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("运行结果：" + i);
//            return i;
//        }, executorService)
//                .whenComplete((result, exception) -> {
//                    // 虽然能得到异常信息，但是没法修改返回数据
//                    System.out.println("异步任务成功完成了, 结果是" + result + "异常是" + exception);
//                }).exceptionally(throwable -> {
//                    // 默认返回，可以感知异常，同时返回默认值
//                    return 10;
//                });

        /**
         * 方法完成后处理
         */
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程: " + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("运行结果：" + i);
//            return i;
//        }, executorService).handle((result, exception) -> {
//            if (result != null) {
//                return result * 2;
//            }
//            if (exception != null) {
//                return 500;
//            }
//            return 0;
//        });


        /**
         * 线程串行化
         * thenRunAsync  不能获取上一步的执行结果
         *   thenAcceptAsync 能获取上一步的执行结果
         *   thenApplyAsync能获取上一步的执行结果,能返回结果
         */
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程: " + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果：" + i);
//            return i;
//        }, executorService)
////                .thenRunAsync(() -> {
////            System.out.println("任务2");
////        }, executorService)
////        .thenAcceptAsync(result -> {
////            System.out.println("任务2" + result);
////        },executorService)
//                .thenApplyAsync(result -> {
//                    System.out.println("任务2" + result);
//                    return "hello " + result;
//                }, executorService);


//        Integer integer = future.get();
//        System.out.println("main...........end.........." + future.get());

        /**
         * 两个都完成
         */
//        CompletableFuture<Integer> future01 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务1线程: " + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("任务1结果：" + i);
//            return i;
//        }, executorService);
//
//        CompletableFuture<Integer> future02 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务2线程: " + Thread.currentThread().getId());
//            try {
//                Thread.sleep(6000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("任务2线程结束: " + Thread.currentThread().getId());
//            return 1;
//        }, executorService);


//        future01.runAfterBothAsync(future02, () -> { System.out.println("任务3线程: " + Thread.currentThread().getId());}, executorService)

//        CompletableFuture<Void> future = future01.thenAcceptBothAsync(future02, (f1, f2) -> {
//            System.out.println("任务3线程: " + Thread.currentThread().getId());
//            System.out.println(f1);
//            System.out.println(f2);
//        }, executorService);

//        CompletableFuture<String> future = future01.thenCombineAsync(future02, (f1, f2) -> {
//            return f1 + ":" + f2 + " ->hello ";
//        }, executorService);
//        System.out.println("main...........end.........." + future.get());

        /**
         * 两个任务，只要 一个任务完成，我们就执行任务3
         * runAfterEitherAsync 不感知结果，自己没有返回值
         * acceptEitherAsync  感知结果，自己没有返回值
         */
//        future01.runAfterEitherAsync(future02, () -> {
//            System.out.println("任务3");
//        }, executorService);

//        future01.acceptEitherAsync(future02, (result) -> {
//            System.out.println("任务3    " + result);
//        }, executorService);

//        CompletableFuture<Integer> future = future01.applyToEitherAsync(future02, result -> {
//            System.out.println("任务3    " + result);
//            return 1234;
//        }, executorService);
//        System.out.println("main...........end.........." + future.get());

        /**
         * 多任务组合
         * allOf  等待所有任务穿完成
         * anyOf  只要有一个任务完成
         */
        CompletableFuture<String> futureImage = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的图片信息");
            return "hello.jpg";
        }, executorService);

        CompletableFuture<String> futureAttr = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品属性");
            return "黑色+256G";
        }, executorService);

        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("查询商品介绍");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "华为";
        }, executorService);

//        futureImage.get();
//        futureAttr.get();
//        futureDesc.get();

        // 等待所有完成
//        CompletableFuture<Void> allOf = CompletableFuture.allOf(futureImage, futureAttr, futureDesc);
//        allOf.get();

        // 等待一个任务完成
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(futureImage, futureAttr, futureDesc);
        System.out.println("main...........end.........." + anyOf.get());
    }


    public void thread(String[] args) {
        System.out.println("main...........start.......");
        /**
         * 1.继承thread
         * 2.实现Runable接口
         * 3.实现callable接口+FutureTask（可以拿到返回结果，可以处理异常）
         * 4.线程池
         *      给线程池直接提交任务
         *     1.创建
         *          Executors
         *          new ThreadPoolExecutor()
         *
         *
         *  区别：
         *  1、2不能得到返回值，3可以获取返回值
         *  1、2、3都不能控制资源
         *  4可以控制资源，性能稳定
         */

        // 我们以后在业务代码里面，以上三种启动线程的方式都不用，将所有的多线程异步任务都提交给线程池直接执行
        new Thread(() -> System.out.println("hello")).start();

        // 当前系统中池只有一两个，每个异步任务，提交给线程池执行
        executorService.execute(new Runable01());

        /**
         * 七大参数
         * int corePoolSize, [5]核心线程数[一直存在，除非allowCoreThreadTimeOut]，线程池创建好以后就准备就绪的线程数量，就等待来接受异步任务去执行
         *  5个 Thread thread = new Thread();
         * int maximumPoolSize, 【200】最大线程数量，控制资源
         * long keepAliveTime, 存活时间;如果当前的线程数量大于core数量；
         * 释放空闲的线程（maximumPoolSize - corePoolSize），只要线程空闲大于指定的keepAliveTime
         * TimeUnit unit,   时间单位
         * BlockingQueue<Runnable> workQueue,  阻塞队列，如果任务有很多，就会将目前多的任务放在队列里面
         *      只要有线程空闲，就会去队列里面取出新的任务继续执行
         * ThreadFactory threadFactory, 线程的创建工厂
         * RejectedExecutionHandler handler 如果队列满了，按照我们指定的拒绝策略执行任务
         *
         *
         * 工作顺序：
         *  1.线程池创建，准备好core数量的核心线程，准备接受任务
         *  2.新的任务进来，用core准备好 空闲线程执行
         *      1.core满了，就将再进来 的任务放入阻塞队列中，空闲的core就会自己去阻塞队列获取任务执行
         *      2.阻塞队列满了，就直接开新线程执行，最大只能开到maximumPoolSize指定的数量
         *      3.maximumPoolSize都执行好了，maximumPoolSize数量空闲的线程会在keepAliveTime
         *      指定时间后自动销毁，最终保持到corePoolSize大小
         *      4.如果线程数开到maximumPoolSize的数量，还有新的任务进来，就会使用reject指定的拒绝策略进行处理
         *  3.所有的线程创建都是由指定的Factory创建的
         *
         *new LinkedBlockingDeque<>() 默认是Integer的最大值，内存不够
         *  面试：
         *  一个线程池 core7，max20，queue50 ，100并发进来怎么分配的
         *   答： 先有7个能直接得到执行，接下来的50进行队列排队，在多开13个继续执行，现在70个被安排上了，剩下的30个默认拒绝策略
         *   如果不想抛弃还要执行，使用CallerRunsPolicy
         *
         *
         *
         *   开发中为什么使用线程池
         *   1.降低资源的消耗
         *      通过重复利用已经创建好的线程降低线程的创建和销毁带来的损耗
         *   2.提高响应速度
         *      因为线程池中的线程数没有超过线程池的最大上限时，有的线程处于等待分配任务的状态，当任务来时无需创建新的线程就能执行
         *    3.提高线程的可管理性
         *      线程池会根据当前系统特点对池内的线程进行优化处理，减少追寻和销毁线程带来的系统开销，无限的创建和销毁线程不公消耗系统资源，
         *      还会降低系统的稳定性，使用线程池进行统一分配
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                200,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

//        Executors.newCachedThreadPool();  // core是0，所有都可回收
//        Executors.newFixedThreadPool();  // 固定大小，core=max 都不可回收
//        Executors.newScheduledThreadPool();  // 定时任务的线程池
//        Executors.newSingleThreadExecutor();  // 单线程的线程池，后台从队列获取任务，一个一个的执行

//        Thread01 thread01 = new Thread01();
//        thread01.start();


//        Runable01 runnable01 = new Runable01();
//        new Thread(runnable01).start();

//        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
//        new Thread(futureTask).start();
//
//        Integer integer = null;
//        try {
//            // 等待整个线程执行完成，获取返回结果
//            integer = futureTask.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        System.out.println(integer);

        System.out.println("main...........end..........");
    }

    public static class Thread01 extends Thread {

        @Override
        public void run() {
            System.out.println("当前线程: " + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }
    }

    public static class Runable01 implements Runnable {

        @Override
        public void run() {
            System.out.println("当前线程: " + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }
    }

    public static class Callable01 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("当前线程: " + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }
    }
}
