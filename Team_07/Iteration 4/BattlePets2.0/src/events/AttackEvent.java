package events;

import datastructures.Damage;
import utilities.Skills;

import java.util.Objects;

public class AttackEvent extends BaseEvent
{
    private int attackingPlayableUid;
    private int victimPlayableUid;
    private Skills attackingSkillChoice;
    private Damage damage;
    public AttackEvent()
    {

    }

    public AttackEvent(int attackingPlayableUid, int victimPlayableUid, Skills attackingSkillChoice, Damage damage)
    {
        this.attackingPlayableUid = attackingPlayableUid;
        this.victimPlayableUid = victimPlayableUid;
        this.attackingSkillChoice = attackingSkillChoice;
        this.damage = damage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttackEvent that = (AttackEvent) o;
        return attackingPlayableUid == that.attackingPlayableUid &&
                victimPlayableUid == that.victimPlayableUid &&
                attackingSkillChoice == that.attackingSkillChoice &&
                Objects.equals(damage, that.damage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attackingPlayableUid, victimPlayableUid, attackingSkillChoice, damage);
    }

    @Override
    public String toString() {
        return "AttackEvent{" +
                "attackingPlayableUid=" + attackingPlayableUid +
                ", victimPlayableUid=" + victimPlayableUid +
                ", attackingSkillChoice=" + attackingSkillChoice +
                ", damage=" + damage +
                '}';
    }

    public int getAttackingPlayableUid() {
        return attackingPlayableUid;
    }

    public int getVictimPlayableUid() {
        return victimPlayableUid;
    }

    public Skills getAttackingSkillChoice() {
        return attackingSkillChoice;
    }

    public Damage getDamage() {
        return damage;
    }
}
