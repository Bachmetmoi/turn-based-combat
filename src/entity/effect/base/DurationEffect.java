package entity.effect.base;

import boundary.GameUI;
import entity.combatant.Combatant;

public abstract class DurationEffect extends StatusEffect {
    protected int duration;

    public DurationEffect(String name, int duration) {
        super(name);
        this.duration = duration;
    }

    public int getDuration() { return duration; }
    public boolean isExpired() { return duration <= 0; }

    public void remove(Combatant target, GameUI ui) {
        ui.displayActionResult(name + " effect on " + target.getName() + " has expired.");
    }

    public boolean tick(Combatant target, GameUI ui) {
        duration--;
        if (isExpired()) remove(target, ui);
        return true;
    }

    @Override
    public String toString() {
        return "[" + name + " " + duration + "]";
    }
}