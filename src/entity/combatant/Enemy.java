package entity.combatant;

import java.util.List;

import boundary.GameUI;

public abstract class Enemy extends Combatant {

    public void takeTurn(List<Combatant> targets, GameUI ui) {
        if (targets.isEmpty()) return;
        Combatant target = targets.get(0);
        int dmg = Math.max(0, attack - target.getEffectiveDefense());

        // SmokeBombEffect on target overrides damage to 0 — handled in effect
        // We query effectiveIncomingDamage if target has smoke bomb
        if (target instanceof Player) {
            Player p = (Player) target;
            if (p.isSmokeBombActive()) {
                ui.displayActionResult(name + " attacks " + target.getName() +
                        " -- 0 damage (Smoke Bomb active)!");
                return;
            }
        }
        target.takeDamage(dmg);
        ui.displayActionResult(name + " attacks " + target.getName() +
                " for " + dmg + " dmg! HP: " + target.getHp() + "/" + target.getMaxHp());
        if (!target.isAlive())
            ui.displayActionResult(target.getName() + " has been DEFEATED!");
    }
}
