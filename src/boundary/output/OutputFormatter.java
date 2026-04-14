package boundary.output;

public class OutputFormatter {
    public static final int PANEL_WIDTH = 72;
    private final ColorPalette palette;

    public OutputFormatter(ColorPalette palette) {
        this.palette = palette;
    }

    public String color(String text, String format) {
        return format + text + palette.reset();
    }

    public String repeat(String s, int count) {
        return s.repeat(Math.max(0, count));
    }

    public void printDivider() {
        System.out.println(color(repeat("═", PANEL_WIDTH), palette.divider()));
    }

    public void printSoftDivider() {
        System.out.println(color(repeat("─", PANEL_WIDTH), palette.softDivider()));
    }

    public void printSectionTitle(String title, String titleColor) {
        System.out.println(color(palette.bold() + title, titleColor));
    }

    public OutputBuilder builder() {
        return new OutputBuilder(this);
    }

    public ColorPalette getPalette() {
        return palette;
    }
}
