package control.mode;

import java.util.Iterator;
import java.util.List;
import boundary.GameUI;
import control.BattleEngine;
import entity.level.Level;
import entity.item.Item;
import entity.equipment.Equipment;

public abstract class GameMode implements Iterable<Level> {
    protected final LevelGenerator levelGenerator;

    protected GameMode(LevelGenerator levelGenerator) {
        this.levelGenerator = levelGenerator;
    }

    public String getName() { return getClass().getSimpleName(); }
    public abstract String getDescription();
    
    @Override
    public Iterator<Level> iterator() {
        return levelGenerator.iterator();
    }

    public int selectPlayerType(GameUI ui) {
        return ui.selectPlayerType();
    }

    public List<Item> selectItems(GameUI ui) {
        return ui.selectItems();
    }

    public Equipment selectWeapon(GameUI ui) {
        return ui.selectWeapon();
    }

    public Equipment selectArtifact(GameUI ui) {
        return ui.selectArtifact();
    }

    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    public abstract void onRoundEnd(BattleEngine engine, GameUI ui);
}
