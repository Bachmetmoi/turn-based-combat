package entity.combatant;

public class Goblin extends Enemy {
    private static int count = 0;

    public Goblin() {
        this.name = "Goblin-" + (char)('A' + count++);
        this.hp = 55; this.maxHp = 55;
        this.attack = 35; this.defense = 15; this.speed = 25;
    }
}