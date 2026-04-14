package entity.effect.base;

import boundary.UserInterface;
import boundary.output.colours.ColourPalette;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public class PermanentStatEffect extends PermanentEffect {
    protected final int value;
    protected final StatField stat;
    
    public PermanentStatEffect(int value, StatField stat) {
        this.value = value;
        this.stat = stat;
    }

    @Override
    public String getColour(ColourPalette palette) {
        return value > 0 ? palette.success() : palette.warning();
    }

    @Override
    public void apply(Combatant target, UserInterface ui) {
        target.statEffects.add(stat, value);
    }

    @Override
    public void remove(Combatant target, UserInterface ui) {
        target.statEffects.subtract(stat, value);
    }
}
