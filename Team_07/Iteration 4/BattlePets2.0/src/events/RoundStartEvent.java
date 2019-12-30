package events;

import java.util.Objects;

public class RoundStartEvent extends BaseEvent
{
    private int roundNumber;

    public RoundStartEvent(int roundNumber)
    {
        this.roundNumber = roundNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundStartEvent that = (RoundStartEvent) o;
        return roundNumber == that.roundNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundNumber);
    }

    @Override
    public String toString() {
        return "RoundStartEvent{" +
                "roundNumber=" + roundNumber +
                '}';
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
