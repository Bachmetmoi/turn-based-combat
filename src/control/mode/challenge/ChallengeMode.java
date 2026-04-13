package control.mode.challenge;

import java.util.List;

import boundary.UserInterface;
import control.BattleEngine;
import control.mode.GameMode;
import entity.equipment.Equipment;
import entity.item.Item;
import entity.item.Potion;

public class ChallengeMode extends GameMode {
    public ChallengeMode() {
        super(new ChallengeLevelGenerator());
    }

    @Override
    public String getName() { return "Challenge Mode"; }

    @Override
    public String getDescription() { return "Fixed loadout (Warrior + 2 Potions), Boss battle"; }

    @Override
    public int selectPlayerType(UserInterface ui) {
        ui.displayActionResult("Challenge Mode: Warrior selected as fixed class.");
        return 1;
    }

    @Override
    public List<Item> selectItems(UserInterface ui) {
        ui.displayActionResult("Challenge Mode: 2x Potion assigned as fixed items.");
        return List.of(new Potion(), new Potion());
    }

    @Override
    public Equipment selectWeapon(UserInterface ui) {
        ui.displayActionResult("Challenge Mode: no equipment selected.");
        return null;
    }

    @Override
    public Equipment selectArtifact(UserInterface ui) {
        return null;
    }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    @Override
    public void onRoundEnd(BattleEngine engine, UserInterface ui) {
        if (engine.getPlayer().isAlive()) {
            ui.displayActionResult("--- Challenge complete! You conquered Boss mode with a fixed loadout! ---");
        } else {
            ui.displayActionResult("--- Challenge failed. Adapt your tactics and try again! ---");
        }
    }
}
