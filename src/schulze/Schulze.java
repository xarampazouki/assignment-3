/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package schulze;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.JSONObject;
/**
 *
 * @author Apostolis
 */
public class Schulze {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Ballot> ballots = new ArrayList<>();
        HashMap<String, Integer> candidates = new HashMap<>();
        ArrayList<Node> nodes = new ArrayList<>();
        int[][] strongest;
        int[][] pairwisePreferences;
        int[][] graphPairwisePreferences;
        int[][] pred;
        Ballot vote;
        ArrayList<Integer>[] list;
        URI uri;
        try {
            String fileName = args[0];
            uri = new URI("file:///"+ fileName);
            //uri = new URI("https://raw.githubusercontent.com/dmst-algorithms-course/assigmnent-3/master/example_elections.json");
            JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
            JSONObject root = new JSONObject(tokener);
            JSONArray arr  = root.getJSONArray("ballots");
            JSONArray arr2 = root.getJSONArray("candidates");
            for(int i = 0; i < arr.length(); i++) {
                vote = new Ballot(arr2, arr.getJSONArray(i));
                ballots.add(vote);
            }
            for(int j = 0; j < arr2.length(); j++) {
                candidates.put(arr2.getString(j), j);
                nodes.add(new Node(arr2.getString(j)));
            }
            pairwisePreferences = new int[arr2.length()][arr2.length()];
            graphPairwisePreferences = new int[arr2.length()][arr2.length()];
            strongest = new int[arr2.length()][arr2.length()];
            pred = new int[arr2.length()][arr2.length()];
            list = new ArrayList[arr2.length()];
            for(int k = 0; k < arr2.length(); k++) {
                for(int m = 0; m < arr2.length(); m++) {
                    pairwisePreferences[k][m] = 0;
                }
            }
            for(int i= 0; i < ballots.size(); i++) {
                ballots.get(i).sortBallotByPower();
                for(int k = 0; k < ballots.get(i).getBallotDetails().size(); k++) {
                    for(int j = k+1; j < ballots.get(i).getBallotDetails().size(); j++) {
                        pairwisePreferences[candidates.get(ballots.get(i).getBallotDetails().get(k).getCandidateName())][candidates.get(ballots.get(i).getBallotDetails().get(j).getCandidateName())]++;
                    }
                }
                System.out.print("[");
                for(int j=0; j < ballots.get(i).getBallotDetails().size(); j++) {
                    System.out.print(ballots.get(i).getBallotDetails().get(j).getCandidateName());
                    if(j != ballots.get(i).getBallotDetails().size()-1) {
                        System.out.print(", ");
                    } else {
                        System.out.print("]");
                    }
                }
                System.out.println("");
            }
            for(int i = 0; i < nodes.size(); i++) {
                for(int j = 0; j < nodes.size(); j++) {
                    System.out.print(pairwisePreferences[i][j]);
                    if(j != nodes.size()-1) {
                        System.out.print(",");
                    }
                }
                System.out.println("");
            }

            for(int i = 0; i < nodes.size(); i ++) {
                for(int k=0; k < nodes.size(); k++) {
                    if(i != k) {
                        if(pairwisePreferences[i][k]-pairwisePreferences[k][i] > 0) {
                            graphPairwisePreferences[i][k] = pairwisePreferences[i][k] - pairwisePreferences[k][i];
                        } else {
                            graphPairwisePreferences[i][k] = 0;
                        }
                    } else {
                        graphPairwisePreferences[i][k] = 0;
                    }
                }
            }
            for(int i = 0; i < nodes.size(); i++) {
                for(int j = 0; j < nodes.size(); j++) {
                    System.out.print(graphPairwisePreferences[i][j]);
                    if(j != nodes.size()-1) {
                        System.out.print(",");
                    }
                }
                System.out.println("");
            } 
            for(int i = 0; i < nodes.size(); i++) {
                for(int j = 0; j < nodes.size(); j++) {
                    if(graphPairwisePreferences[i][j] - graphPairwisePreferences[j][i] > 0) {
                        //edges.add(new Edge(graphPairwisePreferences[i][j], nodes.get(i), nodes.get(k)));
                        strongest[i][j] = graphPairwisePreferences[i][j] - graphPairwisePreferences[j][i];
                        pred[i][j] = i;
                    } else {
                        strongest[i][j] = -1;
                        pred[i][j] = -1;
                    }
                }
            }
            for(int k = 0; k < nodes.size(); k++){
                for(int i = 0; i < nodes.size(); i++) {
                    if(i != k) {
                        for(int j = 0; j < nodes.size(); j++) {
                            if(j != i) {
                                if(strongest[i][j] != -1 && strongest[i][k] != -1 && strongest[k][j] != -1) {
                                    if(strongest[i][j] < min(strongest[i][k], strongest[k][j])) {
                                        strongest[i][j] = min(strongest[i][k], strongest[k][j]);
                                        pred[i][j] = pred[k][j];
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for(int i = 0; i < nodes.size(); i++) {
                for(int j = 0; j < nodes.size(); j++) {
                    System.out.print(strongest[i][j]);
                    if(j != nodes.size()-1) {
                        System.out.print(",");
                    }
                }
                System.out.println("");
            } 
/*            for(int i = 0; i < edges.size(); i++) {
                System.out.println(edges.get(i).toString());
            }*/
            for(int i = 0; i < nodes.size(); i++) {
                list[i] = new ArrayList<>();
                for(int j = 0; j < nodes.size(); j++) {
                    if(i != j) {
                        if(strongest[i][j] != -1) {
                            if(strongest[j][i] != -1) {
                                if(strongest[i][j] > strongest[j][i]) {
                                    list[i].add(j);
                                }
                            }else {
                                list[i].add(j);
                            }
                        }
                    }
                }
            }
            for(int i = 0; i < nodes.size(); i++) {
                System.out.print(nodes.get(i).getCandidateName()+ " = " + list[i].size() + " [");
                for(int j = 0; j < list[i].size(); j++) {
                    if(j == list[i].size()-1) {
                        System.out.print(nodes.get(list[i].get(j)).getCandidateName() + "]");
                    } else {
                        System.out.print(nodes.get(list[i].get(j)).getCandidateName() + ", ");
                    }
                    
                }
                if(list[i].isEmpty()) {
                    System.out.print("]");
                }
                System.out.println("");
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Schulze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Schulze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Schulze.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    private static int min(int a, int b) {
        if(a >= b) {
            return a;
        } else {
            return b;
        }
    }
}
