package lv.id.jc.thermometer;

import java.util.Random;

public class Main {
    private static final String LINE = "-------------+-------------------------------+%n";
    private static final Random random = new Random();

    static double getRandomTemperature() {
        return random.nextDouble() * Thermometer.State.Maximum.value * 2.2 + Thermometer.State.Minimum.value * 1.1;
    }

    public static void main(String[] args) {
        final var thermometer = Thermometer.of(getRandomTemperature());

        System.out.println(thermometer);
        System.out.println();

        System.out
                .format("Scale: %f%n", thermometer.getScale())
                .format("State: %s%n", thermometer.getState())
                .format(LINE)
                .format("Thermometer: | %30s |%n", thermometer)
                .format("Thermometer: | %-30s |%n", thermometer)
                .format(LINE)
                .format("Thermometer: | %+6.0f° %-21s |%n", thermometer.getScale(), "(" + thermometer.getState() + ")")
                .format("Thermometer: | %+6.1f° %-21s |%n", thermometer.getScale(), "(" + thermometer.getState() + ")")
                .format("Thermometer: | %+6.2f° %-21s |%n", thermometer.getScale(), "(" + thermometer.getState() + ")")
                .format("Thermometer: | %+6.2f° %21s |%n", thermometer.getScale(), "(" + thermometer.getState() + ")")
                .format(LINE)
                .format("Thermometer: %+3.0f° (%s)%n", thermometer.getScale(), thermometer.getState().name().charAt(0))
                .format("Thermometer: %+3.0f°%n", thermometer.getScale())
                .format("Thermometer: %#+3.0f°%n", thermometer.getScale())
                .format("Thermometer: %+4.1f°%n", thermometer.getScale())
        ;
    }
}
