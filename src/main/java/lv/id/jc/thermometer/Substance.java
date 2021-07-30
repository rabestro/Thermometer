package lv.id.jc.thermometer;

import lombok.Getter;

import java.util.Random;

@Getter
public class Substance {
    private static final Random random = new Random();
    private static final double MIN_TEMPERATURE = -60;
    private static final double MAX_TEMPERATURE = 100;
    private static final double NORMAL_TEMPERATURE = 0;
    private static final double MAX_DELTA = 20;

    private double temperature;

    public Substance() {
        this.temperature = NORMAL_TEMPERATURE;
    }

    private static double getRandomTemperature() {
        return random.nextDouble() * Thermometer.State.Maximum.value * 2.2 + Thermometer.State.Minimum.value * 1.1;
    }

    public void waitSomeTime() {
        temperature += MAX_DELTA * (random.nextDouble() - 0.5);
        temperature = Math.max(MIN_TEMPERATURE, temperature);
        temperature = Math.min(MAX_TEMPERATURE, temperature);
    }
}
