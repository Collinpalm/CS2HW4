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

    public void computeAlignment(int gencost){
        int min, pos;
        for(int i = 0;i<m;i++){
            a[i][0] = i*gencost;
            setArrow(1, i, 0);
        }
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
                min = 0;
                pos = 0;
                for (int k = 0;k<3;k++){
                    if(arr[k]>min) {
                        min = arr[k];
                        pos = k;
                    }
                }
                a[i][j] = min;
                setArrow(pos, i, j);
            }
        }
    }

    public void setArrow(int num, int i, int j){
        switch(num) {
            case 0:
                this.arrows[i][j] = 'D';
                break;
            case 1:
                this.arrows[i][j] = 'U';
                break;
            case 2:
                this.arrows[i][j] = 'R';
                break;
        }

    }

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

    public String getAlignment(){
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        buildAlign(m, n, str1, str2);
        return str1+" "+str2;
    }

    public void buildAlign(int i, int j, StringBuilder str1, StringBuilder str2){
        if(i<=0 || j<= 0){
            return;
        }
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
