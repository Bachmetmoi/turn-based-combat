package control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import boundary.GameUI;
import control.mode.GameMode;
import control.strategy.SpeedBasedTurnOrder;
import entity.combatant.helpers.EquipmentManager;
import entity.combatant.player.Player;
import entity.combatant.player.Warrior;
import entity.combatant.player.Wizard;
import entity.equipment.Equipment;
import entity.item.Item;
import entity.item.Potion;
import entity.level.Level;

public class GameManager {

    private final GameUI ui = new GameUI();

    public void startGame() {
        ui.displayWelcome();

        GameMode mode = null;
        int playerType = -1;
        List<Item> itemChoices = null;
        Equipment weaponChoice = null;
        Equipment artifactChoice = null;
        boolean replayWithSame = false;

        while (true) {
            if (!replayWithSame) {
                mode = ui.selectGameMode();
                playerType = mode.selectPlayerType(ui);
                itemChoices = mode.selectItems(ui);
                weaponChoice = mode.selectWeapon(ui);
                artifactChoice = mode.selectArtifact(ui);
            }

            boolean won = runMode(mode, playerType, itemChoices, weaponChoice, artifactChoice);
            ui.displayModeEnd(won, mode);

            int choice = ui.askReplay();
            if (choice == 1) {
                replayWithSame = true;
            } else if (choice == 2) {
                replayWithSame = false;
            } else {
                System.out.println("Thanks for playing! Goodbye.");
                break;
            }
        }
    }

    private boolean runMode(GameMode mode, int playerType, List<Item> itemChoices,
                            Equipment weaponChoice, Equipment artifactChoice) {
        
        Iterator<Level> levels = mode.iterator();
        int levelNumber = 1;

        while (levels.hasNext()) {
            Level level = levels.next();

            Player player = createPlayer(playerType, cloneItems(itemChoices), weaponChoice, artifactChoice);
            BattleEngine engine = new BattleEngine(ui, new SpeedBasedTurnOrder(), level, player, levelNumber);

            engine.startBattle();
            mode.onRoundEnd(engine, ui);

            if (mode.isBattleOver(engine)) {
                return player.isAlive();
            }
            levelNumber++;
        }
        return true;
    }

    private Player createPlayer(int type, List<Item> items, Equipment weapon, Equipment artifact) {
        EquipmentManager equipment = new EquipmentManager(weapon, artifact);
        if (type == 1) return new Warrior(items, equipment);
        return new Wizard(items, equipment);
    }

    private List<Item> cloneItems(List<Item> items) {
        List<Item> copies = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Potion) {
                copies.add(new Potion());
            } else if (item instanceof entity.item.PowerStone) {
                copies.add(new entity.item.PowerStone());
            } else if (item instanceof entity.item.SmokeBomb) {
                copies.add(new entity.item.SmokeBomb());
            }
        }
        return copies;
    }
}
