package lv.id.jc.thermometer.format;

import lombok.val;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.stream.DoubleStream;

public class ScaleFormatter extends Format {
    private static final int MINIMUM_MARKS = 10;
    private static final int SPECIAL_MARKS = 3;
    private final int width;

    public ScaleFormatter(final int width) {
        this.width = width;
    }

    @Override
    public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) {
        val scale = (Scale) obj;
        val marks = Math.max(MINIMUM_MARKS, width - SPECIAL_MARKS);
        toAppendTo.append('[');
        val delta = (scale.getMaximum() - scale.getMinimum()) / marks;
        DoubleStream
                .iterate(scale.getMinimum(), temperature -> temperature + delta)
                .limit(marks)
                .forEach(temperature -> toAppendTo.append(temperature < scale.getValue() ? 'o' : '-'));
        toAppendTo.insert(marks / 2 + 1, '|');
        toAppendTo.append(']');
        return toAppendTo;
    }

    @Override
    public Object parseObject(final String source, final ParsePosition pos) {
        return null;
    }
}
