package boundary.output.colours;

public class CyberpunkColourPalette implements ColourPalette {
    @Override public String getName() { return "Cyberpunk"; }
    @Override public String getDescription() { return "Vibrant neon pink and yellow theme."; }

    @Override public String reset() { return "\u001B[0m"; }
    @Override public String bold() { return "\u001B[1m"; }

    @Override public String primary() { return "\u001B[35m"; } // Magenta
    @Override public String secondary() { return "\u001B[93m"; } // Bright Yellow
    @Override public String accent() { return "\u001B[96m"; } // Bright Cyan

    @Override public String success() { return "\u001B[92m"; } // Bright Green
    @Override public String danger() { return "\u001B[91m"; } // Bright Red
    @Override public String warning() { return "\u001B[93m"; } // Bright Yellow
    @Override public String info() { return "\u001B[94m"; } // Bright Blue
    @Override public String neutral() { return "\u001B[97m"; } // Bright White

    @Override public String player() { return "\u001B[96m"; } // Bright Cyan
    @Override public String enemy() { return "\u001B[35m"; } // Magenta
    @Override public String boss() { return "\u001B[91m"; } // Bright Red

    @Override public String high() { return "\u001B[92m"; } // Bright Green
    @Override public String medium() { return "\u001B[93m"; } // Bright Yellow
    @Override public String low() { return "\u001B[91m"; } // Bright Red

    @Override public String divider() { return "\u001B[93m"; } // Bright Yellow
    @Override public String softDivider() { return "\u001B[35m"; } // Magenta
}
