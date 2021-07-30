package lv.id.jc.thermometer;

import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class Main {
    private static final String LINE = "-------------+-------------------------------+%n";
    private static final Substance substance = new Substance();

    public static void main(String[] args) {
        printFormatted();
    }

    static Thermometer thermometer() {
        substance.waitSomeTime();
        return Thermometer.of(substance.getTemperature());
    }

    static void printFormatted() {
        System.out.println(thermometer());

        Stream.of("%-31.0s", "%31.0s", "%-32.1s", "%33.2S", "%30s", "%-30S", "%12.0s", "%-12.0s", "%14.2s", "%-14.2s")
                .flatMap(template -> Stream.concat(
                        Stream.of("%n"), range(0, 5).mapToObj(i -> template + "%n")))
                .forEach(template -> System.out.format(template, thermometer()));
    }

    static void printCard(final Thermometer thermometer) {
        System.out.println(thermometer);
        System.out.println();

        System.out
                .format(LINE)
                .format("Thermometer: | %30s |%n", thermometer)
                .format("Thermometer: | %-30s |%n", thermometer)
                .format("Thermometer: | %30S |%n", thermometer)
                .format("Thermometer: | %-30S |%n", thermometer)
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
