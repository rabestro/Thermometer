package lv.id.jc.thermometer;

import lombok.AllArgsConstructor;
import lombok.val;
import lv.id.jc.thermometer.format.ScaleFormatter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class Recorder implements Runnable {
    private final Substance substance;
    private final Map<Instant, Thermometer> log = new LinkedHashMap<>();

    @Override
    public void run() {
        log.put(Instant.now(), Thermometer.of(substance.getTemperature()));
    }

    public void printLog() {
        log.forEach((time, temp) -> System.out.printf(
                "[%s] Temperature is %-#7s: %<.1s / %<#.1s.%n",
                time.truncatedTo(ChronoUnit.SECONDS), temp));
    }

    public void printGraph() {
        val formatter = new ScaleFormatter(43);
        log.forEach((time, temp) -> System.out.printf("[%s] %.1s %s%n",
                time.truncatedTo(ChronoUnit.MINUTES),
                temp, formatter.format(temp)));
    }
}
