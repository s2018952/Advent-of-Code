import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Solution1 {
    private int output;
    private HashMap<Character,Integer> card_encodings;
    private HashMap<String,Integer> hands_to_bids;
    private ArrayList<String> sorted_hands;
    public Solution1(String filepath) throws IOException {
        this.output = 0;
        this.sorted_hands = new ArrayList<>();
        this.card_encodings = new HashMap<>();
        this.card_encodings.put('A',14);
        this.card_encodings.put('K',13);
        this.card_encodings.put('Q',12);
        this.card_encodings.put('J',11);
        this.card_encodings.put('T',10);
        for (int i = 9; i > 1; i--) {
            this.card_encodings.put(Character.forDigit(i,10),i);
        }
        this.hands_to_bids = new HashMap<>();
        Path path = Paths.get(filepath);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String handString = line.split(" ")[0];
            String bidString = line.split(" ")[1];
            this.hands_to_bids.put(handString,Integer.parseInt(bidString));
            //System.out.println(handString);
            //System.out.println(bidString);
        }
        scanner.close();
    }

    public void printOutput() {
        this.computeOutput();
        System.out.println(output);
    }

    private void computeOutput() {
        for (String hand : hands_to_bids.keySet()) {
            insert_hand(hand);
        }
        for (int i = 0; i < sorted_hands.size(); i++) {
            output += (i + 1) * hands_to_bids.get(sorted_hands.get(i));
            //System.out.println(sorted_hands.get(i));
        }
    }

    private int getRank(String hand) {
        HashMap<Character,Integer> card_counts = new HashMap<>();
        for (int i = 0; i < hand.length(); i++) {
            if (card_counts.containsKey(hand.charAt(i))) {
                card_counts.put(hand.charAt(i),card_counts.get(hand.charAt(i)) + 1);
            }
            else {
                card_counts.put(hand.charAt(i),1);
            }
        }
        int max_count = 0;
        for (Character key : card_counts.keySet()) {
            max_count = Math.max(max_count,card_counts.get(key));
        }
        if (card_counts.keySet().size() == 1) {
            return 7;
        }
        else if (card_counts.keySet().size() == 2 && max_count == 4) {
            return 6;
        }
        else if (card_counts.keySet().size() == 2 && max_count == 3) {
            return 5;
        }
        else if (card_counts.keySet().size() == 3 && max_count == 3) {
            return 4;
        }
        else if (card_counts.keySet().size() == 3 && max_count == 2) {
            return 3;
        }
        else if (max_count == 1) {
            return 1;
        }
        return 2;
    }

    private void insert_hand(String hand) {
        int rank = getRank(hand);
        for (int i = 0; i < sorted_hands.size(); i++) {
            int current_rank = getRank(sorted_hands.get(i));
            if (rank < current_rank) {
                sorted_hands.add(i,hand);
                return;
            }
            else if (rank == current_rank) {
                for (int j = 0; j < 5; j++) {
                    int hand_code = card_encodings.get(hand.charAt(j));
                    int list_hand_code = card_encodings.get(sorted_hands.get(i).charAt(j));
                    if (hand_code < list_hand_code) {
                        sorted_hands.add(i,hand);
                        return;
                    }
                    else if (hand_code > list_hand_code) {
                        break;
                    }
                }
            }
        }
        sorted_hands.add(hand);
    }
}
