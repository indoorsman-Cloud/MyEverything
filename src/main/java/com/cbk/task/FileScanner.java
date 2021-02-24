package com.cbk.task;

import com.cbk.model.FileMeta;
import com.cbk.service.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  传入参数，一个文件夹
 *  目标：
 *      1.扫描这个文件夹的所有孩子
 *      2.针对孩子中也是文件夹的，包装成一个新的任务，提交给线程池
 *      3.收集所有的非文件夹孩子们
 *      4.对比入库
 */
class ScanJob implements Runnable{

    private File root;
    private ExecutorService threadPool;
    private FileService fileService;
    private CountDownLatch countDownLatch;
    private AtomicInteger jobCount;

    public ScanJob(File root, ExecutorService threadPool, FileService fileService, AtomicInteger jobCount, CountDownLatch countDownLatch) {
        this.root = root;
        this.threadPool = threadPool;
        this.fileService = fileService;
        this.jobCount = jobCount;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        File[] childdren = root.listFiles();
        if (childdren != null) {
            List<FileMeta> scanResultList = new ArrayList<>();
            for (File child : childdren) {
                if (child.isDirectory()) {
                    jobCount.incrementAndGet();
                    ScanJob job = new ScanJob(child, threadPool, fileService, jobCount, countDownLatch);
                    threadPool.execute(job);
                } else if (child.isFile()) {
                    scanResultList.add(new FileMeta(child));
                }
            }
            fileService.service(root.getAbsolutePath(), scanResultList);
            if (jobCount.decrementAndGet() == 0) {
                countDownLatch.countDown();
            }
        }
    }
}

public class FileScanner {

    private ExecutorService threadPool = new ThreadPoolExecutor(
            4, 4, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10)
    );

    private final FileService fileService = new FileService();

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private AtomicInteger jobCount = new AtomicInteger();

    public void scan(File root) {
        ScanJob job = new ScanJob(root, threadPool, fileService, jobCount, countDownLatch);
        jobCount.incrementAndGet();
        threadPool.execute(job);


        // 如果不加限制，提交任务之后，会运行到这里
        // 但是这个时候扫描工作不一定完成
        // 需要设置，在这里等待，知道扫描工作完成
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void scanDir(File root) {
        if (!root.isDirectory()) {
            return;
        }

        File[] children = root.listFiles();
        if (children == null) {
            return;
        }

        List<FileMeta> scanResultList = new ArrayList<>();
        for (File child : children) {
            scanDir(child);
            if (child.isFile()) {
                scanResultList.add(new FileMeta(child));
            }
        }

        fileService.service(root.getAbsolutePath(), scanResultList);
    }
}
