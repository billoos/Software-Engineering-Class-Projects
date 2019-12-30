package Utilities;

import DataStructures.TournamentNode;

import java.util.Iterator;
import java.util.List;

public class TournamentSequenceIterator implements Iterator {
    private List sequenceList;
    private int currentIndex;

    /**
     * Constructor for a TournamentSequenceIterator that requires a sequenceList and initializes currentIndex to 0.
     * @param sequenceList - List to iterate over
     */
    public TournamentSequenceIterator(List<TournamentNode> sequenceList) {
        this.sequenceList = sequenceList;
        currentIndex = 0;
    }

    /**
     * Returns if there is another element in the list to be read.
     * @return - boolean for if there is another element in the list
     */
    @Override
    public boolean hasNext() {
        return (currentIndex < sequenceList.size());
    }

    /**
     * Returns the next element in the list
     * @return - Object (Playable) at next list index
     */
    @Override
    public Object next() {
        return sequenceList.get(currentIndex++);
    }
}
