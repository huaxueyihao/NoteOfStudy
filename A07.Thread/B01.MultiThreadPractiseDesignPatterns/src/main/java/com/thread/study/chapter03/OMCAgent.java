package com.thread.study.chapter03;

public class OMCAgent extends Thread {
    @Override
    public void run() {

        boolean isTableModificationMsg = false;
        String updatedTableName = null;

        while (true){
            if(isTableModificationMsg){
                if("MMSCInfo".equals(updatedTableName)){
                    MMSCRouter.setInstance(new MMSCRouter());
                }
            }
        }

    }
}
