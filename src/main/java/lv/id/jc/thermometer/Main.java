package lv.id.jc.thermometer;

public class Main {
    public static void main(String[] args) {
        final var thermometer = Thermometer.of(23.19);

        System.out.println(thermometer);
        System.out.println();

        System.out
                .printf("Scale: %f%n", thermometer.getScale())
                .printf("State: %s%n", thermometer.getState())
                .printf("Thermometer: %s%n", thermometer)
                .printf("Thermometer: %4.1f° (%s)%n", thermometer.getScale(), thermometer.getState());
    }
}
