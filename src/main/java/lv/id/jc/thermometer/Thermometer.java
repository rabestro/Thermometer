package lv.id.jc.thermometer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Formatter;
import java.util.stream.DoubleStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
public class Thermometer implements Formattable {
    private Thermometer(final double value, final State state) {
        this.value = value;
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

    private final double value;
    private final State state;

    public static Thermometer of(final double temperature) {
        val value = min(State.Maximum.value, max(State.Minimum.value, temperature));
        return new Thermometer(value, State.of(temperature));
    }

    public static Thermometer parse(final String temperature) {
        return Thermometer.of(Double.parseDouble(temperature));
    }

    @Override
    public String toString() {
        return String.format("%+5.1f°C %s", value, state);
    }

    @Override
    public void formatTo(final Formatter formatter, final int flags, final int width, final int precision) {
        val isUpperCase = (FormattableFlags.UPPERCASE & flags) > 0;
        val isLeftJustify = (FormattableFlags.LEFT_JUSTIFY & flags) > 0;
        val isAlternate = (FormattableFlags.ALTERNATE & flags) > 0;
        final String output;

        if (precision == -1) {
            val stateName = isAlternate ? state.name().toLowerCase() : state.name();
            val stateTemplate = "%" + (width > 0 ? "." + width : "") + "s";
            output = String.format(stateTemplate, stateName);
        } else {
            val degreeWidth = precision == 0 ? 3 : 4 + precision;
            val degreeValue = isAlternate ? value * 1.8 + 32 : value;
            val degreeSymbol = isAlternate ? "F" : "C";
            val degreeTemplate = "%1$+" + degreeWidth + "." + precision + "f°" + degreeSymbol;
            output = String.format(degreeTemplate, degreeValue);
        }
        val template = "%"
                + (isLeftJustify ? "-" : "")
                + (width > 0 ? String.valueOf(width) : "")
                + (isUpperCase ? "S" : "s");

        formatter.format(template, output);
    }

    public String getGraphScale(final int width) {
        val minimumMarks = 10;
        val specialMarks = 3;
        val marks = Math.max(minimumMarks, width - specialMarks);
        val sb = new StringBuilder(marks + specialMarks).append('[');
        val delta = (State.Maximum.value - State.Minimum.value) / marks;
        DoubleStream
                .iterate(State.Minimum.value, temperature -> temperature + delta)
                .limit(marks)
                .forEach(temperature -> sb.append(temperature < this.value ? 'o' : '-'));
        sb.insert(marks / 2 + 1, '|');
        sb.append(']');
        return sb.toString();
    }
}
