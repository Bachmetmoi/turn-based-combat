package control.mode.survival;

import boundary.GameUI;
import control.BattleEngine;
import control.mode.GameMode;

public class SurvivalMode extends GameMode {

    public SurvivalMode() {
        super(new SurvivalLevelGenerator());
    }

    @Override
    public String getName() { return "Survival Mode"; }

    @Override
    public String getDescription() { return "Endless waves, increasing difficulty"; }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return !engine.getPlayer().isAlive();
    }

    @Override
    public void onRoundEnd(BattleEngine engine, GameUI ui) {
        if (engine.getPlayer().isAlive()) {
            ui.displayActionResult("--- Wave survived! Brace yourself — the next wave approaches... ---");
        }
    }
}
