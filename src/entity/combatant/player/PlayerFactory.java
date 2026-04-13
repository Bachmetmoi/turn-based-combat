package entity.combatant.player;

import java.util.List;
import entity.combatant.helpers.EquipmentManager;
import entity.equipment.Equipment;
import entity.item.Item;

public class PlayerFactory {
    public static Player createPlayer(int type, List<Item> items, Equipment weapon, Equipment artifact) {
        EquipmentManager equipment = new EquipmentManager(weapon, artifact);
        if (type == 1) return new Warrior(items, equipment);
        return new Wizard(items, equipment);
    }
}
