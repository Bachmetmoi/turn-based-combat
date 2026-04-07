package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect() { this.name = "SmokeBomb"; this.duration = 2; }

    @Override
    public void apply(Combatant c, GameUI ui) {}

    @Override
    public void onExpire(Combatant c, GameUI ui) {
        ui.displayActionResult("Smoke Bomb effect on " + c.getName() + " has expired.");
    }
}