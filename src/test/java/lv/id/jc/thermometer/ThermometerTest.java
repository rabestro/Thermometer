package lv.id.jc.thermometer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Thermometer class")
class ThermometerTest {

    @DisplayName("must create the object correctly")
    @ParameterizedTest(name = "Temperature: {0}°, Thermometer: {1}° ({2})")
    @CsvFileSource(resources = "/thermometer.csv", numLinesToSkip = 1)
    void of(final double temperature, final double expectedScale, final Thermometer.State expectedState) {
        final var thermometer = Thermometer.of(temperature);
        assertSame(expectedState, thermometer.getState());
        assertEquals(expectedScale, thermometer.getScale());
    }

    @Nested
    @DisplayName("should override toString")
    class ToStringTest {
        @DisplayName("test using toString together with format")
        @ParameterizedTest(name = "Temperature: {0}°C, Expected = {1}")
        @CsvFileSource(resources = "/tostring.csv", numLinesToSkip = 1, encoding = "windows-1252")
        void testToString(final double temperature, final String expected) {
            final var thermometer = Thermometer.of(temperature);
            final var actual = thermometer.toString();
            assertEquals(expected, actual);
        }

        @DisplayName("test using only toString")
        @ParameterizedTest(name = "Temperature: {1}°, \"{0}\" = {2}")
        @CsvFileSource(resources = "/tostring-format.csv", numLinesToSkip = 1, encoding = "windows-1252")
        void testToStringPlusFormat(final String format, final double temperature, final String expected) {
            final var thermometer = Thermometer.of(temperature);
            final var toString = String.valueOf(thermometer);
            final var actual = String.format(format, toString);
            assertEquals(expected, actual);
        }

    }

    @DisplayName("should implement Formattable interface")
    @ParameterizedTest(name = "Format: \"{0}\", Temperature: {1}°, Expected: {2}")
    @CsvFileSource(resources = "/format.csv", numLinesToSkip = 1, encoding = "windows-1252")
    void formatTo(final String format, final double temperature, final String expected) {
        final var thermometer = Thermometer.of(temperature);
        final var actual = String.format(format, thermometer);
        assertEquals(expected, actual);
    }


}