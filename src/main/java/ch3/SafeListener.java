package ch3;

interface Event {
}

interface EventSource {
    void registerListener(EventListener listener);
}

interface EventListener {
    void onEvent(Event e);
}
public class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }


    public static SafeListener newInstance(EventSource source) {
        SafeListener sf = new SafeListener();
        source.registerListener(sf.listener);
        return sf;
    }

    void doSomething(Event e) {
        System.out.println("Something" + e);
    }
}

class TestClass {
    public static void main(String[] args) {
        SafeListener safe = SafeListener.newInstance(new EventSource() {
            @Override
            public void registerListener(EventListener listener) {
                System.out.println("Registered");
            }
        });
    }
}
