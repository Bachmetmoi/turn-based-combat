package boundary.output.colours;

import control.Registry;

public class ColourPaletteRegistry extends Registry<ColourPalette> {
    private static final ColourPaletteRegistry instance = new ColourPaletteRegistry();

    private ColourPaletteRegistry() {
        register(ClassicColourPalette.class);
        register(SolarizedColourPalette.class);
        register(CyberpunkColourPalette.class);
        register(MidnightColourPalette.class);
    }

    public static ColourPaletteRegistry getInstance() {
        return instance;
    }
}
