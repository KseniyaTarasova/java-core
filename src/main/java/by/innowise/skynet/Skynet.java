package by.innowise.skynet;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Skynet {
    public static void main(String[] args) {
        Factory factory = new Factory();
        Faction world = new Faction("World", factory);
        Faction wednesday = new Faction("Wednesday", factory);

        ExecutorService executors = Executors.newFixedThreadPool(3);
        executors.execute(factory::createParts);
        executors.execute(world);
        executors.execute(wednesday);

        executors.shutdown();

        try {
            if (!executors.awaitTermination(5, TimeUnit.MINUTES)) {
                log.info("Executor did not terminate in 5 minutes, shutting down now");
                executors.shutdownNow();
            }
        } catch (InterruptedException e) {
            executors.shutdownNow();
        }

        log.info("World created {} robots", world.getRobots());
        log.info("Wednesday created {} robots", wednesday.getRobots());

        log.info("{} has the strongest army", world.getRobots() > wednesday.getRobots() ? "World" : "Wednesday");
    }
}
