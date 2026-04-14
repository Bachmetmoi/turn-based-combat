package boundary.output.colours;

public class ClassicColourPalette implements ColourPalette {
    @Override public String getName() { return "Classic"; }
    @Override public String getDescription() { return "Balanced high-contrast colours."; }

    @Override public String reset() { return "\u001B[0m"; }
    @Override public String bold() { return "\u001B[1m"; }

    @Override public String primary() { return "\u001B[96m"; } // Bright Cyan
    @Override public String secondary() { return "\u001B[92m"; } // Bright Green
    @Override public String accent() { return "\u001B[93m"; } // Bright Yellow

    @Override public String success() { return "\u001B[32m"; } // Green
    @Override public String danger() { return "\u001B[31m"; } // Red
    @Override public String warning() { return "\u001B[33m"; } // Yellow
    @Override public String info() { return "\u001B[34m"; } // Blue
    @Override public String neutral() { return "\u001B[37m"; } // White

    @Override public String player() { return "\u001B[94m"; } // Bright Blue
    @Override public String enemy() { return "\u001B[91m"; } // Bright Red
    @Override public String boss() { return "\u001B[95m"; } // Bright Purple

    @Override public String high() { return "\u001B[92m"; } // Bright Green
    @Override public String medium() { return "\u001B[93m"; } // Bright Yellow
    @Override public String low() { return "\u001B[91m"; } // Bright Red

    @Override public String divider() { return "\u001B[96m"; } // Bright Cyan
    @Override public String softDivider() { return "\u001B[90m"; } // Bright Black (Gray)
}
