package lv.id.jc.thermometer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
//    @Disabled
    void getRandomTemperature() {
        Stream.generate(Main::getRandomTemperature).limit(10).forEach(System.out::println);
    }
}