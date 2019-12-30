package events;

import utilities.PetTypes;
import utilities.PlayerTypes;
import utilities.Skills;

import java.util.Objects;
import java.util.Set;

public class PlayerEventInfo
{
    private int playableUid;
    private String petName;
    private PetTypes petType;
    private PlayerTypes playerType;
    private double startingHp;
    private Set<Skills> skillSet;

    private PlayerEventInfo(PlayerEventInfoBuilder builder)
    {
        this.playableUid = builder.playableUid;
        this.petName = builder.petName;
        this.petType = builder.petType;
        this.playerType = builder.playerType;
        this.startingHp = builder.startingHp;
        this.skillSet = builder.skillSet;
    }

    public PlayerEventInfo(PlayerEventInfo otherPlayerEventInfo)
    {
        this.playableUid = otherPlayerEventInfo.playableUid;
        this.petName = otherPlayerEventInfo.petName;
        this.petType = otherPlayerEventInfo.petType;
        this.playerType = otherPlayerEventInfo.playerType;
        this.startingHp = otherPlayerEventInfo.startingHp;
        this.skillSet = otherPlayerEventInfo.skillSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerEventInfo that = (PlayerEventInfo) o;
        return playableUid == that.playableUid &&
                Double.compare(that.startingHp, startingHp) == 0 &&
                Objects.equals(petName, that.petName) &&
                petType == that.petType &&
                playerType == that.playerType &&
                Objects.equals(skillSet, that.skillSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playableUid, petName, petType, playerType, startingHp, skillSet);
    }

    @Override
    public String toString() {
        return "PlayerEventInfo{" +
                "playableUid=" + playableUid +
                ", petName='" + petName + '\'' +
                ", petType=" + petType +
                ", playerType=" + playerType +
                ", startingHp=" + startingHp +
                ", skillSet=" + skillSet +
                '}';
    }

    public static class PlayerEventInfoBuilder
    {
        private int playableUid;
        private String petName;
        private PetTypes petType;
        private PlayerTypes playerType;
        private double startingHp;
        private Set<Skills> skillSet;

        public PlayerEventInfoBuilder()
        {

        }

        public PlayerEventInfoBuilder withPlayableUid(int playableUid)
        {
            this.playableUid = playableUid;
            return this;
        }

        public PlayerEventInfoBuilder withPetName(String petName)
        {
            this.petName = petName;
            return this;
        }

        public PlayerEventInfoBuilder withPetType(PetTypes petType)
        {
            this.petType = petType;
            return this;
        }

        public PlayerEventInfoBuilder withPlayerType(PlayerTypes playerType)
        {
            this.playerType = playerType;
            return this;
        }

        public PlayerEventInfoBuilder withStartingHp(double startingHp)
        {
            this.startingHp = startingHp;
            return this;
        }

        public PlayerEventInfoBuilder withSkillSet(Set<Skills> skills)
        {
            this.skillSet = skills;
            return this;
        }

        public PlayerEventInfo build()
        {
            return new PlayerEventInfo(this);
        }
    }
}
