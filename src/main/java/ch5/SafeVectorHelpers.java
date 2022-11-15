package ch5;

import java.util.Vector;

public class SafeVectorHelpers {
    private Vector vector;

    public static Object getLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }

    public static Object deleteLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            return list.remove(lastIndex);
        }
    }

    public void iterate() {
        synchronized (vector) {
            for (int i = 0; i < vector.size(); i++) {
                doSomething(vector.get(i));
            }
        }
    }

    private void doSomething(Object obj) {

    }

    public static void main(String[] args) {
        System.out.println("x");
    }
}
