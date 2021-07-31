package lv.id.jc.thermometer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Formatter;
import java.util.stream.DoubleStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
public class Thermometer implements Formattable {
    private Thermometer(final double scale, final State state) {
        this.scale = scale;
        this.state = state;
    }

    @AllArgsConstructor
    enum State {
        Maximum(50),
        High(30),
        Normal(0),
        Low(-30),
        Minimum(-50);
        final double value;

        static State of(final double temperature) {
            return temperature >= Maximum.value ? Maximum
                    : temperature <= Minimum.value ? Minimum
                    : temperature >= High.value ? High
                    : temperature <= Low.value ? Low
                    : Normal;
        }
    }

    private final double scale;
    private final State state;

    public static Thermometer of(final double temperature) {
        final var scale = min(State.Maximum.value, max(State.Minimum.value, temperature));
        return new Thermometer(scale, State.of(temperature));
    }

    public static Thermometer parse(final String temperature) {
        return Thermometer.of(Double.parseDouble(temperature));
    }

    @Override
    public String toString() {
        return String.format("%+5.1f° %s", scale, state);
    }

    @Override
    public void formatTo(final Formatter formatter, final int flags, final int width, final int precision) {
        final var isUpperCase = (FormattableFlags.UPPERCASE & flags) > 0;
        final var isLeftJustify = (FormattableFlags.LEFT_JUSTIFY & flags) > 0;
        final var isAlternate = (FormattableFlags.ALTERNATE & flags) > 0;

        final String output;
        if (precision == -1) {
            final var stateName = isAlternate ? state.name() : state.name().toLowerCase();
            final var stateTemplate = "%" + (width > 0 ? "." + width : "") + "s";
            output = String.format(stateTemplate, stateName);
        } else {
            final var degreeWidth = precision == 0 ? 3 : 4 + precision;
            final var degreeSymbol = isAlternate ? "F" : "C";
            final var degreeTemplate = "%1$+" + degreeWidth + "." + precision + "f°" + degreeSymbol;
            output = String.format(degreeTemplate, isAlternate ? scale * 1.8 + 32 : scale);
        }
        final var template = "%"
                + (isLeftJustify ? "-" : "")
                + (width > 0 ? String.valueOf(width) : "")
                + (isUpperCase ? "S" : "s");

        formatter.format(template, output);
    }

    public String getGraphScale(final int width) {
        final var minimumMarks = 10;
        final var specialMarks = 3;
        final int marks = Math.max(minimumMarks, width - specialMarks);
        final var sb = new StringBuilder(marks + specialMarks).append('[');
        final var delta = (State.Maximum.value - State.Minimum.value) / marks;
        DoubleStream
                .iterate(State.Minimum.value, temperature -> temperature + delta)
                .limit(marks)
                .forEach(temperature -> sb.append(temperature < this.scale ? 'o' : '-'));
        sb.insert(marks / 2 + 1, '|');
        sb.append(']');
        return sb.toString();
    }
}
