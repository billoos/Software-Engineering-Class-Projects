package DataStructures;

import Participants.Playable;

import java.util.ArrayList;
import java.util.List;

public class Round
{
    //DataStructures.Damage in each index is FROM the participant at the same index
    private List<SkillData> skillDataList = new ArrayList<>();
    private List<Playable> finalAwakePets = new ArrayList<>();

    public List<Playable> getFinalAwakePets() {
        return finalAwakePets;
    }

    public void setFinalAwakePets(List<Playable> finalAwakePets) {
        this.finalAwakePets = finalAwakePets;
    }

    public Round(){ }

    /**
     * adds skill data to the data list
     * @param skillData - holds attacker index, target index, damage, skill used and skill prediction
     */
    public void addSkillData(SkillData skillData) {
        skillDataList.add(skillData);
    }

    public List<SkillData> getSkillDataList() {
        return skillDataList;
    }

    /**
     * creates a new fight with a set amount of fights and catches illegal arguments
     * @param index - index of skill data
     * @param skillDataa - holds attacker index, target index, damage, skill used and skill prediction
     */
    public void updateSkillData(int index, SkillData skillDataa)
    {
        skillDataList.set(index, skillDataa);
    }
}
