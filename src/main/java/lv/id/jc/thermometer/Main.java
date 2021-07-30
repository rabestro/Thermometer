package lv.id.jc.thermometer;

public class Main {
    private static final String LINE = "-------------+-------------------------------+%n";

    public static void main(String[] args) {
        final var substance = new Substance();

        final var thermometer = Thermometer.of(substance.getTemperature());

        System.out.println(thermometer);
        System.out.println();

        System.out
                .format(LINE)
                .format("Thermometer: | %30s |%n", thermometer)
                .format("Thermometer: | %-30s |%n", thermometer)
                .format(LINE)
                .format("Thermometer: | %+6.0f° %-21s |%n", thermometer.getScale(), "(" + thermometer.getState() + ")")
                .format("Thermometer: | %+6.1f° %-21s |%n", thermometer.getScale(), "(" + thermometer.getState() + ")")
                .format("Thermometer: | %+6.2f° %-21s |%n", thermometer.getScale(), "(" + thermometer.getState() + ")")
                .format("Thermometer: | %+6.2f° %21s |%n", thermometer.getScale(), "(" + thermometer.getState() + ")")
                .format(LINE)
                .format("Thermometer: %+f°%n", thermometer.getScale())
                .format("Thermometer: %s%n", thermometer.getState())
                .format("Thermometer: %+1.0f° (%s)%n", thermometer.getScale(), thermometer.getState().name().charAt(0))
                .format("Thermometer: %-+1.0f°%n", thermometer.getScale())
                .format("Thermometer: %+4.1f°%n", thermometer.getScale())
        ;
    }
}
