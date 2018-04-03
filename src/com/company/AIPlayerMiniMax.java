import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AIPlayerMiniMax {
    /*
    Todo
    minimax algoritme
    Winning stategie
    Puntentelling zet
    Winning moves?

     */

    //variabelen
    private boolean computerMove = false;
    private int[][] ocupiedCells = {{0,0,0},{0,0,0},{0,0,0}};
    private HashMap<Integer[], Integer> hashMap = new HashMap<>();
    private int globalPlayer = 1;
    private int globalOpponent = 2;

    // 0 is leeg, 1 is computer, 2 is speler


    public void printHashMap() {

        int[] best = miniMaxTicTacToe(1, 0);

        // Print the content of the hashMap
        Set<Entry<Integer[],Integer>> hashSet=hashMap.entrySet();
        for(Entry entry:hashSet ) {

            //System.out.println("Key="+entry.getKey()+", Value="+entry.getValue());
        }


        Map.Entry<Integer[],Integer> maxEntry = null;

        for (Map.Entry<Integer[],Integer> entry : hashMap.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        Integer[] xy = maxEntry.getKey();

        System.out.println(xy[0] + " " + xy[1]);
    }




    public int[] miniMaxTicTacToe(int player, int depth) {
        depth++;

        int winner = checkWinner();
        if(winner == globalPlayer) {
            int[] returnArray1 = {3, -1, -1};
            return returnArray1;
        }
        else if (winner == globalOpponent) {
            int[] returnArray2 = {0, -1, -1};
            return returnArray2;
        }
        else if (boardFull()){
            int[] returnArray3 = {1, -1, -1};
            return returnArray3;
        }


        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                if(ocupiedCells[x][y] == 0) {
                    ocupiedCells[x][y] = player;
                    int[] temp;
                    if(player == 1) {
                        temp = miniMaxTicTacToe(2, depth);
                    }
                    else {
                        temp = miniMaxTicTacToe(1, depth);
                    }
                    depth--;
                    ocupiedCells[x][y] = 0;

                    //System.out.println(temp[0]);
                    if(depth == 0) {
                        //Integer[] temparr = {temp[0],x, y};
                        Integer temparr[] = {x, y};
                        hashMap.put(temparr, temp[0]);
                    }
                }
            }
        }
        int[] returnarray = {0,0,0};
        return returnarray;
    }

    private boolean boardFull() {
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                if(ocupiedCells[x][y] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int checkWinner() {
        int[][][] winPatterns = {{ {0,0},{0,1},{0,2} }, { {0,0},{1,1},{2,2} }, { {0,0},{1,0},{2,0} }, { {2,0},{2,1},{2,2} }, { {0,2},{1,2},{2,2} }, { {1,0},{1,1},{1,2} }, { {2,0},{1,1},{0,2} }, { {0,1},{1,1},{2,1} }};

        for(int i = 0; i < 8; i++) {
            if(ocupiedCells[winPatterns[i][0][0]][winPatterns[i][0][1]] == 1 && ocupiedCells[winPatterns[i][1][0]][winPatterns[i][1][1]] == 1 && ocupiedCells[winPatterns[i][2][0]][winPatterns[i][2][1]] == 1) {
                return 1;
            }
            if(ocupiedCells[winPatterns[i][0][0]][winPatterns[i][0][1]] == 2 && ocupiedCells[winPatterns[i][1][0]][winPatterns[i][1][1]] == 2 && ocupiedCells[winPatterns[i][2][0]][winPatterns[i][2][1]] == 2) {
                return 2;
            }
        }
        return 0;



        //possible ways to win:
        //0,0 0,1 0,2
        //0,0 1,1 2,2
        //0,0 1,0 2,0
        //2,0 2,1 2,2
        //0,2 1,2 2,2
        //1,0 1,1 1,2
        //2,0 1,1 0,2
        //0,1 1,1 2,1



        // implementatie om hele bord te checken of er een winnaar is.
    }


}
