package utilities;

@FunctionalInterface
public interface Observer
{
    void update(Object event);
}
