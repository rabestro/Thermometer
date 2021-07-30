# How to use Formattable interface

## class Thermometer

```java
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
public class Thermometer {
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
}

```

Sample output without implementation of toString method.

### Code

```java
final var thermometer = Thermometer.of(23.19);

System.out.println(thermometer);
System.out.println();

System.out
        .printf("Scale: %f%n", thermometer.getScale())
        .printf("State: %s%n", thermometer.getState())
        .printf("Thermometer: %s%n", thermometer)
        .printf("Thermometer: %4.1f° (%s)%n", thermometer.getScale(), thermometer.getState());
```

### Output

```text
lv.id.jc.thermometer.Thermometer@3fee733d

Scale: 23.190000
State: Normal
Thermometer: lv.id.jc.thermometer.Thermometer@3fee733d
Thermometer: 23.2° (Normal)
```

## Method toString()

```java
@Override
public String toString() {
    return String.format("%+6.1f° (%s)", scale, state);
}
```

### Code
```java
System.out.println(thermometer);
System.out.println();

System.out
        .format("Thermometer: | %30s |%n", thermometer)
        .format("Thermometer: | %-30s |%n", thermometer)
```

### Output
```text
 -29.8° (Normal)

Thermometer: |               -29.8° (Normal) |
Thermometer: |  -29.8° (Normal)              |
```

We still do not have enough options to format output. No precision. No control what to output.
What we desire is like this different formats:
```text
Thermometer: +36.565129°
Thermometer: High
Thermometer: +37° (H)
Thermometer: +37°
Thermometer: +36.6°
```