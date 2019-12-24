package com.thread.study.chapter08;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DiskbasedRequestPersistence implements RequestPersistence {

    private final SectionBasedDiskStorage storage = new SectionBasedDiskStorage();

    @Override
    public void store(MMSDeliveryRequest request) {

        // 申请缓存文件的文件名
        String[] fileNameParts = storage.apply4Filename(request);
        File file = new File(fileNameParts[0]);

        try {
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            storage.decrementSectionFileCount(fileNameParts[1]);
            System.out.println("Failed to store request :" + e.getMessage());

        } catch (IOException e) {
            storage.decrementSectionFileCount(fileNameParts[1]);
            System.out.println("Failed to store request :" + e.getMessage());
        }

    }

    class SectionBasedDiskStorage {
        private Deque<String> sectionNames = new LinkedList<>();

        private Map<String, AtomicInteger> sectionFileCountMap = new HashMap<>();
        private int maxFilesPerSection = 2000;
        private int maxSectionCount = 100;
        private String storageBaseDir = System.getProperty("java.io.tmpdir");

        private final Object sectionLock = new Object();

        public SectionBasedDiskStorage() {
            File dir = new File(storageBaseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }

        public String[] apply4Filename(MMSDeliveryRequest request) {
            String sectionName;
            int iFileCount;
            boolean need2RemoveSection = false;
            String[] fileName = new String[2];

            synchronized (sectionLock) {
                sectionName = this.getSectionName();
                AtomicInteger fileCount;
                fileCount = sectionFileCountMap.get(sectionName);
                iFileCount = fileCount.get();

                // 当前存储子目录已满
                if (iFileCount >= maxFilesPerSection) {
                    if (sectionNames.size() >= maxSectionCount) {
                        need2RemoveSection = true;
                    }

                    sectionName = this.makeNewSectionDir();
                    fileCount = sectionFileCountMap.get(sectionName);
                }
                iFileCount = fileCount.addAndGet(1);
            }

            fileName[0] = storageBaseDir + "/" + sectionName + "/" + new DecimalFormat("0000").format(iFileCount) + "-" + request.getTimeStamp().getTime() / 1000 + "-" + request.getExpiry();
            fileName[1] = sectionName;

            if (need2RemoveSection) {
                //删除最老的存储子目录
                String oldestSectionName = sectionNames.removeFirst();
                this.removeSection(oldestSectionName);
            }
            return fileName;
        }

        private boolean removeSection(String sectionName) {
            boolean result = true;
            File dir = new File(storageBaseDir + "/" + sectionName);
            for (File file : dir.listFiles()) {
                result = result && file.delete();
            }
            result = result && dir.delete();
            return result;
        }

        private String makeNewSectionDir() {
            String sectionName;
            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
            sectionName = sdf.format(new Date());
            File dir = new File(storageBaseDir + "/" + sectionName);
            if (dir.mkdir()) {
                sectionNames.addLast(sectionName);
                sectionFileCountMap.put(sectionName, new AtomicInteger(0));
            } else {
                throw new RuntimeException("Cannot create section dir " + sectionName);
            }
            return sectionName;
        }

        private String getSectionName() {
            String sectionName;
            if (sectionNames.isEmpty()) {
                sectionName = this.makeNewSectionDir();
            } else {
                sectionName = sectionNames.getLast();
            }
            return sectionName;
        }

        public void decrementSectionFileCount(String sectionName) {
            AtomicInteger fileCount = sectionFileCountMap.get(sectionName);
            if (null != fileCount) {
                fileCount.decrementAndGet();
            }
        }

    }
}
