package entity.action;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;

public class BasicAttack implements Action {
    private final Combatant target;

    public BasicAttack(Combatant target) { this.target = target; }
    // TODO: have an AttackAction interface and implement it for BasicAttack and SpecialSkill

    @Override
    public void execute(Combatant actor, List<Combatant> allCombatants, GameUI ui) {
        int dmg = Math.max(0, actor.getAttack() - target.getEffectiveDefense());
        target.takeDamage(dmg);
        ui.displayActionResult(actor.getName() + " attacks " + target.getName() +
                " for " + dmg + " dmg! HP: " + target.getHp() + "/" + target.getMaxHp());
        if (!target.isAlive())
            ui.displayActionResult(target.getName() + " is ELIMINATED!");
    }
}