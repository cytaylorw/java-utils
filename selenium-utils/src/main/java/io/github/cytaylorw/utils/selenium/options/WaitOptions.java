package io.github.cytaylorw.utils.selenium.options;

import java.time.Duration;

public class WaitOptions {

    private Duration timeout = Duration.ofSeconds(10);
    private Duration pollingInterval = Duration.ofMillis(500);
    private boolean ignoreStale = true;

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(Duration pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public boolean isIgnoreStale() {
        return ignoreStale;
    }

    public void setIgnoreStale(boolean ignoreStale) {
        this.ignoreStale = ignoreStale;
    }
}
