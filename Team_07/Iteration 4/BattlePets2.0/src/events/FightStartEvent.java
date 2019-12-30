package events;

import java.util.List;
import java.util.Objects;

public class FightStartEvent extends BaseEvent
{
    private int fightIndex;
    private List<PlayerEventInfo> playerEventInfos;

    public FightStartEvent(int fightIndex, List<PlayerEventInfo> playerEventInfoList)
    {
        this.fightIndex = fightIndex;
        this.playerEventInfos = playerEventInfoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FightStartEvent that = (FightStartEvent) o;
        return fightIndex == that.fightIndex &&
                Objects.equals(playerEventInfos, that.playerEventInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fightIndex, playerEventInfos);
    }

    @Override
    public String toString() {
        return "FightStartEvent{" +
                "fightIndex=" + fightIndex +
                ", playerEventInfos=" + playerEventInfos +
                '}';
    }

    public int getFightIndex() {
        return fightIndex;
    }

    public List<PlayerEventInfo> getPlayerEventInfos() {
        return playerEventInfos;
    }
}
