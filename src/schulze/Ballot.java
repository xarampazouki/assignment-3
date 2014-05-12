/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package schulze;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;

/**
 *
 * @author Apostolis
 */
public class Ballot {
    private final ArrayList<BallotWrapper> ballotDetails;
    
    public Ballot(JSONArray keys, JSONArray values) {
        this.ballotDetails = new ArrayList<>();
        for(int i = 0; i < keys.length(); i++) {
            ballotDetails.add(new BallotWrapper(keys.getString(i), values.getInt(i)));
        }
    }

    public ArrayList<BallotWrapper> getBallotDetails() {
        return ballotDetails;
    }
    
    public void sortBallotByPower() {
        BallotWrapper temp;
        for(int i = 0; i < ballotDetails.size()-1; i++ ) {
            for(int j = 0; j < ballotDetails.size()-1-i; j++) {
                if(ballotDetails.get(j).getBallotPower() < ballotDetails.get(j+1).getBallotPower()) {
                    temp = new BallotWrapper(ballotDetails.get(j).getCandidateName(), ballotDetails.get(j).getBallotPower());
                    ballotDetails.get(j).setBallotPower(ballotDetails.get(j+1).getBallotPower());
                    ballotDetails.get(j).setCandidateName(ballotDetails.get(j+1).getCandidateName());
                    ballotDetails.get(j+1).setBallotPower(temp.getBallotPower());
                    ballotDetails.get(j+1).setCandidateName(temp.getCandidateName());                    
                }
            }
        }
    }
    
}
