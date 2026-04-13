package control.mode.story;

import boundary.UserInterface;
import control.BattleEngine;
import entity.level.Difficulty;
import control.mode.GameMode;

public class StoryMode extends GameMode {

    public StoryMode() {
        super(new StoryLevelGenerator());
    }

    @Override
    public String getName() { return "Story Mode"; }

    @Override
    public String getDescription() { return "Progress through all difficulties: " + getDifficultyPath(); }

    private String getDifficultyPath() {
        StringBuilder sb = new StringBuilder();
        Difficulty[] diffs = Difficulty.values();
        for (int i = 0; i < diffs.length; i++) {
            sb.append(diffs[i]);
            if (i < diffs.length - 1) sb.append(" --> ");
        }
        return sb.toString();
    }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return !engine.getPlayer().isAlive();
    }

    @Override
    public void onRoundEnd(BattleEngine engine, UserInterface ui) {
        int level = engine.getLevelNumber();
        int totalLevels = Difficulty.values().length;
        
        if (engine.getPlayer().isAlive()) {
            if (level < totalLevels) {
                ui.displayActionResult("--- Chapter " + level + " cleared! Prepare for the next enemy wave... ---");
            } else {
                ui.displayActionResult("--- All chapters conquered! You are victorious! ---");
            }
        }
    }
}
