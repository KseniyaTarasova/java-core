package by.innowise.skynet;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Faction implements Runnable {
    private Factory factory;
    private String name;
    private int robots;
    private final Map<RobotParts, Integer> parts = new ConcurrentHashMap<>();

    public Faction(String name, Factory factory) {
        this.name = name;
        this.factory = factory;
        Arrays.stream(RobotParts.values()).forEach(part -> parts.put(part, 0));
    }

    @Override
    public void run() {
        while (factory.isWorking()) {
            List<RobotParts> robotParts = factory.getParts();
            robotParts.forEach(part -> parts.merge(part, 1, Integer::sum));

            createRobots();
        }
    }

    private void createRobots() {
        while (canCreateRobot()) {
            Arrays.stream(RobotParts.values())
                    .forEach(part -> parts.computeIfPresent(part, (key, value) -> value - 1));
            robots++;
        }
    }

    private boolean canCreateRobot() {
        return Arrays.stream(RobotParts.values())
                .allMatch(part -> parts.get(part) > 0);
    }
}
