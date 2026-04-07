package entity.action;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;

// OCP + DIP: BattleEngine depends on this interface
public interface Action {
    void execute(Combatant actor, List<Combatant> allCombatants, GameUI ui);
}