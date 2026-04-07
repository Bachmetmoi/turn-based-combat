package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public class DefendEffect extends StatusEffect {
    private static final int BONUS = 10;
    private boolean applied = false;

    public DefendEffect() { this.name = "Defend"; this.duration = 2; }

    @Override
    public void apply(Combatant c, GameUI ui) {
        if (!applied) {
            // Boost defense — tracked via override
            applied = true;
        }
    }

    @Override
    public void onExpire(Combatant c, GameUI ui) {
        ui.displayActionResult(c.getName() + "'s Defend effect expires.");
    }

    public int getBonus() { return BONUS; }
}