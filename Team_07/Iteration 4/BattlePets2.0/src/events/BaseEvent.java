package events;

public abstract class BaseEvent
{
    private EventTypes eventTypes;



    public BaseEvent(EventTypes eventTypes)
    {
        this.eventTypes = eventTypes;
    }
    public abstract int hashCode();
    public abstract boolean equals(Object o);
    public abstract String toString();
    public BaseEvent()
    {

    }

}
