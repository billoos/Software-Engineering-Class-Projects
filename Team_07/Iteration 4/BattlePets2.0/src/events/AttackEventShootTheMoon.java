package events;

import datastructures.Damage;
import utilities.Skills;

import java.util.Objects;

public class AttackEventShootTheMoon extends AttackEvent
{
    private Skills predictedSkillEnum;

    private AttackEventShootTheMoon(AttackEventShootTheMoonBuilder builder)
    {
        super(builder.attackingPlayableUid, builder.victimPlayableUid, builder.attackingSkillChoice, builder.damage);
        this.predictedSkillEnum = builder.predictedSkillEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AttackEventShootTheMoon that = (AttackEventShootTheMoon) o;
        return predictedSkillEnum == that.predictedSkillEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), predictedSkillEnum);
    }

    @Override
    public String toString() {
        return "AttackEventShootTheMoon{" +
                "predictedSkillEnum=" + predictedSkillEnum +
                '}';
    }

    public Skills getPredictedSkillEnum() {
        return predictedSkillEnum;
    }

    public static class AttackEventShootTheMoonBuilder
    {
        private int attackingPlayableUid;
        private int victimPlayableUid;
        private Skills attackingSkillChoice;
        private Damage damage;
        private Skills predictedSkillEnum = null;

        public AttackEventShootTheMoonBuilder()
        {

        }

        public AttackEventShootTheMoonBuilder withAttackingPlayableUid(int attackingPlayableUid)
        {
            this.attackingPlayableUid = attackingPlayableUid;
            return this;
        }

        public AttackEventShootTheMoonBuilder withVictimPlayableUid(int victimPlayableUid)
        {
            this.victimPlayableUid = victimPlayableUid;
            return this;
        }

        public AttackEventShootTheMoonBuilder withAttackingSkillChoice(Skills attackingSkillChoice)
        {
            this.attackingSkillChoice = attackingSkillChoice;
            return this;
        }

        public AttackEventShootTheMoonBuilder withDamage(Damage damage)
        {
            this.damage = damage;
            return this;
        }

        public AttackEventShootTheMoonBuilder withPredictedSkillEnum(Skills predictedSkillEnum)
        {
            this.predictedSkillEnum = predictedSkillEnum;
            return this;
        }

        public AttackEventShootTheMoon build()
        {
            return new AttackEventShootTheMoon(this);
        }

    }
}
