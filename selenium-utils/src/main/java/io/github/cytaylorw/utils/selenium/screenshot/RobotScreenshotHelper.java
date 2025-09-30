package io.github.cytaylorw.utils.selenium.screenshot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import io.github.cytaylorw.utils.selenium.action.LatencyProfile;
import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.options.ScreenshotOptions;

public class RobotScreenshotHelper extends AbstractScreenshotHelper {

    public RobotScreenshotHelper(WebManager manager, ScreenshotOptions options, LatencyProfile latency) {
    	super(manager, options, latency);
    }
    
    public RobotScreenshotHelper(WebManager manager, LatencyProfile latency) {
        super(manager, manager.getOptions().screenshot(), latency);
    }
    
    public RobotScreenshotHelper(WebManager manager) {
    	super(manager, manager.getOptions().screenshot(), manager.getOptions().robotLatency());
    }
    
    
    public RobotScreenshotHelper(WebManager manager, ScreenshotOptions options) {
    	super(manager, options, manager.getOptions().robotLatency());
    }

    @Override
    public Path capture(String label) {
//        String filename = formatter.apply(label);
//        Path destination = baseFolder.resolve(filename);
//
//        try {
//            Robot robot = new Robot();
//            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//            BufferedImage image = robot.createScreenCapture(screenRect);
//            ImageIO.write(image, "png", destination.toFile());
//        } catch (AWTException | IOException e) {
//            throw new RuntimeException("Screen capture failed: " + e.getMessage(), e);
//        }
//
//        return destination;
        return captureRegion(getDefaultRegion(), label);
    }
    
    // 🖥️ Capture all monitors into one image
    public Path captureFullDesktop(String label) {
        Rectangle fullArea = getFullDesktopBounds();
        return captureRegion(fullArea, label);
    }

    // 🖥️ Capture each monitor separately
    public List<Path> captureEachMonitor(String labelPrefix) {
        List<Path> paths = new ArrayList<>();
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

        for (int i = 0; i < screens.length; i++) {
            Rectangle bounds = screens[i].getDefaultConfiguration().getBounds();
            String label = labelPrefix + "_monitor" + i;
            paths.add(captureRegion(bounds, label));
        }

        return paths;
    }

    // 🖥️ Capture a specific monitor by index
    public Path captureMonitor(int monitorIndex, String label) {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        if (monitorIndex < 0 || monitorIndex >= screens.length) {
            throw new IllegalArgumentException("Invalid monitor index: " + monitorIndex);
        }

        Rectangle bounds = screens[monitorIndex].getDefaultConfiguration().getBounds();
        return captureRegion(bounds, label);
    }

    // 📐 Capture a specific region
    public Path captureRegion(Rectangle region, String label) {
    	sleepBeforeAction();
        String filename = formatter.apply(label);
        Path destination = baseFolder.resolve(filename);

        try {
            BufferedImage image = new Robot().createScreenCapture(region);
            ImageIO.write(image, "png", destination.toFile());
            return destination;
        } catch (AWTException | IOException e) {
            throw new RuntimeException("Failed to capture region: " + e.getMessage(), e);
        }
    }

    // 🧠 Utility: get bounds of all monitors combined
    private Rectangle getFullDesktopBounds() {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (GraphicsDevice screen : screens) {
            Rectangle bounds = screen.getDefaultConfiguration().getBounds();
            minX = Math.min(minX, bounds.x);
            minY = Math.min(minY, bounds.y);
            maxX = Math.max(maxX, bounds.x + bounds.width);
            maxY = Math.max(maxY, bounds.y + bounds.height);
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public int getPrimaryMonitorIndex() {
        GraphicsDevice primary = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

        for (int i = 0; i < screens.length; i++) {
            if (screens[i].equals(primary)) {
                return i;
            }
        }

        throw new IllegalStateException("Primary monitor not found among screen devices.");
    }
    
    public Rectangle getDefaultRegion() {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

        if (screens.length > 1) {
            GraphicsDevice primary = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            return primary.getDefaultConfiguration().getBounds();
        } else {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            return new Rectangle(0, 0, size.width, size.height);
        }
    }

}
