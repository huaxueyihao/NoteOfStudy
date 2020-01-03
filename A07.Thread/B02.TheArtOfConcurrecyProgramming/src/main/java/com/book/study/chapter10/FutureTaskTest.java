package com.book.study.chapter10;

import java.util.concurrent.*;

public class FutureTaskTest {

    private final ConcurrentHashMap<Object, Future<String>> taskCache = new ConcurrentHashMap<Object, Future<String>>();


    private String executionTask(final String taskName) throws ExecutionException, InterruptedException {

        while (true) {
            Future<String> future = taskCache.get(taskName);
            if (future == null) {
                Callable<String> task = new Callable<String>() {
                    public String call() throws Exception {
                        return taskName;
                    }
                };

                FutureTask<String> futureTask = new FutureTask<String>(task);

                future = taskCache.putIfAbsent(task, futureTask);

                if(future == null){
                    future = futureTask;
                    futureTask.run();
                }
            }

            try {
                return future.get();
            }catch (CancellationException e){
                taskCache.remove(taskName,future);
            }
        }

    }


}
