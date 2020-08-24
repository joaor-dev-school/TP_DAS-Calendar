package tp.das.Service;

import tp.das.Model.Timer.TimerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimerService {

    private static final int SECOND_MS = 1000;
    private static TimerService timerService;
    private static Long countListener;
    private Map<Long, TimerListener> listenersMap;

    private TimerService() {
        this.listenersMap = new HashMap<>();

        final Runnable timerRunnable = () -> {
            int errorCount = 0;
            while (errorCount < 3) {
                try {
                    final long currentTimeMillis = System.currentTimeMillis();
                    for (TimerListener l : this.getListeners()) {
                        l.timerEventCallback(currentTimeMillis);
                    }
                    Thread.sleep(SECOND_MS);
                    errorCount = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    errorCount++;
                }
            }
        };
        timerRunnable.run();
    }

    public static TimerService getInstance() {
        if (timerService == null) {
            countListener = 0L;
            return timerService = new TimerService();
        }
        return timerService;
    }

    public static TimerService getTimerService() {
        return timerService;
    }

    public Long subscribe(TimerListener listener) {
        timerService.addListener(countListener, listener);
        return countListener++;
    }

    public boolean unsubscribe(Long subscriptionId) {
        return timerService.removeListener(countListener);
    }

    private synchronized void addListener(Long subscriptionId, TimerListener listener) {
        this.listenersMap.put(subscriptionId, listener);
    }

    private synchronized boolean removeListener(Long subscriptionId) {
        return this.listenersMap.remove(subscriptionId) != null;
    }

    private synchronized List<TimerListener> getListeners() {
        return new ArrayList<>(this.listenersMap.values());
    }
}
