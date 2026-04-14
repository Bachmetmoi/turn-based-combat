package boundary;

import java.util.ArrayList;
import java.util.List;

import boundary.input.ConsoleInputHandler;
import boundary.input.InputHandler;
import boundary.output.ColorPalette;
import boundary.output.CombatantRenderer;
import boundary.output.DefaultColorPalette;
import boundary.output.OutputFormatter;
import control.Registry;
import control.mode.GameMode;
import control.mode.challenge.ChallengeMode;
import control.mode.story.StoryMode;
import control.mode.survival.SurvivalMode;
import control.mode.timed.TimedMode;
import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.combatant.player.Player;
import entity.interfaces.Describable;
import entity.interfaces.Named;
import entity.item.Item;
import entity.level.Difficulty;

public class GameUI implements UserInterface {

    private final InputHandler inputHandler;
    private final OutputFormatter formatter;
    private final CombatantRenderer combatantRenderer;

    public GameUI() {
        ColorPalette palette = new DefaultColorPalette();
        this.formatter = new OutputFormatter(palette);
        this.inputHandler = new ConsoleInputHandler(formatter.color("> ", palette.bold() + palette.primary()));
        this.combatantRenderer = new CombatantRenderer(formatter);
    }

    // Constructor for dependency injection
    public GameUI(InputHandler inputHandler, OutputFormatter formatter, CombatantRenderer combatantRenderer) {
        this.inputHandler = inputHandler;
        this.formatter = formatter;
        this.combatantRenderer = combatantRenderer;
    }

    private ColorPalette palette() { return formatter.getPalette(); }

    @Override
    public void displayWelcome() {
        System.out.println();
        formatter.builder()
            .divider()
            .append("████████╗██╗   ██╗██████╗ ███╗   ██╗    ██████╗  █████╗ ███████╗███████╗██████╗ ", palette().primary()).newLine()
            .append("╚══██╔══╝██║   ██║██╔══██╗████╗  ██║    ██╔══██╗██╔══██╗██╔════╝██╔════╝██╔══██╗", palette().primary()).newLine()
            .append("   ██║   ██║   ██║██████╔╝██╔██╗ ██║    ██████╔╝███████║███████╗█████╗  ██║  ██║", palette().primary()).newLine()
            .append("   ██║   ██║   ██║██╔══██╗██║╚██╗██║    ██╔══██╗██╔══██║╚════██║██╔══╝  ██║  ██║", palette().primary()).newLine()
            .append("   ██║   ╚██████╔╝██║  ██║██║ ╚████║    ██████╔╝██║  ██║███████║███████╗██████╔╝", palette().primary()).newLine()
            .append("   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝╚═════╝ ", palette().primary()).newLine()
            .append("        ██████╗ ██████╗ ███╗   ███╗██████╗  █████╗ ████████╗", palette().accent()).newLine()
            .append("       ██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔══██╗╚══██╔══╝", palette().accent()).newLine()
            .append("       ██║     ██║   ██║██╔████╔██║██████╔╝███████║   ██║   ", palette().accent()).newLine()
            .append("       ██║     ██║   ██║██║╚██╔╝██║██╔══██╗██╔══██║   ██║   ", palette().accent()).newLine()
            .append("       ╚██████╗╚██████╔╝██║ ╚═╝ ██║██████╔╝██║  ██║   ██║   ", palette().accent()).newLine()
            .append("        ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═════╝ ╚═╝  ╚═╝   ╚═╝   ", palette().accent()).newLine()
            .divider()
            .append("                 Enter the arena. Survive the fight.", palette().neutral()).newLine()
            .divider()
            .print();
    }

    @Override
    public GameMode selectGameMode() {
        return selectGameMode(List.of(
                new StoryMode(),
                new SurvivalMode(),
                new TimedMode(),
                new ChallengeMode()
        ));
    }

    public GameMode selectGameMode(List<GameMode> modes) {
        System.out.println();
        formatter.printSectionTitle("GAME MODES", palette().primary());
        formatter.printSoftDivider();

        for (int i = 0; i < modes.size(); i++) {
            GameMode mode = modes.get(i);
            System.out.printf("%s %s  %s%n",
                    formatter.color((i + 1) + ".", palette().primary()),
                    formatter.color(String.format("%-16s", mode.getName()), palette().secondary()),
                    formatter.color(mode.getDescription(), palette().neutral()));
        }

        formatter.printSoftDivider();
        int pick = inputHandler.readChoice(1, modes.size());
        return modes.get(pick - 1);
    }

    @Override
    public void displayModeEnd(boolean playerWon, GameMode mode) {
        System.out.println();
        formatter.printDivider();
        System.out.println(formatter.color("MODE", palette().primary()) + "  " + formatter.color(mode.getName().toUpperCase(), palette().bold() + palette().neutral()));
        System.out.println(formatter.color("RESULT", palette().primary()) + "  "
                + (playerWon
                ? formatter.color("VICTORY", palette().bold() + palette().success())
                : formatter.color("DEFEATED", palette().bold() + palette().danger())));
        formatter.printDivider();
    }

    @Override
    public <T extends Named & Describable> Class<? extends T> selectFromRegistry(Registry<T> registry, String title) {
        displayRegistry(registry, title);
        int choice = inputHandler.readChoice(1, registry.getNames().size());
        return registry.getType(choice - 1);
    }

    @Override
    public <T extends Named & Describable> List<Class<? extends T>> selectMultipleFromRegistry(
            Registry<T> registry, String title, int count) {

        displayRegistry(registry, title);
        List<Class<? extends T>> chosen = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            System.out.print(formatter.color("Choice " + i + ": ", palette().primary()));
            int pick = inputHandler.readChoice(1, registry.getNames().size());
            chosen.add(registry.getType(pick - 1));
        }

        return chosen;
    }

    private <T extends Named & Describable> void displayRegistry(Registry<T> registry, String title) {
        List<String> names = registry.getNames();
        List<Registry.Entry<T>> entries = registry.getEntries();

        System.out.println();
        formatter.printSectionTitle(title.toUpperCase(), palette().primary());
        formatter.printSoftDivider();

        for (int i = 0; i < entries.size(); i++) {
            System.out.printf("%s %s%n",
                    formatter.color((i + 1) + ".", palette().primary()),
                    formatter.color(names.get(i), palette().secondary()) + "  " + formatter.color(entries.get(i).description, palette().neutral()));
        }

        formatter.printSoftDivider();
    }

    @Override
    public Difficulty selectDifficulty() {
        System.out.println();
        formatter.printSectionTitle("DIFFICULTY", palette().primary());
        formatter.printSoftDivider();

        Difficulty[] diffs = Difficulty.values();
        for (int i = 0; i < diffs.length; i++) {
            String d = diffs[i].name();
            String difficultyColor = palette().success();

            if ("MEDIUM".equalsIgnoreCase(d)) {
                difficultyColor = palette().warning();
            } else if ("HARD".equalsIgnoreCase(d)) {
                difficultyColor = palette().danger();
            }

            System.out.printf("%s %s%n",
                    formatter.color((i + 1) + ".", palette().primary()),
                    formatter.color(d, difficultyColor));
        }

        formatter.printSoftDivider();
        int pick = inputHandler.readChoice(1, diffs.length);
        return diffs[pick - 1];
    }

    @Override
    public void displayRoundStart(int round, List<Combatant> combatants) {
        System.out.println();
        formatter.printDivider();
        System.out.println(formatter.color("ROUND " + round, palette().bold() + palette().accent()));
        formatter.printDivider();

        List<Combatant> players = new ArrayList<>();
        List<Combatant> enemies = new ArrayList<>();

        for (Combatant c : combatants) {
            if (!c.isAlive()) continue;

            if (c.getTeam() == ActionContext.Team.PLAYER) {
                players.add(c);
            } else {
                enemies.add(c);
            }
        }

        if (!players.isEmpty()) {
            formatter.printSectionTitle("ALLIES", palette().player());
            for (Combatant c : players) {
                combatantRenderer.printCombatantCard(c, 0, false);
            }
        }

        if (!players.isEmpty() && !enemies.isEmpty()) {
            formatter.printSoftDivider();
        }

        if (!enemies.isEmpty()) {
            formatter.printSectionTitle("ENEMIES", palette().enemy());
            for (Combatant c : enemies) {
                combatantRenderer.printCombatantCard(c, 0, false);
            }
        }

        formatter.printDivider();
    }

    @Override
    public Combatant selectTarget(List<Combatant> combatants) {
        System.out.println();
        formatter.printSectionTitle("SELECT TARGET", palette().primary());
        formatter.printSoftDivider();

        for (int i = 0; i < combatants.size(); i++) {
            combatantRenderer.printCombatantCard(combatants.get(i), i + 1, true);
            if (i < combatants.size() - 1) {
                System.out.println();
            }
        }

        formatter.printSoftDivider();
        int idx = inputHandler.readChoice(1, combatants.size()) - 1;
        return combatants.get(idx);
    }

    @Override
    public Item selectItem(List<Item> items) {
        System.out.println();
        formatter.printSectionTitle("SELECT ITEM", palette().primary());
        formatter.printSoftDivider();

        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%s %s%n",
                    formatter.color((i + 1) + ".", palette().primary()),
                    formatter.color(items.get(i).getName(), palette().secondary()));
        }

        formatter.printSoftDivider();
        return items.get(inputHandler.readChoice(1, items.size()) - 1);
    }

    @Override
    public void displayActionResult(String msg) {
        String lower = msg.toLowerCase();
        String chosenColor = palette().neutral();

        if (lower.contains("victory") || lower.contains("heal") || lower.contains("recover")
                || lower.contains("restored") || lower.contains("gain")) {
            chosenColor = palette().success();
        } else if (lower.contains("defeated") || lower.contains("eliminated")
                || lower.contains("dmg") || lower.contains("damage")
                || lower.contains("hit") || lower.contains("attacks")
                || lower.contains("critical hit")) {
            chosenColor = palette().danger();
        } else if (lower.contains("stun") || lower.contains("cooldown")
                || lower.contains("buff") || lower.contains("debuff")
                || lower.contains("prepare")) {
            chosenColor = palette().warning();
        }

        System.out.println(formatter.color(">> " + msg, chosenColor));
    }

    @Override
    public void displayBattleEnd(boolean playerWon, Player player, int rounds) {
        System.out.println();
        formatter.printDivider();

        if (playerWon) {
            System.out.println(formatter.color("BATTLE RESULT", palette().primary()) + "  " + formatter.color("VICTORY", palette().bold() + palette().success()));
            System.out.println(formatter.color("You defeated all enemies.", palette().success()));
            System.out.println(formatter.color("Final HP", palette().primary()) + "  "
                    + formatter.color(player.getHp() + "/" + player.stats().get(StatField.maxHp), palette().success()));
            System.out.println(formatter.color("Rounds", palette().primary()) + "    " + formatter.color(String.valueOf(rounds), palette().accent()));
        } else {
            System.out.println(formatter.color("BATTLE RESULT", palette().primary()) + "  " + formatter.color("DEFEATED", palette().bold() + palette().danger()));
            System.out.println(formatter.color("The enemy team has won this battle.", palette().danger()));
            System.out.println(formatter.color("Rounds", palette().primary()) + "    " + formatter.color(String.valueOf(rounds), palette().accent()));
        }

        formatter.printDivider();
    }

    @Override
    public int askReplay() {
        System.out.println();
        formatter.printSectionTitle("NEXT STEP", palette().primary());
        formatter.printSoftDivider();
        System.out.println(formatter.color("1.", palette().primary()) + " " + formatter.color("Replay with same settings", palette().neutral()));
        System.out.println(formatter.color("2.", palette().primary()) + " " + formatter.color("Start a new game", palette().neutral()));
        System.out.println(formatter.color("3.", palette().primary()) + " " + formatter.color("Exit", palette().neutral()));
        formatter.printSoftDivider();
        return inputHandler.readChoice(1, 3);
    }

    @Override
    public Action selectAction(List<Action> allActions,
                               List<Action> readyActions,
                               Combatant owner) {

        System.out.println();
        formatter.printSectionTitle("TURN", palette().primary());
        System.out.println(formatter.color(combatantRenderer.iconFor(owner) + " " + owner.getName(), palette().bold() + combatantRenderer.colorFor(owner)));
        formatter.printSoftDivider();

        for (int i = 0; i < allActions.size(); i++) {
            Action action = allActions.get(i);
            boolean ready = readyActions.contains(action);

            if (ready) {
                System.out.printf("%s %s%n",
                        formatter.color((i + 1) + ".", palette().primary()),
                        formatter.color(action.getLabel(), palette().secondary()));
            } else {
                System.out.printf("%s %s%n",
                        formatter.color((i + 1) + ".", palette().primary()),
                        formatter.color(action.getLabel() + " [UNAVAILABLE]", palette().danger()));
            }
        }

        formatter.printSoftDivider();

        while (true) {
            int input = inputHandler.readChoice(1, allActions.size());
            Action chosen = allActions.get(input - 1);
            if (readyActions.contains(chosen)) {
                return chosen;
            }
            System.out.println(formatter.color("Invalid choice. Please select a ready action.", palette().danger()));
        }
    }
}
