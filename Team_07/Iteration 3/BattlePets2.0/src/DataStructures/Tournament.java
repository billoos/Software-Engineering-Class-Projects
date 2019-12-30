package DataStructures;

import Participants.Playable;
import Utilities.TournamentSequenceIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Tournament implements Iterable{
    private TournamentNode root;
    private List<TournamentNode> sequence;
    private List<Playable> winners = new ArrayList<>();
    private int numParticipants;
    private int participantsPerBattle;

    public Tournament(int numParticipants, int participantsPerBattle) {
        this.numParticipants = numParticipants;
        this.participantsPerBattle = participantsPerBattle;
        root = new TournamentNode(null);
        root.buildTourney(numParticipants, participantsPerBattle);
        createSequence();
    }

    /**
     * This method constructs a list of TournamentNodes and saves it in the order to be run.
     */
    private void createSequence()
    {
        sequence = new ArrayList<>();
        Stack<TournamentNode> bfsS = new Stack<>();
        List<TournamentNode> bfsQ = new ArrayList<>();

        //Creates the sequence from the root node
        bfsQ.add(root);
        while(!bfsQ.isEmpty()) {
            if (bfsQ.get(0).getChildCount() != 0)
                for (int i = 0; i < bfsQ.get(0).getChildCount(); i++)
                    bfsQ.add((TournamentNode) bfsQ.get(0).getChildAt(i));
            bfsS.push(bfsQ.remove(0));
        }
        //Reverses the list
        while(!bfsS.isEmpty())
            //System.out.println(bfsS.peek().getInstancedCount());
            sequence.add(bfsS.pop());
    }

    //Please ignore this method
    private TournamentNode sendNodes(){ return root; }

    /**
     * iterator to iterate through the tournament nodes
     * @return TournamentSequenceIterator
     */
    @Override
    public Iterator iterator()
    {
        return new TournamentSequenceIterator(sequence);
    }

    public void setWinners(List<Playable> winners) {
        this.winners = winners;
    }
}


