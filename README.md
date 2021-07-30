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

## Implementation of interface Formattable

```java
@Override
public void formatTo(final Formatter formatter, final int flags, final int width, final int precision) {
    final var isUpperCase = (FormattableFlags.UPPERCASE & flags) > 0;
    final var isLeftJustify = (FormattableFlags.LEFT_JUSTIFY & flags) > 0;

    final var scaleTemplate = precision == -1 ? "" : " %1$+" + (4 + precision) + "." + precision + "f° ";
    final var stateTemplate = precision == -1 || width > 14 + precision ? "%2$s" : "";
    final var template = isLeftJustify ? scaleTemplate + stateTemplate : stateTemplate + scaleTemplate;

    final var output = String.format(template, scale, state);

    final var justify = "%"
            + (isLeftJustify ? "-" : "")
            + (width > 0 ? String.valueOf(width) : "")
            + (isUpperCase ? "S" : "s");

    formatter.format(justify, output);
}
```
### Code 
```java
System.out
        .format("Thermometer: %20s %n", thermometer())
        .format("Thermometer: %-20s %n", thermometer())
        .format("Thermometer: %20S %n", thermometer())
        .format("Thermometer: %-20S %n", thermometer())
        .format("Thermometer: %.0s %n", thermometer())
        .format("Thermometer: %.1s %n", thermometer())
        .format("Thermometer: %.2s %n", thermometer())
        .format("Thermometer: %.3s %n", thermometer())
        .format("Thermometer: %-18.2s %n", thermometer())
        .format("Thermometer: %-18.2s %n", thermometer())
        .format("Thermometer: %18.2s %n", thermometer())
        .format("Thermometer: %18.2s %n", thermometer())
        .format("Thermometer: %18.2S %n", thermometer())
        .format("Thermometer: %18.2S %n", thermometer())
;
```
### Output

```text
Thermometer:               Normal 
Thermometer: Normal               
Thermometer:               NORMAL 
Thermometer: HIGH                 
Thermometer:   +32°  
Thermometer:  +34.4°  
Thermometer:  +42.96°  
Thermometer:  +34.117°  
Thermometer:  +27.36° Normal   
Thermometer:  +33.00° High     
Thermometer:   Normal +24.94°  
Thermometer:   Normal +17.85°  
Thermometer:   NORMAL +10.36°  
Thermometer:   NORMAL  +4.15° 
```