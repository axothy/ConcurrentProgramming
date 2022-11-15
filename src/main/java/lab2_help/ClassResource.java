package lab2_help;

/**
 * Создать общий класс-ресурс с единственным публичным полем – массивом целых чисел.
 * Создать один объект класса-ресурса, в программе не должно быть больше одного объекта класса-ресурса.
 * Сделать три класса, которые реализуют интерфейс Runnable и принимают в своих конструкторах
 * этот объект класса-ресурса. Первый класс должен увеличить значение всех чисел в массиве на единицу.
 * Второй класс должен найти произведение всех чисел в массиве и заменить каждое число в массиве на это произведение.
 * Третий класс должен найти среднее арифметическое всех чисел в массиве и вывести его на экран.
 * Синхронизировать доступ к переменной с массивом в общем классе-ресурсе с помощью оператора synchronized.
 **/

public class ClassResource {
    /**
     * Наш массив целых чисел будет мутируемым, а значит он должен быть безопасно опубликован.
     * Безопасную публикацию объекта, при которой ссылка на него и его члены
     * видна всем потокам в одно и то же время можно провести с помощью
     * статической инициализации. Статическая инициализация выполняется
     * JVM-машиной во время инициализации класса-ресурса, гарантируя безопасную
     * публикацию. Поэтому объявим его как static
     */
    public static int[] intArray = new int[]{1, 2, 3, 4, 5};

    /**
     * Теперь, после публикации, нужно обеспечить видимость изменений
     * этого массива для всех потоков. Поэтому мы объявляе методы класса
     * synchronized. Теперь действия одного потока будут видимы другим потокам.
     * Потоки смогут безопасно совместно использовать этот массив.
     */
    public synchronized void increment() {
        for (int i = 0; i < intArray.length; i++) {
            intArray[i]++;
        }
    }

    public synchronized void product() {
        int product = 1;
        for (int i = 0; i < intArray.length; i++) {
            product *= intArray[i];
        }

        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = product;
        }
    }

    /**
     * Здесь мы не объявляли весь метод synchronized. Мы здесь используем
     * синхронизацию внутренним замком - synchronized(this). Это называется
     * синхронизированный блок. Он гарантирует видимость действий одного потока
     * другими. Например, когда поток А будет выполнять этот синхронизированный
     * блок, а затем поток B будет входить в этот же блок, то значения переменных,
     * которые были видны потоку А до освобождения замка, будут видны потоку
     * B по приобретении замка.
     * <p>
     * Почему я использовал синхронизированный блок, а не объявил
     * весь метод average() целиком? Потому что часть кода, которая находится вне
     * синхронизированного блока (вывод в консоль результата) - достаточно
     * долгая операция и она не влияет на состояние объекта. Это нужно
     * ради производительности.
     *
     * Локальные переменные result, sum тоже можем не синхронизировать - они хранятся
     * только в собственном стеке каждого потока и совместно никогда не будут
     * использоваться
     */
    public void average() {
        double result = 0;
        int sum = 0;

        synchronized (this) {
            for (int i = 0; i < intArray.length; i++) {
                sum += intArray[i];
            }
            result = (double) sum / (double) intArray.length;
        }

        System.out.println(result);
    }

    public static void main(String[] args) {
        ClassResource commonResource = new ClassResource();

        new Thread(new Thread1(commonResource)).start();
        new Thread(new Thread2(commonResource)).start();
        new Thread(new Thread3(commonResource)).start();
    }
}

class Thread1 implements Runnable {
    private ClassResource resource;

    public Thread1(ClassResource resource) {
        this.resource = resource;
    }

    public void run() {
        resource.increment();
    }
}

class Thread2 implements Runnable {
    private ClassResource resource;

    public Thread2(ClassResource resource) {
        this.resource = resource;
    }

    public void run() {
        resource.product();
    }
}

class Thread3 implements Runnable {
    private ClassResource resource;

    public Thread3(ClassResource resource) {
        this.resource = resource;
    }

    public void run() {
        resource.average();
    }
}
