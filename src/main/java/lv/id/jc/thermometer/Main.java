package lv.id.jc.thermometer;

public class Main {
    private static final String LINE = "-------------+--------------------------------+%n";
    private static final Substance substance = new Substance();

    public static void main(String[] args) {
        printCard();
    }

    static Thermometer thermometer() {
        substance.waitSomeTime();
        return Thermometer.of(substance.getTemperature());
    }

    static void printCard() {
        System.out.println(thermometer());
        System.out.println();

        System.out
                .format("Thermometer: %20s %n", thermometer())
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
    }
}
