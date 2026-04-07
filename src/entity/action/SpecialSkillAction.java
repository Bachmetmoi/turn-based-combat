package entity.action;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.combatant.Player;

public class SpecialSkillAction implements Action {
    private final List<Combatant> targets;

    public SpecialSkillAction(List<Combatant> targets) { this.targets = targets; }

    @Override
    public void execute(Combatant actor, List<Combatant> allCombatants, GameUI ui) {
        if (actor instanceof Player) {
            Player p = (Player) actor;
            p.executeSpecialSkill(targets, ui);
            p.setSpecialCooldown(3);
        }
    }
}