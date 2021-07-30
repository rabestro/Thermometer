package lv.id.jc.thermometer;

import lombok.Getter;

import java.util.Random;

@Getter
public class Substance {
    private static final Random random = new Random();
    private static final double MIN_TEMPERATURE = -273.15;
    private static final double MAX_TEMPERATURE = 500;
    private static final double NORMAL_TEMPERATURE = 0;

    private double temperature;

    public Substance() {
        this.temperature = getRandomTemperature();
    }

    private static double getRandomTemperature() {
        return random.nextDouble() * Thermometer.State.Maximum.value * 2.2 + Thermometer.State.Minimum.value * 1.1;
    }
}
