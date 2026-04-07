package entity.item;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.combatant.Player;

public class PowerStone extends Item {
    public PowerStone() { this.name = "Power Stone"; }

    @Override
    public void use(Combatant actor, List<Combatant> targets, GameUI ui) {
        if (!(actor instanceof Player)) return;
        Player p = (Player) actor;
        ui.displayActionResult(actor.getName() + " uses Power Stone -- Special Skill triggered (no cooldown change)!");
        p.executeSpecialSkill(targets, ui);
        // Cooldown is NOT changed — Power Stone does not set or reset it
        used = true;
    }
}