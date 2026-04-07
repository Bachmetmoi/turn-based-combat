package control.strategy;

import java.util.List;

import entity.combatant.Combatant;

// DIP: BattleEngine depends on this interface, not concrete implementations
public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}