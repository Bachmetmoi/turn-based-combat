package entity.effect.equipment;

import boundary.UserInterface;
import boundary.output.colours.ColourPalette;
import entity.combatant.Combatant;
import entity.effect.base.NonStackableEffect;
import entity.effect.base.PermanentEffect;

public class EquipmentEffect extends PermanentEffect implements NonStackableEffect {

    public void apply(Combatant target, UserInterface ui) {}

    public void remove(Combatant target, UserInterface ui) {}

    @Override
    public String getColour(ColourPalette palette) {
        return palette.success();
    }
}
