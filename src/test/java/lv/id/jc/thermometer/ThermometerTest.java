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
        assertSame(expectedState, thermometer.getState());
        assertEquals(expectedScale, thermometer.getScale());
    }

    @ParameterizedTest(name = "Temperature: {0}°C, Expected = {1}")
    @CsvFileSource(resources = "/to-string.csv", numLinesToSkip = 1, encoding = "windows-1252")
    void testToString(final double temperature, final String expected) {
        final var thermometer = Thermometer.of(temperature);
        final var actual = thermometer.toString();
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "Temperature: {1}°, \"{0}\" = {2}")
    @CsvFileSource(resources = "/format-value-tostring.csv", numLinesToSkip = 1, encoding = "windows-1252")
    void testToStringPlusFormat(final String format, final double temperature, final String expected) {
        final var thermometer = Thermometer.of(temperature);
        final var toString = String.valueOf(thermometer);
        final var actual = String.format(format, toString);
        assertEquals(expected, actual);
    }

}