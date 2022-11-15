package lab1_help;

/**
 * Запустите пять своих потоков путем наследования от класса Thread, которые выведут на экран строку «Привет!
 * Я поток унаследован от класса Thread», и уснуть на 5 секунд с помощью метода sleep(…).
 * Запустите еще пять своих потоков путем имплементации интерфейса Runnable, которые выведут на экран строку «Привет!
 * Я поток имплементирующий интерфейс Runnable», и уснуть на 10 секунд с помощью метода sleep(…).
 * Главный поток приложения должен вывести на экран строку «Главный поток завершен» только после доработки
 * последнего из десяти запущенных потоков. Достичь этого используя метод join(…).
 */
public class Main {
    public static void main(String[] args) {
        //Запускаем 5 унаследованных от Thread потока
        for (int i = 0; i < 5; i++) {
            Thread thread = new ExtendingThread();
            thread.start();
            try {
                //Здесь мы ожидаем завершение предыдущего потока через метод join()
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread has been interrupted");
            }
        }

        //Здесь запускаем в цикле 5 потоков, реализующих Runnable
        //В конструктор базового Thread передается наша реализация интерфейса Runnable
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new ImplementingThread());
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread has been interrupted");
            }
        }

        System.out.println("Главный поток завершен");
    }
}

/**
 * Объявляем свой поток, наследуя от базового Thread. Нужно
 * просто переопределить метод run(), запускающий логику потока
 */
class ExtendingThread extends Thread {
    public void run() {
        System.out.println("Я поток унаследован от класса Thread");
        try {
            //Поток засыпает на 5000 мс = 5 сек
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println("Thread has been interrupted");
        }
    }
}

/**
 * Здесь тоже объявляем свой поток, но имплементируя интерфейс Runnable.
 * Всё точно также - нужно реализовать метод run().
 */
class ImplementingThread implements Runnable {
    public void run() {
        System.out.println("Я поток имплементирующий интерфейс Runnable");
        try {
            //Засыпаем на 10 сек
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.err.println("Thread has been interrupted");
        }
    }
}
