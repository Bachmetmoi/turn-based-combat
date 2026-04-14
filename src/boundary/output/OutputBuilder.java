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

    private String color(String text, String format) {
        return format + text + palette.reset();
    }

    public OutputBuilder repeat(String s, int count) {
        buffer.append(s.repeat(Math.max(0, count)));
        return this;
    }

    public OutputBuilder repeat(String s, int count, String color) {
        buffer.append(color(s.repeat(Math.max(0, count)), color));
        return this;
    }

    public ColourPalette getPalette() {
        return palette;
    }

    // --- Stateful Buffer Management ---

    public OutputBuilder setColour(String color) {
        this.currentColour = color;
        return this;
    }

    public OutputBuilder resetColour() {
        this.currentColour = palette.reset();
        return this;
    }

    public OutputBuilder append(String text) {
        buffer.append(color(text, currentColour));
        return this;
    }

    public OutputBuilder append(String text, String color) {
        buffer.append(color(text, color));
        return this;
    }

    public OutputBuilder appendLine(String text) {
        return append(text).newLine();
    }

    public OutputBuilder appendLine(String text, String color) {
        return append(text, color).newLine();
    }

    public OutputBuilder bold(String text, String color) {
        buffer.append(color(palette.bold() + text, color));
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

    public OutputBuilder sectionTitle(String title, String titleColor) {
        buffer.append(color(palette.bold() + title, titleColor));
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
