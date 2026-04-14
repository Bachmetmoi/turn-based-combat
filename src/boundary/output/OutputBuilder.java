package boundary.output;

import boundary.output.colours.ColourPalette;

public class OutputBuilder {
    public static final int PANEL_WIDTH = 72;
    private final ColourPalette palette;
    private final StringBuilder buffer = new StringBuilder();
    private String currentColour;

    public OutputBuilder(ColourPalette palette) {
        this.palette = palette;
        resetColour();
    }

    // --- Formatters ---

    private String colour(String text, String format) {
        return format + text + palette.reset();
    }

    public OutputBuilder repeat(String s, int count) {
        buffer.append(s.repeat(Math.max(0, count)));
        return this;
    }

    public OutputBuilder repeat(String s, int count, String colour) {
        buffer.append(colour(s.repeat(Math.max(0, count)), colour));
        return this;
    }

    public ColourPalette getPalette() {
        return palette;
    }

    // --- Stateful Buffer Management ---

    public OutputBuilder setColour(String colour) {
        this.currentColour = colour;
        return this;
    }

    public OutputBuilder resetColour() {
        this.currentColour = palette.reset();
        return this;
    }

    public OutputBuilder append(String text) {
        buffer.append(colour(text, currentColour));
        return this;
    }

    public OutputBuilder append(String text, String colour) {
        buffer.append(colour(text, colour));
        return this;
    }

    public OutputBuilder appendLine(String text) {
        return append(text).newLine();
    }

    public OutputBuilder appendLine(String text, String colour) {
        return append(text, colour).newLine();
    }

    public OutputBuilder bold(String text, String colour) {
        buffer.append(colour(palette.bold() + text, colour));
        return this;
    }

    public OutputBuilder newLine() {
        buffer.append(System.lineSeparator());
        return this;
    }

    public OutputBuilder divider() {
        repeat("═", PANEL_WIDTH, palette.divider());
        return newLine();
    }

    public OutputBuilder softDivider() {
        repeat("─", PANEL_WIDTH, palette.softDivider());
        return newLine();
    }

    public OutputBuilder sectionTitle(String title, String titlecolour) {
        buffer.append(colour(palette.bold() + title, titlecolour));
        return newLine();
    }

    public void print() {
        System.out.print(buffer.toString());
        clear();
    }

    public void println() {
        System.out.println(buffer.toString());
        clear();
    }

    public void print(String s) {
        buffer.append(s);
        System.out.print(buffer.toString());
        clear();
    }

    public void println(String s) {
        buffer.append(s);
        System.out.println(buffer.toString());
        clear();
    }

    public void clear() {
        buffer.setLength(0);
        resetColour();
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
