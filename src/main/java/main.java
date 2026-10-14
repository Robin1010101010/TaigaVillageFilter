import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Util util = new Util();
        util.writeToFile("", "logs/main.log");

        final Logger LOGGER = LogManager.getLogger();

        final int numberOfThreads = Runtime.getRuntime().availableProcessors();
        LOGGER.info("number of threads: " + numberOfThreads);

        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(
                    () -> new TaigaVillageFinder().findSeeds(LOGGER)
                    );
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }
    }
}
