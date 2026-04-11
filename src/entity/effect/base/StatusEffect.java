package entity.effect.base;

import boundary.GameUI;
import entity.combatant.Combatant;

public abstract class StatusEffect extends Observer {
    protected final String name;

    public StatusEffect(String name) { this.name = name; }

    public String getName() { return name; }

    public abstract void apply(Combatant target, GameUI ui);
    public abstract void remove(Combatant target, GameUI ui);
    public abstract boolean isExpired();

    public String toString() {
        return "[" + name + "]";
    }
}
