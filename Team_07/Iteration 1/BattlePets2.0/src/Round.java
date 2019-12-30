public class Round
{
    //Damage in each index is FROM the participant at the same index
    private Damage[] damageDone;
    private Skills[] skillsUsed;

    public Round(Integer numberOfParticipants)
    {
        damageDone = new Damage[numberOfParticipants];
        skillsUsed = new Skills[numberOfParticipants];
    }

    public void addDamageDone(Damage damage, Integer index)
    {
        damageDone[index] = damage;
    }

    public void addSkillUsed(Skills skill, Integer index)
    {
        skillsUsed[index] = skill;
    }

    public Skills[] getSkillsUsed() {
        return skillsUsed;
    }

    public Damage[] getDamageDone() {
        return damageDone;
    }
}
