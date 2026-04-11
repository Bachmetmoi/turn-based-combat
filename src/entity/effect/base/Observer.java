package entity.effect.base;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import boundary.GameUI;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;

public abstract class Observer {
    protected final Map<CombatEvent, BiPredicate<Combatant, GameUI>> triggerMap = new HashMap<>();

    public boolean trigger(CombatEvent event, Combatant target, GameUI ui) {
        if (triggerMap.containsKey(event)) {
            return triggerMap.get(event).test(target, ui);
        }
        return true;
    }

    protected void addTrigger(CombatEvent event, BiPredicate<Combatant, GameUI> trigger) { triggerMap.put(event, trigger); }
    protected void removeTrigger(CombatEvent event) { triggerMap.remove(event); }

}
