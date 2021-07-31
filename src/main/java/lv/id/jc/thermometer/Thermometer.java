package lv.id.jc.thermometer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Formatter;

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

    @Override
    public String toString() {
        return String.format("%+5.1f° %s", scale, state);
    }

    @Override
    public void formatTo(final Formatter formatter, final int flags, final int width, final int precision) {
        final var isUpperCase = (FormattableFlags.UPPERCASE & flags) > 0;
        final var isLeftJustify = (FormattableFlags.LEFT_JUSTIFY & flags) > 0;
        final var isAlternate = (FormattableFlags.ALTERNATE & flags) > 0;
        final var scaleTemplate
                = precision == -1 ? ""
                : precision == 0 ? "%1$+3.0f°"
                : "%1$+" + (4 + precision) + "." + precision + "f°";
        final var stateTemplate = precision == -1 || width > 13 + precision ? "%2$s" : "";
        final var delimiter = stateTemplate.isEmpty() || scaleTemplate.isEmpty() ? "" : " ";
        final var template = isLeftJustify
                ? scaleTemplate + delimiter + stateTemplate
                : stateTemplate + delimiter + scaleTemplate;

        final var output = String.format(template, scale, state);
        final var justify = "%"
                + (isLeftJustify ? "-" : "")
                + (width > 0 ? String.valueOf(width) : "")
                + (isUpperCase ? "S" : "s");

        formatter.format(justify, output);
    }
}
