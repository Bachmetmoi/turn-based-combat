package entity.combatant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import boundary.GameUI;
import entity.effect.StatusEffect;

public abstract class Combatant {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected boolean alive = true;
    protected int stunDuration = 0;
    protected final List<StatusEffect> statusEffects = new ArrayList<>();

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
        if (hp == 0) alive = false;
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }
    
    public int getEffectiveDefense() {
        int bonus = statusEffects.stream()
            .filter(e -> e instanceof entity.effect.DefendEffect)
            .mapToInt(e -> ((entity.effect.DefendEffect) e).getBonus())
            .sum();
        return defense + bonus;
    }

    public void addStatusEffect(StatusEffect e) { statusEffects.add(e); }

    public void tickStatusEffects(GameUI ui) {
        Iterator<StatusEffect> it = statusEffects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            e.apply(this, ui);
            e.decrementDuration();
            if (e.isExpired()) {
                e.onExpire(this, ui);
                it.remove();
            }
        }
    }

    // TODO: Decouple stun from combatant

    public boolean isStunned() { return stunDuration > 0; }

    public void applyStun(int turns) { stunDuration = Math.max(stunDuration, turns); }

    public void decrementStun() { if (stunDuration > 0) stunDuration--; }

    public String getStatusSummary() {
        if (stunDuration > 0) return "[STUNNED " + stunDuration + "]";
        if (!statusEffects.isEmpty()) return "[" + statusEffects.get(0).getName() + "]";
        return "";
    }

    // Getters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getSpeed() { return speed; }
    public boolean isAlive() { return alive; }
}