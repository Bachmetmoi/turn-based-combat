package entity.item;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;

public abstract class Item {
    protected String name;
    protected boolean used = false;

    public String getName() { return name; }
    public boolean isUsed() { return used; }

    public abstract void use(Combatant actor, List<Combatant> targets, GameUI ui);
}