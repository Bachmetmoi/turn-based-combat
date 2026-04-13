package control.mode;

import boundary.GameUI;
import control.BattleEngine;

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
            ui.displayActionResult("--- Wave " + engine.getLevelNumber() +
                    " survived! Brace yourself — the next wave approaches... ---");
        }
    }
}
