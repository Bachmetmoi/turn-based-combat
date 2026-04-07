package entity.action;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.item.Item;

public class ItemAction implements Action {
    private final Item item;
    private final List<Combatant> targets;

    public ItemAction(Item item, List<Combatant> targets) {
        this.item = item;
        this.targets = targets;
    }

    @Override
    public void execute(Combatant actor, List<Combatant> allCombatants, GameUI ui) {
        if (item.isUsed()) {
            ui.displayActionResult("Item already used!");
            return;
        }
        item.use(actor, targets, ui);
    }
}