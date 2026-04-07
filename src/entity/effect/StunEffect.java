package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public class StunEffect extends StatusEffect {
    public StunEffect() { this.name = "Stun"; this.duration = 2; }

    @Override
    public void apply(Combatant c, GameUI ui) {}
}