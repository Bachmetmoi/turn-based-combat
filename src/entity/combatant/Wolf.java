package entity.combatant;

public class Wolf extends Enemy {
    private static int count = 0;

    public Wolf() {
        this.name = "Wolf-" + (char)('A' + count++);
        this.hp = 40; this.maxHp = 40;
        this.attack = 45; this.defense = 5; this.speed = 35;
    }
}