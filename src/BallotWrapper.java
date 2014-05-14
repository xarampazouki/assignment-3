package schulze;

class BallotWrapper {
    private String candidateName;
    private int ballotPower;

    public BallotWrapper(String candidateName, int ballotPower) {
        this.candidateName = candidateName;
        this.ballotPower = ballotPower;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public int getBallotPower() {
        return ballotPower;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public void setBallotPower(int ballotPower) {
        this.ballotPower = ballotPower;
    }

}
