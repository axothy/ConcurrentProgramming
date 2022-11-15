package ch4;

public class PrivateLock {
    private final Object myLock = new Object();
    //Widget widget;

    void someMethod() {
        synchronized (myLock) {
            //use widget...
        }
    }
}
