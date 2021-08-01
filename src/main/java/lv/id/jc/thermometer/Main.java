package lv.id.jc.thermometer;

import lombok.val;
import lv.id.jc.thermometer.format.ScaleFormatter;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;

public class Main {
    private static final String LINE = "-------------+--------------------------------+%n";
    private static final Substance substance = new Substance();

    public static void main(String[] args) throws InterruptedException {
        val substance = new Substance();
        val recorder = new Recorder(substance);
        val laboratory = new Laboratory(substance, recorder);
        val scheduler = Executors.newScheduledThreadPool(3);
        scheduler.scheduleAtFixedRate(substance, 500, 1000, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(recorder, 1, 5, TimeUnit.SECONDS);
        val cc = new Thread(laboratory);
        cc.start();
        cc.join();

        scheduler.shutdown();
        scheduler.shutdownNow();
        System.out.println("Goodbye!");
    }

    static void printGraph() {
        val thermometer = thermometer();
        val chartFormatter = new ScaleFormatter(43);
        val chart = chartFormatter.format(thermometer);
        System.out.printf("%.1s %s%n", thermometer, chart);
    }

    static Thermometer thermometer() {
        return Thermometer.of(substance.getTemperature());
    }

    static void printCard() {
        System.out.println(thermometer());
        System.out.println();

        System.out
                .format("Thermometer: %1s %n", thermometer())
                .format("Thermometer: %-20s %n", thermometer())
                .format("Thermometer: %20S %n", thermometer())
                .format("Thermometer: %-20S %n", thermometer())
                .format("Thermometer: %.0s %n", thermometer())
                .format("Thermometer: %.1s %n", thermometer())
                .format("Thermometer: %.2s %n", thermometer())
                .format("Thermometer: %.3s %n", thermometer())
                .format("Thermometer: %-18.2s %n", thermometer())
                .format("Thermometer: %-18.2s %n", thermometer())
                .format("Thermometer: %18.2s %n", thermometer())
                .format("Thermometer: %18.2s %n", thermometer())
                .format("Thermometer: %18.2S %n", thermometer())
                .format("Thermometer: %18.2S %n", thermometer())
        ;

        testFormat("%.2s, ");
    }

    static void testFormat(final String format) {
        DoubleStream
                .of(51, 42.682, 24.974, -12.863, -31.098, -50.001)
                .mapToObj(Thermometer::of)
                .forEach(thermometer -> System.out.format(format, thermometer));
    }
}
