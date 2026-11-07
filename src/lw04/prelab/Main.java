package lw04.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("branches.txt");
            Scanner scanner = new Scanner(file);

            int N = scanner.nextInt();
            int[] branches = new int[N];
            
            for (int i = 0; i < N; i++) {
                branches[i] = scanner.nextInt();
            }

              int M = scanner.nextInt();
            int[] targets = new int[M];
            
            for (int i = 0; i < M; i++) {
                targets[i] = scanner.nextInt();
            }
            
            scanner.close();

            bubbleSort3PassesDescending(branches);

            for (int i = 0; i < branches.length; i++) {
                System.out.print(branches[i] + (i == branches.length - 1 ? "" : " "));
            }
            System.out.println();

            for (int i = 0; i < M; i++) {
                int target = targets[i];
                int index = linearSearch(branches, target);
                
                if (index != -1) {
                    System.out.println(target + ": FOUND at index " + index);
                } else {
                    System.out.println(target + ": NOT FOUND");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File branches.txt tidak ditemukan.");
        }
    }

    public static void bubbleSort3PassesDescending(int[] arr) {
        int passes = 3;
        int n = arr.length;
        
        for (int i = 0; i < passes; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] < arr[j + 1]) {

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
}