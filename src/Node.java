
package schulze;

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
