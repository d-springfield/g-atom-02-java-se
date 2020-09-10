package multithreading;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int d = 5_000;
            try {
                System.out.println("Асинхронный привет!");
                Thread.sleep(d);
                System.out.println("Асинхронный пока!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread secondThread = new Thread(runnable);
        int interval = 1_000;
        secondThread.start();
        while (secondThread.isAlive()) {
            System.out.println("Работает основная программа");
            Thread.sleep(interval);
        }

        System.out.println("Финиш");
    }
}
