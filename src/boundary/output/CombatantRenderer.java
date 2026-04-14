package boundary.output;

import boundary.output.colours.ColourPalette;
import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public class CombatantRenderer {
    private final OutputBuilder builder;
    private static final int BAR_LENGTH = 50;
    private static final int CARD_WIDTH = 70;

    public CombatantRenderer(OutputBuilder builder) { this.builder = builder; }

    private ColourPalette palette() { return builder.getPalette(); }

    private String hpColour(int hp, int maxHp) {
        double ratio = hp * 1.0 / Math.max(maxHp, 1);
        if (ratio > 0.60) return palette().success();
        if (ratio > 0.30) return palette().warning();
        return palette().danger();
    }

    private String fit(String s, int width) {
        if (s.length() > width) {
            return s.substring(0, width - 3) + "...";
        }
        return String.format("%-" + width + "s", s);
    }

    private void healthBar(int hp, int maxHp) {
        maxHp = Math.max(maxHp, 1);
        int filled = (int) Math.round((hp * 1.0 / maxHp) * BAR_LENGTH);
        filled = Math.max(0, Math.min(BAR_LENGTH, filled));

        builder
            .repeat("█", filled, hpColour(hp, maxHp))
            .repeat("░", BAR_LENGTH - filled, palette().softDivider());
    }

    private void cardBorder() {
        builder
            .append("    +")
            .repeat("-", CARD_WIDTH)
            .appendLine("+");
    }

    private void cardLine(String text, String colour) {
        builder
            .append("    |")
            .append(fit(text, CARD_WIDTH), colour)
            .appendLine("|");
    }

    public void printCombatantCard(Combatant c, int index, boolean showIndex) {
        int maxHp = c.stats().get(StatField.maxHp);
        String statusText = c.getStatusText();
        String team = (c.getTeam() == ActionContext.Team.PLAYER) ? "ALLY" : "ENEMY";

        cardBorder();

        String name = (showIndex ? index + ". " : "") + c.getName() + " [" + team + "]";
        cardLine(name, c.getColour(palette()));
        cardLine("STATUS: " + statusText, c.getStatusColour(palette()));
        cardLine("", palette().neutral());

        String[] art = c.getArt();
        for (String a : art) { cardLine(a, c.getColour(palette())); }

        cardLine("", palette().neutral());

        builder
            .append("    |")
            .append(fit("HP " + c.getHp() + "/" + maxHp, 12), hpColour(c.getHp(), maxHp));
        healthBar(c.getHp(), maxHp);

        int used = 12 + BAR_LENGTH;
        int remain = (CARD_WIDTH) - used;
        if (remain > 0) builder.append(fit("", remain));

        builder.appendLine("|");
        cardBorder();
        builder.print();
    }
}