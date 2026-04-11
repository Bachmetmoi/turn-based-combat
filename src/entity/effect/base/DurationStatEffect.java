package entity.effect.base;

import boundary.GameUI;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public class DurationStatEffect extends DurationEffect {
    protected final int value;
    protected final StatField stat;

    public DurationStatEffect(String name, int duration, int value, StatField stat) {
        super(name, duration);
        this.value = value;
        this.stat = stat;
        addTrigger(CombatEvent.TURN_START, this::tick);
    }

    public int getValue() { return value; }
    public StatField getStat() { return stat; }

    @Override
    public void apply(Combatant target, GameUI ui) {
        target.getStatEffects().add(stat, value);
    }

    @Override
    public void remove(Combatant target, GameUI ui) {
        target.getStatEffects().subtract(stat, value);
        super.remove(target, ui);
    }
}
