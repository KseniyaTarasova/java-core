package by.innowise.skynet;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
public class Factory {
    private static final int MAX_PARTS_TO_CREATE = 11;
    private static final int MAX_PARTS_TO_GET = 6;
    private static AtomicInteger workDays = new AtomicInteger(0);
    private final Random random = new Random();

    private final BlockingQueue<RobotParts> createdParts = new ArrayBlockingQueue<>(10);
    private final RobotParts[] robotParts = RobotParts.values();

    public void createParts() {
        log.info("Factory is working...");
        while (isWorking()) {
            createdParts.clear();

            int partsToCreate = random.nextInt(MAX_PARTS_TO_CREATE);
            for (int i = 0; i < partsToCreate; i++) {
                createdParts.add(robotParts[random.nextInt(robotParts.length)]);
            }

            synchronized (this) {
                notifyAll();
            }

            workDays.incrementAndGet();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error("Thread was interrupted while sleeping", e);
            }
        }
        log.info("Factory stopped working...");
    }

    public List<RobotParts> getParts() {
        synchronized (this) {
            while (createdParts.isEmpty() && isWorking()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    log.error("Thread was interrupted while waiting", e);
                }
            }

            int partsToGet = Math.min(random.nextInt(MAX_PARTS_TO_GET), createdParts.size());
            List<RobotParts> parts = new ArrayList<>();
            for (int i = 0; i < partsToGet; i++) {
                parts.add(createdParts.poll());
            }

            return parts;
        }
    }

    public boolean isWorking() {
        return workDays.get() < 100;
    }
}
