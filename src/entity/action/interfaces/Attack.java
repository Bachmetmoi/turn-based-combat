package entity.action.interfaces;

import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public interface Attack extends Action {
    default int getDamage(Combatant target, ActionContext ctx) {
        return Math.max(0, ctx.actor.getStat(StatField.attack) - target.getStat(StatField.defense));
    }

    default void displayDamage(Combatant target, ActionContext ctx, int dmg) {
        ctx.ui.displayActionResult(dmg + " dmg dealt! HP: " + target.getHp() + "/" + target.getStat(StatField.maxHp));
        if (!target.isAlive())
            ctx.ui.displayActionResult(target.getName() + " is ELIMINATED!");
    }

    default void displayAttack(Combatant target, ActionContext ctx) {
        ctx.ui.displayActionResult(ctx.actor.getName() + " " + getVerb() + " " + target.getName() + "!");
    }

    @Override
    default void executeOn(Combatant target, ActionContext ctx) {
        int dmg = getDamage(target, ctx);
        displayAttack(target, ctx);
        if (target.takeDamage(dmg, ctx.ui)) { displayDamage(target, ctx, dmg); }
    }

    @Override 
    default String getLabel() { return "Basic Attack"; }

    default String getVerb() { return "attacks"; }
}