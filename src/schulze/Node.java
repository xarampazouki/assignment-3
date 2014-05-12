/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package schulze;

/**
 *
 * @author Apostolis
 */
public class Node {
    private static int nodeCount = 0;
    private String candidateName;
    private final int ID;
    
    
    public Node(String candidateName) {
        ID = nodeCount++;
        this.candidateName = candidateName;
    }

    public static int getEdgeCount() {
        return nodeCount;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public int getID() {
        return ID;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
    
    @Override
    public String toString() {
        return "Node ID : "+ ID+ ", Candidate Name : " + candidateName;
    }
}
