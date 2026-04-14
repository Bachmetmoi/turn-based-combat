package boundary;

import control.Registry;
import control.mode.GameMode;
import control.mode.challenge.ChallengeMode;
import control.mode.story.StoryMode;
import control.mode.survival.SurvivalMode;
import control.mode.timed.TimedMode;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.combatant.player.Player;
import entity.interfaces.Describable;
import entity.interfaces.Named;
import entity.item.Item;
import entity.level.Difficulty;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameUI implements UserInterface {

    private final Scanner scanner = new Scanner(System.in);

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";

    private static final String BRIGHT_BLACK = "\u001B[90m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";
    private static final String BRIGHT_BLUE = "\u001B[94m";
    private static final String BRIGHT_PURPLE = "\u001B[95m";
    private static final String BRIGHT_CYAN = "\u001B[96m";
    private static final String BRIGHT_WHITE = "\u001B[97m";

    private static final int BAR_LENGTH = 18;
    private static final int PANEL_WIDTH = 72;

    private String color(String text, String color) {
        return color + text + RESET;
    }

    private String repeat(String s, int count) {
        return s.repeat(Math.max(0, count));
    }

    private void printDivider() {
        System.out.println(color(repeat("═", PANEL_WIDTH), BRIGHT_CYAN));
    }

    private void printSoftDivider() {
        System.out.println(color(repeat("─", PANEL_WIDTH), BRIGHT_BLACK));
    }

    private void printSectionTitle(String title, String titleColor) {
        System.out.println(color(BOLD + title, titleColor));
    }

    private String iconFor(Combatant c) {
        String name = c.getName().toLowerCase();

        if (name.contains("warrior")) return "🛡";
        if (name.contains("wizard")) return "🔮";
        if (name.contains("goblin")) return "👹";
        if (name.contains("wolf")) return "🐺";
        if (name.contains("player")) return "⚔";
        return "✦";
    }

    private String colorFor(Combatant c) {
        String name = c.getName().toLowerCase();

        if (name.contains("warrior")) return BRIGHT_BLUE;
        if (name.contains("wizard")) return BRIGHT_PURPLE;
        if (name.contains("goblin")) return BRIGHT_GREEN;
        if (name.contains("wolf")) return BRIGHT_RED;
        return BRIGHT_WHITE;
    }

    private boolean isPlayerSide(Combatant c) {
        String name = c.getName().toLowerCase();
        return name.contains("warrior") || name.contains("wizard") || name.contains("player");
    }

    private String hpColor(int hp, int maxHp) {
        if (maxHp <= 0) return BRIGHT_RED;

        double ratio = hp * 1.0 / maxHp;
        if (ratio > 0.60) return BRIGHT_GREEN;
        if (ratio > 0.30) return BRIGHT_YELLOW;
        return BRIGHT_RED;
    }

    private String barFillColor(int hp, int maxHp) {
        if (maxHp <= 0) return RED;

        double ratio = hp * 1.0 / maxHp;
        if (ratio > 0.60) return GREEN;
        if (ratio > 0.30) return YELLOW;
        return RED;
    }

    private String healthBar(int hp, int maxHp) {
        if (maxHp <= 0) maxHp = 1;

        int filled = (int) Math.round((hp * 1.0 / maxHp) * BAR_LENGTH);
        filled = Math.max(0, Math.min(BAR_LENGTH, filled));

        String filledPart = color(repeat("█", filled), barFillColor(hp, maxHp));
        String emptyPart = color(repeat("░", BAR_LENGTH - filled), BRIGHT_BLACK);

        return filledPart + emptyPart;
    }

    private String safeStatusText(Combatant c) {
        if (c.status == null) return "NORMAL";
        return c.status.toString();
    }

    private String statusColor(String status) {
        String s = status.toLowerCase();

        if (s.contains("normal")) return BRIGHT_WHITE;
        if (s.contains("stun")) return BRIGHT_YELLOW;
        if (s.contains("dead") || s.contains("defeat")) return BRIGHT_RED;
        if (s.contains("buff")) return BRIGHT_GREEN;
        return BRIGHT_WHITE;
    }

    private void printCombatantCard(Combatant c, int index, boolean showIndex) {
        int maxHp = c.stats().get(StatField.maxHp);
        String indexText = showIndex ? color(index + ". ", BRIGHT_CYAN) : "   ";
        String sideTag = isPlayerSide(c)
                ? color("[ALLY]", BRIGHT_BLUE)
                : color("[ENEMY]", BRIGHT_RED);

        String line1 = indexText
                + color(iconFor(c), colorFor(c))
                + " "
                + color(c.getName(), BOLD + colorFor(c))
                + " "
                + sideTag
                + "  "
                + color(safeStatusText(c), statusColor(safeStatusText(c)));

        String line2 = "    "
                + color("HP ", BRIGHT_WHITE)
                + color(String.format("%d/%d", c.getHp(), maxHp), hpColor(c.getHp(), maxHp))
                + "  "
                + healthBar(c.getHp(), maxHp);

        System.out.println(line1);
        System.out.println(line2);
    }

    @Override
    public void displayWelcome() {
        System.out.println();
        printDivider();
        System.out.println(color("████████╗██╗   ██╗██████╗ ███╗   ██╗    ██████╗  █████╗ ███████╗███████╗██████╗ ", BRIGHT_CYAN));
        System.out.println(color("╚══██╔══╝██║   ██║██╔══██╗████╗  ██║    ██╔══██╗██╔══██╗██╔════╝██╔════╝██╔══██╗", BRIGHT_CYAN));
        System.out.println(color("   ██║   ██║   ██║██████╔╝██╔██╗ ██║    ██████╔╝███████║███████╗█████╗  ██║  ██║", BRIGHT_CYAN));
        System.out.println(color("   ██║   ██║   ██║██╔══██╗██║╚██╗██║    ██╔══██╗██╔══██║╚════██║██╔══╝  ██║  ██║", BRIGHT_CYAN));
        System.out.println(color("   ██║   ╚██████╔╝██║  ██║██║ ╚████║    ██████╔╝██║  ██║███████║███████╗██████╔╝", BRIGHT_CYAN));
        System.out.println(color("   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝╚═════╝ ", BRIGHT_CYAN));
        System.out.println(color("        ██████╗ ██████╗ ███╗   ███╗██████╗  █████╗ ████████╗", BRIGHT_YELLOW));
        System.out.println(color("       ██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔══██╗╚══██╔══╝", BRIGHT_YELLOW));
        System.out.println(color("       ██║     ██║   ██║██╔████╔██║██████╔╝███████║   ██║   ", BRIGHT_YELLOW));
        System.out.println(color("       ██║     ██║   ██║██║╚██╔╝██║██╔══██╗██╔══██║   ██║   ", BRIGHT_YELLOW));
        System.out.println(color("       ╚██████╗╚██████╔╝██║ ╚═╝ ██║██████╔╝██║  ██║   ██║   ", BRIGHT_YELLOW));
        System.out.println(color("        ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═════╝ ╚═╝  ╚═╝   ╚═╝   ", BRIGHT_YELLOW));
        printDivider();
        System.out.println(color("                 Enter the arena. Survive the fight.", BRIGHT_WHITE));
        printDivider();
    }

    @Override
    public GameMode selectGameMode() {
        List<GameMode> modes = List.of(
                new StoryMode(),
                new SurvivalMode(),
                new TimedMode(),
                new ChallengeMode()
        );

        System.out.println();
        printSectionTitle("GAME MODES", BRIGHT_CYAN);
        printSoftDivider();

        for (int i = 0; i < modes.size(); i++) {
            GameMode mode = modes.get(i);
            System.out.printf("%s %s  %s%n",
                    color((i + 1) + ".", BRIGHT_CYAN),
                    color(String.format("%-16s", mode.getName()), BRIGHT_GREEN),
                    color(mode.getDescription(), BRIGHT_WHITE));
        }

        printSoftDivider();
        int pick = readChoice(1, modes.size());
        return modes.get(pick - 1);
    }

    @Override
    public void displayModeEnd(boolean playerWon, GameMode mode) {
        System.out.println();
        printDivider();
        System.out.println(color("MODE", BRIGHT_CYAN) + "  " + color(mode.getName().toUpperCase(), BOLD + BRIGHT_WHITE));
        System.out.println(color("RESULT", BRIGHT_CYAN) + "  "
                + (playerWon
                ? color("VICTORY", BOLD + BRIGHT_GREEN)
                : color("DEFEATED", BOLD + BRIGHT_RED)));
        printDivider();
    }

    @Override
    public <T extends Named & Describable> Class<? extends T> selectFromRegistry(Registry<T> registry, String title) {
        displayRegistry(registry, title);
        int choice = readChoice(1, registry.getNames().size());
        return registry.getType(choice - 1);
    }

    @Override
    public <T extends Named & Describable> List<Class<? extends T>> selectMultipleFromRegistry(
            Registry<T> registry, String title, int count) {

        displayRegistry(registry, title);
        List<Class<? extends T>> chosen = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            System.out.print(color("Choice " + i + ": ", BRIGHT_CYAN));
            int pick = readChoice(1, registry.getNames().size());
            chosen.add(registry.getType(pick - 1));
        }

        return chosen;
    }

    private <T extends Named & Describable> void displayRegistry(Registry<T> registry, String title) {
        List<String> names = registry.getNames();
        List<Registry.Entry<T>> entries = registry.getEntries();

        System.out.println();
        printSectionTitle(title.toUpperCase(), BRIGHT_CYAN);
        printSoftDivider();

        for (int i = 0; i < entries.size(); i++) {
            System.out.printf("%s %s%n",
                    color((i + 1) + ".", BRIGHT_CYAN),
                    color(names.get(i), BRIGHT_GREEN) + "  " + color(entries.get(i).description, BRIGHT_WHITE));
        }

        printSoftDivider();
    }

    @Override
    public Difficulty selectDifficulty() {
        System.out.println();
        printSectionTitle("DIFFICULTY", BRIGHT_CYAN);
        printSoftDivider();

        Difficulty[] diffs = Difficulty.values();
        for (int i = 0; i < diffs.length; i++) {
            String d = diffs[i].name();
            String difficultyColor = BRIGHT_GREEN;

            if ("MEDIUM".equalsIgnoreCase(d)) {
                difficultyColor = BRIGHT_YELLOW;
            } else if ("HARD".equalsIgnoreCase(d)) {
                difficultyColor = BRIGHT_RED;
            }

            System.out.printf("%s %s%n",
                    color((i + 1) + ".", BRIGHT_CYAN),
                    color(d, difficultyColor));
        }

        printSoftDivider();
        int pick = readChoice(1, diffs.length);
        return diffs[pick - 1];
    }

    @Override
    public void displayRoundStart(int round, List<Combatant> combatants) {
        System.out.println();
        printDivider();
        System.out.println(color("ROUND " + round, BOLD + BRIGHT_YELLOW));
        printDivider();

        List<Combatant> players = new ArrayList<>();
        List<Combatant> enemies = new ArrayList<>();

        for (Combatant c : combatants) {
            if (!c.isAlive()) continue;

            if (isPlayerSide(c)) {
                players.add(c);
            } else {
                enemies.add(c);
            }
        }

        if (!players.isEmpty()) {
            printSectionTitle("ALLIES", BRIGHT_BLUE);
            for (Combatant c : players) {
                printCombatantCard(c, 0, false);
            }
        }

        if (!players.isEmpty() && !enemies.isEmpty()) {
            printSoftDivider();
        }

        if (!enemies.isEmpty()) {
            printSectionTitle("ENEMIES", BRIGHT_RED);
            for (Combatant c : enemies) {
                printCombatantCard(c, 0, false);
            }
        }

        printDivider();
    }

    @Override
    public Combatant selectTarget(List<Combatant> combatants) {
        System.out.println();
        printSectionTitle("SELECT TARGET", BRIGHT_CYAN);
        printSoftDivider();

        for (int i = 0; i < combatants.size(); i++) {
            printCombatantCard(combatants.get(i), i + 1, true);
            if (i < combatants.size() - 1) {
                System.out.println();
            }
        }

        printSoftDivider();
        int idx = readChoice(1, combatants.size()) - 1;
        return combatants.get(idx);
    }

    @Override
    public Item selectItem(List<Item> items) {
        System.out.println();
        printSectionTitle("SELECT ITEM", BRIGHT_CYAN);
        printSoftDivider();

        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%s %s%n",
                    color((i + 1) + ".", BRIGHT_CYAN),
                    color(items.get(i).getName(), BRIGHT_GREEN));
        }

        printSoftDivider();
        return items.get(readChoice(1, items.size()) - 1);
    }

    @Override
    public void displayActionResult(String msg) {
        String lower = msg.toLowerCase();
        String chosenColor = BRIGHT_WHITE;

        if (lower.contains("victory") || lower.contains("heal") || lower.contains("recover")
                || lower.contains("restored") || lower.contains("gain")) {
            chosenColor = BRIGHT_GREEN;
        } else if (lower.contains("defeated") || lower.contains("eliminated")
                || lower.contains("dmg") || lower.contains("damage")
                || lower.contains("hit") || lower.contains("attacks")) {
            chosenColor = BRIGHT_RED;
        } else if (lower.contains("stun") || lower.contains("cooldown")
                || lower.contains("buff") || lower.contains("debuff")
                || lower.contains("prepare")) {
            chosenColor = BRIGHT_YELLOW;
        }

        System.out.println(color(">> " + msg, chosenColor));
    }

    @Override
    public void displayBattleEnd(boolean playerWon, Player player, int rounds) {
        System.out.println();
        printDivider();

        if (playerWon) {
            System.out.println(color("BATTLE RESULT", BRIGHT_CYAN) + "  " + color("VICTORY", BOLD + BRIGHT_GREEN));
            System.out.println(color("You defeated all enemies.", BRIGHT_GREEN));
            System.out.println(color("Final HP", BRIGHT_CYAN) + "  "
                    + color(player.getHp() + "/" + player.stats().get(StatField.maxHp), BRIGHT_GREEN));
            System.out.println(color("Rounds", BRIGHT_CYAN) + "    " + color(String.valueOf(rounds), BRIGHT_YELLOW));
        } else {
            System.out.println(color("BATTLE RESULT", BRIGHT_CYAN) + "  " + color("DEFEATED", BOLD + BRIGHT_RED));
            System.out.println(color("The enemy team has won this battle.", BRIGHT_RED));
            System.out.println(color("Rounds", BRIGHT_CYAN) + "    " + color(String.valueOf(rounds), BRIGHT_YELLOW));
        }

        printDivider();
    }

    @Override
    public int askReplay() {
        System.out.println();
        printSectionTitle("NEXT STEP", BRIGHT_CYAN);
        printSoftDivider();
        System.out.println(color("1.", BRIGHT_CYAN) + " " + color("Replay with same settings", BRIGHT_WHITE));
        System.out.println(color("2.", BRIGHT_CYAN) + " " + color("Start a new game", BRIGHT_WHITE));
        System.out.println(color("3.", BRIGHT_CYAN) + " " + color("Exit", BRIGHT_WHITE));
        printSoftDivider();
        return readChoice(1, 3);
    }

    private int readChoice(int min, int max) {
        while (true) {
            System.out.print(color("> ", BOLD + BRIGHT_CYAN));
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) {
                    return val;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println(color("Please enter a number between " + min + " and " + max + ".", BRIGHT_RED));
        }
    }

    @Override
    public Action selectAction(List<Action> allActions,
                               List<Action> readyActions,
                               Combatant owner) {

        System.out.println();
        printSectionTitle("TURN", BRIGHT_CYAN);
        System.out.println(color(iconFor(owner) + " " + owner.getName(), BOLD + colorFor(owner)));
        printSoftDivider();

        for (int i = 0; i < allActions.size(); i++) {
            Action action = allActions.get(i);
            boolean ready = readyActions.contains(action);

            if (ready) {
                System.out.printf("%s %s%n",
                        color((i + 1) + ".", BRIGHT_CYAN),
                        color(action.getLabel(), BRIGHT_GREEN));
            } else {
                System.out.printf("%s %s%n",
                        color((i + 1) + ".", BRIGHT_CYAN),
                        color(action.getLabel() + " [UNAVAILABLE]", BRIGHT_RED));
            }
        }

        printSoftDivider();

        while (true) {
            System.out.print(color("> ", BOLD + BRIGHT_CYAN));
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= 1 && input <= allActions.size()) {
                    Action chosen = allActions.get(input - 1);
                    if (readyActions.contains(chosen)) {
                        return chosen;
                    }
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println(color("Invalid choice. Please select a ready action.", BRIGHT_RED));
        }
    }
}