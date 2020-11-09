package com.taole.framework.lock;

public class ResourceLock {
   public static  int oo=1;
   final static SysnLockPool rtLockPool = new SysnLockPool();

    private ResourceLock() {
    }

    public static SysnKeyLock accquireWrite(String resourceID) {
        SysnKeyLock lock = rtLockPool.getLock(resourceID);
        lock.lock();
        return lock;
    }

    public static void releaseWrite(String resourceID) {
        SysnKeyLock lock = rtLockPool.getLock(resourceID);
        rtLockPool.releaseLock(lock);
    }


    public static void main(String[] args) {
        final String resourceID = "kkkkk";
        final String resourceID_1 = "kkkkk1";
        for (int i = 1; i <= 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SysnKeyLock lock = accquireWrite(resourceID);
                    System.out.println("l1=="+lock);
                    System.out.println("T1执行lockId:=" + Thread.currentThread().getId()+"oo=="+oo++);

                    releaseWrite(resourceID);
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    SysnKeyLock lock = accquireWrite(resourceID_1);
                    System.out.println("l2=="+lock);
                    System.out.println("T2执行lockId:=" + Thread.currentThread().getId()+"oo=="+oo++);
                    releaseWrite(resourceID_1);
                }
            }).start();

        }

    }
}
