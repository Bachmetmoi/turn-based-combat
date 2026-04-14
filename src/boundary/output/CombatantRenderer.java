package boundary.output;

import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public class CombatantRenderer {
    private final OutputFormatter formatter;
    private static final int BAR_LENGTH = 18;

    public CombatantRenderer(OutputFormatter formatter) {
        this.formatter = formatter;
    }

    private ColorPalette palette() { return formatter.getPalette(); }

    public String iconFor(Combatant c) {
        String name = c.getName().toLowerCase();
        if (name.contains("warrior")) return "🛡";
        if (name.contains("wizard")) return "🔮";
        if (name.contains("goblin")) return "👹";
        if (name.contains("wolf")) return "🐺";
        if (name.contains("dragon")) return "🐲";
        if (name.contains("player")) return "⚔";
        return "✦";
    }

    public String colorFor(Combatant c) {
        if (c.getName().toLowerCase().contains("dragon")) return palette().boss();
        return (c.getTeam() == ActionContext.Team.PLAYER) ? palette().player() : palette().enemy();
    }

    public String hpColor(int hp, int maxHp) {
        if (maxHp <= 0) return palette().low();
        double ratio = hp * 1.0 / maxHp;
        if (ratio > 0.60) return palette().high();
        if (ratio > 0.30) return palette().medium();
        return palette().low();
    }

    private String barFillColor(int hp, int maxHp) {
        double ratio = hp * 1.0 / maxHp;
        if (ratio > 0.60) return palette().success();
        if (ratio > 0.30) return palette().warning();
        return palette().danger();
    }

    public String healthBar(int hp, int maxHp) {
        if (maxHp <= 0) maxHp = 1;
        int filled = (int) Math.round((hp * 1.0 / maxHp) * BAR_LENGTH);
        filled = Math.max(0, Math.min(BAR_LENGTH, filled));
        String filledPart = formatter.color(formatter.repeat("█", filled), barFillColor(hp, maxHp));
        String emptyPart = formatter.color(formatter.repeat("░", BAR_LENGTH - filled), palette().softDivider());
        return filledPart + emptyPart;
    }

    public String safeStatusText(Combatant c) {
        if (c.status == null) return "NORMAL";
        return c.status.toString();
    }

    public String statusColor(String status) {
        String s = status.toLowerCase();
        if (s.contains("normal")) return palette().neutral();
        if (s.contains("stun")) return palette().warning();
        if (s.contains("dead") || s.contains("defeat")) return palette().danger();
        if (s.contains("buff")) return palette().success();
        return palette().neutral();
    }

    public void printCombatantCard(Combatant c, int index, boolean showIndex) {
        int maxHp = c.stats().get(StatField.maxHp);
        String indexText = showIndex ? formatter.color(index + ". ", palette().primary()) : "   ";
        String sideTag = (c.getTeam() == ActionContext.Team.PLAYER)
                ? formatter.color("[ALLY]", palette().player())
                : formatter.color("[ENEMY]", palette().enemy());

        String line1 = indexText
                + formatter.color(iconFor(c), colorFor(c))
                + " "
                + formatter.color(c.getName(), palette().bold() + colorFor(c))
                + " "
                + sideTag
                + "  "
                + formatter.color(safeStatusText(c), statusColor(safeStatusText(c)));

        String line2 = "    "
                + formatter.color("HP ", palette().neutral())
                + formatter.color(String.format("%d/%d", c.getHp(), maxHp), hpColor(c.getHp(), maxHp))
                + "  "
                + healthBar(c.getHp(), maxHp);

        System.out.println(line1);
        System.out.println(line2);
    }
}
