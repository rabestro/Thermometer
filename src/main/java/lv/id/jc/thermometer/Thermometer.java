package lv.id.jc.thermometer;

import lombok.Getter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
public class Thermometer {
    private Thermometer(final double scale, final State state) {
        this.scale = scale;
        this.state = state;
    }

    enum State {Maximum, High, Normal, Low, Minimum}

    private final double scale;
    private final State state;

    public static Thermometer of(final double temperature) {
        final var scale = min(100, max(0, temperature));

        final var state
                = temperature >= 100 ? State.Maximum
                : temperature >= 80 ? State.High
                : temperature > 20 ? State.Normal
                : temperature > 0 ? State.Low
                : State.Minimum;

        return new Thermometer(scale, state);
    }
}
