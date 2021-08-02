package lv.id.jc.thermometer;

import lv.id.jc.thermometer.format.ScaleFormatter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Thermometer class")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ThermometerTest {

    @DisplayName("must create the object correctly")
    @ParameterizedTest(name = "Temperature: {0}°, Thermometer: {1}° ({2})")
    @CsvFileSource(resources = "/thermometer.csv", numLinesToSkip = 1)
    void of(final double temperature, final double expectedScale, final Thermometer.State expectedState) {
        final var thermometer = Thermometer.of(temperature);
        assertSame(expectedState, thermometer.getState());
        assertEquals(expectedScale, thermometer.getValue());
    }

    @Nested
    @DisplayName("Thermometer::parse")
    class ParseTest {
        @Test
        @DisplayName("when argument is null then throw NullPointerException")
        void parseNull() {
            assertThrows(NullPointerException.class, () -> Thermometer.parse(null));
        }

        @Test
        @DisplayName("when argument is empty then throw NumberFormatException")
        void parseEmpty() {
            assertThrows(NumberFormatException.class, () -> Thermometer.parse(""));
        }
    }

    @Nested
    @DisplayName("should override toString")
    class ToStringTest {
        @DisplayName("test using only toString")
        @ParameterizedTest(name = "Temperature: {0}°C, Expected = {1}")
        @CsvFileSource(resources = "/tostring.csv", numLinesToSkip = 1, encoding = "windows-1252")
        void testToString(final Thermometer thermometer, final String expected) {
            assertEquals(expected, thermometer.toString());
        }

        @DisplayName("test using toString together with format")
        @ParameterizedTest(name = "Temperature: {1}°, \"{0}\" = {2}")
        @CsvFileSource(resources = "/tostring-format.csv", numLinesToSkip = 1, encoding = "windows-1252")
        void testToStringPlusFormat(final String format, final Thermometer thermometer, final String expected) {
            final var actual = format.formatted(String.valueOf(thermometer));
            assertEquals(expected, actual);
        }
    }

    @DisplayName("should implement Formattable interface")
    @ParameterizedTest(name = "Temperature: {0}°, Format: \"{1}\", Expected: {2}")
    @CsvFileSource(resources = "/thermometer-format-to.csv", numLinesToSkip = 1, encoding = "windows-1252")
    void formatTo(final Thermometer thermometer, final String format, final String expected) {
        assertEquals(expected, format.formatted(thermometer));
    }

    @DisplayName("should plot graphic scale")
    @ParameterizedTest(name = "Width: {0}, Temperature: {1}°, Graph: {2}")
    @CsvFileSource(resources = "/graph-scale.csv", numLinesToSkip = 1, encoding = "windows-1252")
    void getGraphScale(final int width, final Thermometer thermometer, final String expected) {
        assertEquals(expected, new ScaleFormatter(width).format(thermometer));
    }

}