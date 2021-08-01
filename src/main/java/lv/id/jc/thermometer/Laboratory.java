package lv.id.jc.thermometer;

import lombok.AllArgsConstructor;

import java.util.Scanner;
@AllArgsConstructor
public class Laboratory implements Runnable {
    private static final Scanner scanner = new Scanner(System.in);
    private final Substance substance;
    private final Recorder recorder;


    @Override
    public void run() {
        System.out.println("Welcome to JetBrains Research Center!");
        System.out.println();

        while (true) {
            System.out.print("cmd > ");
            final var command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "help" -> printHelp();
                case "exit" -> {
                    return;
                }
                case "logs" -> recorder.printLog();
                case "graph" -> recorder.printGraph();
                case "temp" -> {
                    final var thermometer = Thermometer.of(substance.getTemperature());
                    System.out.printf("Temperature is %-#7s: %<.1s / %<#.1s.%n", thermometer);
                }
                default -> System.out.println("Unknown command. Type 'HELP' for list of available command");
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                help - get this help
                temp - measure the temperature of the substance
                exit - leave the laboratory
                """);
    }
}
