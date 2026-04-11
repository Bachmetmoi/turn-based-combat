package entity.action.enemy;

import java.util.List;

import entity.action.ActionContext;
import entity.action.interfaces.SingleTargetAttack;
import entity.combatant.Combatant;

public class EnemyBasicAttack implements SingleTargetAttack {

    @Override
    public void executeOn(Combatant target, ActionContext ctx) {
        SingleTargetAttack.super.executeOn(target, ctx);
    }

    @Override
    public List<Combatant> selectTargets(ActionContext ctx) {
        return SingleTargetAttack.super.selectTargets(ctx);
    }

    @Override
    public Combatant selectTarget(ActionContext ctx) {
        return ctx.getLivingOpponents().get(0);
    }

    @Override public String getLabel() { return "Basic Attack"; }
}