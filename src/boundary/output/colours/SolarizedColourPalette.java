package boundary.output.colours;

public class SolarizedColourPalette implements ColourPalette {
    @Override public String getName() { return "Solarized"; }
    @Override public String getDescription() { return "Low contrast eye-friendly theme."; }

    @Override public String reset() { return "\u001B[0m"; }
    @Override public String bold() { return "\u001B[1m"; }

    @Override public String primary() { return "\u001B[36m"; } // Cyan
    @Override public String secondary() { return "\u001B[32m"; } // Green
    @Override public String accent() { return "\u001B[33m"; } // Yellow

    @Override public String success() { return "\u001B[32m"; } // Green
    @Override public String danger() { return "\u001B[31m"; } // Red
    @Override public String warning() { return "\u001B[33m"; } // Yellow
    @Override public String info() { return "\u001B[34m"; } // Blue
    @Override public String neutral() { return "\u001B[37m"; } // White

    @Override public String player() { return "\u001B[34m"; } // Blue
    @Override public String enemy() { return "\u001B[31m"; } // Red
    @Override public String boss() { return "\u001B[35m"; } // Magenta

    @Override public String high() { return "\u001B[32m"; } // Green
    @Override public String medium() { return "\u001B[33m"; } // Yellow
    @Override public String low() { return "\u001B[31m"; } // Red

    @Override public String divider() { return "\u001B[36m"; } // Cyan
    @Override public String softDivider() { return "\u001B[30m"; } // Black
}
