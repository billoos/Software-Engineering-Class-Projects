package datastructures;

import utilities.IOManager;
import utilities.Skills;

public class SkillData {
    private Skills skillChosen;
    private Skills skillPredicted;
    private Damage damage;
    private int attackerIndex;
    private int targetIndex;

    /**
     * Default constructor
     */
    public SkillData(){}

    public Skills getSkillChosen() {
        return skillChosen;
    }

    public void setSkillChosen(Skills skillChosen) {
        this.skillChosen = skillChosen;
    }


    /**
     * Skill Predicted getter but will also print a message if there isn't a skill to predict yet
     * @return skill predicted
     */
    public Skills getSkillPredicted() {
        if (skillPredicted == null)
            IOManager.promptLine("Attempted to retrieve skill prediction when there was none");
        return skillPredicted;
    }

    public void setSkillPredicted(Skills skillPredicted) {
        this.skillPredicted = skillPredicted;
    }

    public Damage getDamage() {
        return damage;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }

    public int getAttackerIndex() {
        return attackerIndex;
    }

    public void setAttackerIndex(int attackerIndex) {
        this.attackerIndex = attackerIndex;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }
}
