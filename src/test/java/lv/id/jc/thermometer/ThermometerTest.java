package lv.id.jc.thermometer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Thermometer")
class ThermometerTest {

    @ParameterizedTest(name = "Temperature: {0}°, Thermometer: {1}° ({2})")
    @CsvFileSource(resources = "/thermometer.csv", numLinesToSkip = 1)
    void of(final double temperature, final double expectedScale, final Thermometer.State expectedState) {
        final var thermometer = Thermometer.of(temperature);
        assertAll(
                () -> assertSame(expectedState, thermometer.getState()),
                () -> assertEquals(expectedScale, thermometer.getScale())
        );
    }
}