package entity.combatant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.item.Item;

public abstract class Player extends Combatant {
    protected List<Item> inventory;
    protected int specialCooldown = 0;

    // TODO: decouple smoke bomb from player class
    private boolean smokeBombActive = false;
    private int smokeBombTurns = 0;

    public boolean isSmokeBombActive() { return smokeBombActive; }

    public void activateSmokeBomb(int turns) {
        smokeBombActive = true;
        smokeBombTurns = turns;
    }

    public void tickSmokeBomb() {
        if (smokeBombActive) {
            smokeBombTurns--;
            if (smokeBombTurns <= 0) smokeBombActive = false;
        }
    }

    public Player(List<Item> items) {
        this.inventory = new ArrayList<>(items);
    }

    public List<Item> getUsableItems() {
        return inventory.stream().filter(i -> !i.isUsed()).collect(Collectors.toList());
    }

    public int getSpecialCooldown() { return specialCooldown; }

    public void setSpecialCooldown(int cd) { specialCooldown = cd; }

    public void decrementCooldown() {
        if (specialCooldown > 0) specialCooldown--;
    }

    public abstract void executeSpecialSkill(List<Combatant> targets, boundary.GameUI ui);


}