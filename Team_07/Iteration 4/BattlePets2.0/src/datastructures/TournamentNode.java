package datastructures;

import javax.swing.tree.TreeNode;
import participants.Playable;
import utilities.IOManager;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;

public class TournamentNode implements TreeNode {
    private static int count = 1;//used only for debugging
    private int instancedCount;//used only for debugging
    private TournamentNode parent;
    private List<TournamentNode> children;
    private List<Playable> participants = new ArrayList<>();
    private Playable winner; //Not used but good for tracking later

    /**
     * Constructor for TournamentNode
     * @param parent - parent node for this node
     */
    TournamentNode(TournamentNode parent){
        instancedCount = count++;
        this.parent = parent;
        children = new ArrayList<>();
    }

    /**
     * This method recursively builds a tournament bracket based on Doug's Spicy Algorithm™
     * @param numRoundParticipants - the number of participants that are covered by this node
     */
    void buildTourney(int numRoundParticipants, int maxNumParticipants)
    {
        TournamentNode newRound;
        //System.out.println("Creating node with " + numRoundParticipants + " participants");

        int numParticipantsForNextRound = numRoundParticipants;
        int remainingPossibleChildren =  maxNumParticipants;
        if(numParticipantsForNextRound != 1)
        {
            while(numRoundParticipants > 0) {
                numParticipantsForNextRound =
                        (int) Math.ceil((double) numRoundParticipants / remainingPossibleChildren);
                //create a new node and call buildTourney on it to build subtree
                newRound = new TournamentNode(this);
                children.add(newRound);
                newRound.buildTourney(numParticipantsForNextRound, maxNumParticipants);
                //update numbers for next node(s)
                numRoundParticipants -= numParticipantsForNextRound;
                remainingPossibleChildren--;
            }
        }
    }

    /**
     * This method will add the passed node to the children list
     * @param ttn - TournamentNode to be added
     */
    public void addChild(TournamentNode ttn)
    {
        children.add(ttn);
    }

    /**
     * This method is only used for debugging currently.
     * @return garbage
     */
    public int getInstancedCount()
    {
        return instancedCount;
    }

    /**
     * This method retrieves a child from a given index in the children list.
     * @param i - index of child to return.
     * @return - child at the specified index
     */
    @Override
    public TreeNode getChildAt(int i) {
        if(children.size() < i)
            return null;
        return children.get(i);
    }

    /**
     * This method retrieves the size of the children list.
     * @return - size of children list
     */
    @Override
    public int getChildCount() {
        return children.size();
    }

    /**
     * This method retrieves the parent of the node.
     * @return - parent of the node
     */
    @Override
    public TreeNode getParent() {
        return parent;
    }

    /**
     * This method retrieves the index of the passed node.
     * @param treeNode - node to retrieve index for
     * @return - index of the passed node, -1 if node isn't in list
     */
    @Override
    public int getIndex(TreeNode treeNode) {
        int index = -1;
        for(TournamentNode child: children)
            if(child.equals(treeNode))
                index = children.indexOf(child);
        return index;
    }

    /**
     * This method returns if the node is willing to take more children.
     * @return - whether of not the node is willing to take another child.
     */
    @Override
    public boolean getAllowsChildren() {
        IOManager.promptLine("DON'T CALL THIS METHOD WE JUST NEED IT FOR TREE NODE");
        return false;
    }

    /**
     * This method detects if the node is a leaf.
     * @return - whether of not the node is a leaf
     */
    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * This method retrieves the children list and returns it as an enumeration.
     * @return - Enumeration of the children list
     */
    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(children);
    }

    /**
     * This method calculates whether the passed object is equal to this
     * @param o - object to compare to
     * @return - whether or not they are equal
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
            return true;
        if(!(o instanceof TournamentNode))
            return false;
        return (this.children() == ((TournamentNode) o).children());
    }

    /**
     * This method adds a participant to the list of participants
     * @param participant - participant in the tourney
     */
    public void addParticipant(Playable participant){
        participants.add(participant);
    }

    public List<Playable> getParticipants() {
        return participants;
    }

    /**
     * This method assigns the player as the winner of the fight and automatically adds
     * them to the playables for the next battle
     * @param winner - winner of the fight
     */
    public void assignWinner(Playable winner) {
        this.winner = winner;
        if(parent != null)
            parent.addParticipant(winner);
    }
}


