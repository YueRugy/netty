package com.yue.core;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by yue on 2016/7/20
 *
 * @see FutureTask
 */
public class CompletionServiceTest {
    static class Task implements Callable<String> {

        private int i;

        public Task(int i) {
            this.i = i;
        }

        public String call() throws Exception {
            Thread.sleep(1000);
            return Thread.currentThread().getName() + "  " + i;
        }
    }

    public static void main(String[] args) {
        testUseFuture();
        testUseFuture1();
    }


    public static void testUseFuture() {
        int threadNum = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        List<Future<String>> list = new CopyOnWriteArrayList<Future<String>>();
        for (int i = 0; i < threadNum; i++) {
            list.add(executor.submit(new Task(i)));
        }

        while (threadNum > 0) {
            for (Future<String> future : list) {
                String result = null;
                try {
                    result = future.get(0, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                }
                if (result != null) {
                    System.out.println(result);
                    list.remove(future);
                    threadNum--;
                }
            }
        }

    }

    public static void testUseFuture1() {
        int threadNum = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        ExecutorCompletionService<String> esc = new ExecutorCompletionService<String>(executor);
        for (int i = 0; i < threadNum; i++) {
            esc.submit(new Task(i));
        }

        for (int i = 0; i < threadNum; i++) {
            try {
                System.out.println(esc.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }


}
