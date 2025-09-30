package io.github.cytaylorw.utils.selenium.action;
import java.util.concurrent.ThreadLocalRandom;

public class LatencyProfile {

    private long baseMillis;
    private int jitterMillis;
    private boolean enabled;

    public LatencyProfile() {
    }
    
    public LatencyProfile(long baseMillis, int jitterMillis, boolean enabled) {
        this.baseMillis = baseMillis;
        this.jitterMillis = jitterMillis;
        this.enabled = enabled;
    }

    public void apply() {
        if (!enabled) return;

        long delay = baseMillis;
        if (jitterMillis > 0) {
            int jitter = ThreadLocalRandom.current().nextInt(-jitterMillis, jitterMillis + 1);
            delay += jitter;
        }

        try {
            Thread.sleep(Math.max(0, delay));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    // Presets
    public static LatencyProfile examing() {
        return new LatencyProfile(5000, 1000, true);
    }
    
    public static LatencyProfile human() {
        return new LatencyProfile(300, 100, true);
    }

    public static LatencyProfile robotic() {
        return new LatencyProfile(50, 0, true);
    }

    public static LatencyProfile none() {
        return new LatencyProfile(0, 0, false);
    }
    
    // 🆕 New latency tiers
    public static LatencyProfile low() {
        return new LatencyProfile(100, 20, true);
    }

    public static LatencyProfile medium() {
        return new LatencyProfile(500, 150, true);
    }

    public static LatencyProfile high() {
        return new LatencyProfile(1000, 300, true);
    }

}
