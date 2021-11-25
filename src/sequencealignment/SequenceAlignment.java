package sequencealignment;
import java.util.*;
import java.lang.*;

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
    private char[][] arrows;//matrix to store the arrow directions
    int m;//stores length of string one, so I don't completely annihilate the runtime
    int n;//same thing as m but for string two

    //constructor
    public SequenceAlignment(String first, String second) {
        one = first;
        two = second;
        //LOOK IM DOING A DYNAMIC PROGRAMING THING!
        m = one.length();
        n = two.length();
        a = new int[m+1][n+1];
        arrows = new char[m+1][n+1];
    }
    //builds the matrix to get the proper alignment
    public void computeAlignment(int gencost){
        int min, pos;//var declarations
        //fills in first column
        for(int i = 0;i<m;i++){
            a[i][0] = i*gencost;
            setArrow(1, i, 0);
        }
        //fills in first row
        for(int j = 0;j<n;j++){
            a[0][j] = j*gencost;
            setArrow(2, 0, j);
        }
        int[] arr = {0,0,0};//array to store the 3 options and pick the minimum
        for(int j = 1; j<=n;j++){
            for(int i = 1; i<=m; i++){
                arr[0] = alpha(one.charAt(i-1), two.charAt(j-1)) + a[i-1][j-1];
                arr[1] = gencost + a[i-1][j];
                arr[2] = gencost + a[i][j-1];
                min = arr[0];
                pos = 0;
                for (int k = 0;k<3;k++){
                    if(arr[k]<min) {//picks the smallest value
                        min = arr[k];
                        pos = k;
                    }
                }
                a[i][j] = min;//sets the number in the matrix
                setArrow(pos, i, j);//sets the arrow in the matrix
            }
        }
    }

    public void setArrow(int num, int i, int j){
        //picks which directions the arrow points based on the
        //cases from class
        switch(num) {
            case 0:
                this.arrows[i][j] = 'D';
                //diagonal
                break;
            case 1:
                this.arrows[i][j] = 'U';
                //vertical
                break;
            case 2:
                this.arrows[i][j] = 'R';
                //horizontal
                break;
        }
        //yes I realize that the chars do not match the first letter of the words
        //leave me alone
    }
    //method to get the value for alpha, it checks if they are the same
    //then if they are both a vowel or a consonant, otherwise returns the max value
    public int alpha(char a, char b){
        if(a == b){
            return 0;
        }
        if("aeiouy".contains(""+a) && "aeiouy".contains(""+b)){
            return 1;
        }
        if(!"aeiouy".contains(""+a) && !"aeiouy".contains(""+b)){
            return 1;
        }
        return 3;
    }
//called by the runner
    public String getAlignment(){


        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        buildAlign(m, n, str1, str2);
        return str1+" "+str2;
    }
    //recursive function that builds the strings from back to front, inserting '-' where appropriate
    /*I really thought this was very smart, I am using the stringbuilder objects to insert the new
     *char at the front.  Of course, you could use a char arraylist for the same purpose, but I feel
     *like if stringbuilder exists why not use it.
    */
    public void buildAlign(int i, int j, StringBuilder str1, StringBuilder str2){
        if(i<=0 || j<= 0){
            return;
        }
        //checks the key values and does the proper operations
        switch(this.arrows[i][j]){
            case 'D':
                str1.insert(0, one.charAt(i-1));
                str2.insert(0, two.charAt(j-1));
                buildAlign(i-1, j-1, str1, str2);
                break;
            case 'U':
                str1.insert(0, one.charAt(i-1));
                str2.insert(0, '-');
                buildAlign(i-1, j, str1, str2);
                break;
            case 'R':
                str1.insert(0, '-');
                str2.insert(0, two.charAt(j-1));
                buildAlign(i, j-1, str1, str2);
                break;
        }
    }
}
