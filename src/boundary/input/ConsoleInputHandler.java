package boundary.input;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final String prompt;

    public ConsoleInputHandler(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public int readChoice(int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) {
                    return val;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("\u001B[91mPlease enter a number between " + min + " and " + max + ".\u001B[0m");
        }
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
