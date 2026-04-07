package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public abstract class StatusEffect {
    protected String name;
    protected int duration;

    public String getName() { return name; }
    public boolean isExpired() { return duration <= 0; }
    public void decrementDuration() { duration--; }

    public abstract void apply(Combatant c, GameUI ui);
    public void onExpire(Combatant c, GameUI ui) {}
}