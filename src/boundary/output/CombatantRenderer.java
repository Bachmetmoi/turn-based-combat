package boundary.output;

import boundary.output.colours.ColourPalette;
import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public class CombatantRenderer {
    private final OutputBuilder builder;
    private static final int BAR_LENGTH = 18;

    public CombatantRenderer(OutputBuilder builder) {
        this.builder = builder;
    }

    private ColourPalette palette() { return builder.getPalette(); }

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

    public String colourFor(Combatant c) {
        if (c.getName().toLowerCase().contains("dragon")) return palette().boss();
        return (c.getTeam() == ActionContext.Team.PLAYER) ? palette().player() : palette().enemy();
    }

    public String hpcolour(int hp, int maxHp) {
        if (maxHp <= 0) return palette().low();
        double ratio = hp * 1.0 / maxHp;
        if (ratio > 0.60) return palette().high();
        if (ratio > 0.30) return palette().medium();
        return palette().low();
    }

    private String barFillcolour(int hp, int maxHp) {
        double ratio = hp * 1.0 / maxHp;
        if (ratio > 0.60) return palette().success();
        if (ratio > 0.30) return palette().warning();
        return palette().danger();
    }

    private void appendHealthBar(int hp, int maxHp) {
        if (maxHp <= 0) maxHp = 1;
        int filled = (int) Math.round((hp * 1.0 / maxHp) * BAR_LENGTH);
        filled = Math.max(0, Math.min(BAR_LENGTH, filled));
        builder
            .repeat("█", filled, barFillcolour(hp, maxHp))
            .repeat("░", BAR_LENGTH - filled, palette().softDivider());
    }

    public String safeStatusText(Combatant c) {
        if (c.status == null) return "NORMAL";
        return c.status.toString();
    }

    public String statuscolour(String status) {
        String s = status.toLowerCase();
        if (s.contains("normal")) return palette().neutral();
        if (s.contains("stun")) return palette().warning();
        if (s.contains("dead") || s.contains("defeat")) return palette().danger();
        if (s.contains("buff")) return palette().success();
        return palette().neutral();
    }

    public void printCombatantCard(Combatant c, int index, boolean showIndex) {
        int maxHp = c.stats().get(StatField.maxHp);
        String status = safeStatusText(c);

        // Header Line
        if (showIndex) {
            builder.append(index + ". ", palette().primary());
        } else {
            builder.append("   ");
        }

        builder.append(iconFor(c), colourFor(c))
                .append(" ")
                .bold(c.getName(), colourFor(c))
                .append(" ");

        if (c.getTeam() == ActionContext.Team.PLAYER) {
            builder.append("[ALLY]", palette().player());
        } else {
            builder.append("[ENEMY]", palette().enemy());
        }

        builder.append("  ")
                .appendLine(status, statuscolour(status));

        // Stats Line
        builder.append("    ")
                .append("HP ", palette().neutral())
                .append(String.format("%d/%d", c.getHp(), maxHp), hpcolour(c.getHp(), maxHp))
                .append("  ");
        
        appendHealthBar(c.getHp(), maxHp);
        
        builder.println();
    }
}
