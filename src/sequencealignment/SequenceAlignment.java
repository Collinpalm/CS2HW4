package sequencealignment;
/* Name:
 * Collin Palm
 * COP3503 Fall 2021
 * Programming Assignment 4
 */




public class SequenceAlignment {
    //obj variables
    private String one;
    private String two;
    private int[][] a;//matrix to compute the optimal cost
    private int[][] arrows;//matrix to store the arrow directions
    int m;//stores length of string one, so I don't completely annihilate the runtime
    int n;//same thing as m but for string two

    //constructor
    public SequenceAlignment(String first, String second) {
        one = first;
        two = second;
        //LOOK IM DOING A DYNAMIC PROGRAMING THING!
        m = one.length();
        n = two.length();
        a = new int[m][n];
        arrows = new int[m][n];
    }

    public void computeAlignment(int gencost){
        for(int i = 0;i<m;i++){
            a[i][0] = i*gencost;
        }
        for(int j = 0;j<n;j++){
            a[0][j] = j*gencost;
        }
        int[] arr = {0,0,0};//array to store the 3 options and pick the minimum
        for(int j = 0; j<n;j++){
            for(int i = 0; i<m; i++){

            }
        }
    }

    public String getAlignment(){
        return one;
    }
}
