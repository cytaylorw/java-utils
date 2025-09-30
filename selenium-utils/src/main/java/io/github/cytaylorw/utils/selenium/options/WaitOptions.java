package io.github.cytaylorw.utils.selenium.options;

import java.time.Duration;

public class WaitOptions {

    private Duration timeout = Duration.ofSeconds(10);
    private Duration pollingInterval = Duration.ofMillis(500);
    private boolean ignoreStale = true;

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Long seconds) {
        this.timeout = Duration.ofSeconds(seconds);
    }

    public Duration getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(Long millis) {
        this.pollingInterval = Duration.ofMillis(500);
    }

    public boolean isIgnoreStale() {
        return ignoreStale;
    }

    public void setIgnoreStale(boolean ignoreStale) {
        this.ignoreStale = ignoreStale;
    }
}
