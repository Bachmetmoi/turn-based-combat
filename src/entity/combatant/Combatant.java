package entity.combatant;

import boundary.GameUI;
import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.combatant.helpers.ActionMenu;
import entity.combatant.helpers.StatField;
import entity.combatant.helpers.Stats;
import entity.combatant.helpers.StatusManager;
import entity.effect.base.StatusEffect;

public abstract class Combatant {
    protected final String name;
    protected int hp;
    protected final Stats baseStats;
    protected final Stats statEffects;
    protected final StatusManager status;
    protected final ActionMenu actions;

    public Combatant(String name, int hp, int attack, int defense, int speed) {
        this.name = name;
        this.hp = hp;
        this.baseStats = new Stats(hp, attack, defense, speed);
        this.statEffects = new Stats(0, 0, 0, 0);
        this.status = new StatusManager(this);
        this.actions = new ActionMenu();
    }


    public abstract Action chooseAction(ActionContext ctx);

    public void takeTurn(ActionContext ctx) {
        if (status.trigger(CombatEvent.TURN_START, ctx.ui)) {
            Action chosen = chooseAction(ctx);
            actions.decrementCooldowns();
            chosen.execute(ctx);
        } else {
            actions.decrementCooldowns();
        }
        status.trigger(CombatEvent.TURN_END, ctx.ui);
        status.removeExpired();
    }

    public boolean takeDamage(int dmg, GameUI ui) {
        if (!status.trigger(CombatEvent.ATTACKED, ui)) return false;
        hp = Math.max(0, hp - dmg);
        return true;
    }
    
    public Stats stats() { return baseStats.add(statEffects); }
    public void setHp(int hp) { this.hp = hp; }
    
    // Getters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }
    public ActionMenu getActions() { return actions; }
    public Stats getBaseStats() { return baseStats; }
    public Stats getStatEffects() { return statEffects; }
    public int getStat(StatField stat) { return stats().get(stat); }
    public void applyStatus(StatusEffect effect, GameUI ui) { status.add(effect, ui); }
    public String showStatus() { return status.toString(); }
}