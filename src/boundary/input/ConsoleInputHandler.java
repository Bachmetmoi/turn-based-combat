package boundary.input;

import java.util.Scanner;

import boundary.output.OutputBuilder;
import boundary.output.colours.ColourPalette;

public class ConsoleInputHandler implements InputHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final String prompt;
    private final OutputBuilder builder;
    private final ColourPalette palette;

    public ConsoleInputHandler(String prompt, ColourPalette palette) {
        this.builder = new OutputBuilder(palette);
        this.palette = palette;
        this.prompt = builder.append(prompt, palette.primary() + palette.bold()).toString();
    }

    @Override
    public int readChoice(int min, int max) {
        while (true) {
            builder.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) {
                    return val;
                }
            } catch (NumberFormatException ignored) {
            }
            builder
                .newLine()
                .append("Please enter a number between " + min + " and " + max + ".", palette.danger() + palette.bold())
                .print();
        }
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
