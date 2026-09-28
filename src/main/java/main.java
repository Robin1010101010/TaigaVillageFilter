public class main {
    public static void main(String[] args) throws InterruptedException {
        final int numberOfThreads = Runtime.getRuntime().availableProcessors();
        //System.out.println("number of threads: " + numberOfThreads);

        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(
                    () -> new TaigaVillageFinder().findSeeds()
                    );
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }
    }
}
