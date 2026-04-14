package boundary.output;

public class OutputBuilder {
    private final StringBuilder buffer = new StringBuilder();
    private final OutputFormatter formatter;

    public OutputBuilder(OutputFormatter formatter) {
        this.formatter = formatter;
    }

    public OutputBuilder append(String text) {
        buffer.append(text);
        return this;
    }

    public OutputBuilder append(String text, String color) {
        buffer.append(formatter.color(text, color));
        return this;
    }

    public OutputBuilder bold(String text, String color) {
        buffer.append(formatter.color(formatter.getPalette().bold() + text, color));
        return this;
    }

    public OutputBuilder newLine() {
        buffer.append(System.lineSeparator());
        return this;
    }

    public OutputBuilder divider() {
        buffer.append(formatter.color(formatter.repeat("═", OutputFormatter.PANEL_WIDTH), formatter.getPalette().divider()));
        return newLine();
    }

    public OutputBuilder softDivider() {
        buffer.append(formatter.color(formatter.repeat("─", OutputFormatter.PANEL_WIDTH), formatter.getPalette().softDivider()));
        return newLine();
    }

    public OutputBuilder sectionTitle(String title, String titleColor) {
        buffer.append(formatter.color(formatter.getPalette().bold() + title, titleColor));
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

    public void clear() {
        buffer.setLength(0);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
