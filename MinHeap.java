package Code;

// Greyson Cabrera 014121118
// Dustin Martin 015180085

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinHeap {

    public int[] heap;
    public int size;
    public int maxsize;


    public MinHeap(int max) {

        this.size = 0;
        this.maxsize = max;
        this.heap = new int[this.maxsize + 1];
        this.heap[0] = Integer.MIN_VALUE;
    }

    public void insert(int element){

        if (size >= maxsize) {
            return;
        }
        heap[++size] = element;
        int i = size;

        while (heap[i] < heap[get_parent(i)]) {
            swap(i, get_parent(i));
            i = get_parent(i);
        }
    }

    public int pop(){

        int popped = heap[1];
        heap[1] = heap[size--];
        percolate_down(1);
        return popped;
    }

    public void percolate_down(int i){

        if (i >= (size / 2) && i <= size) {
            if (heap[i] > heap[get_left(i)]
                    || heap[i] > heap[get_right(i)]) {


                if (heap[get_left(i)] < heap[get_right(i)]) {
                    swap(i, get_left(i));
                    percolate_down(get_left(i));
                }

                else {
                    swap(i, get_right(i));
                    percolate_down(get_right(i));
                }
            }
        }
    }

    public void build_heap(){

        for (int i = size/2; i >= 1; i--) {
            percolate_down(i);
        }
    }

    public void heap_sort(){

        int si = size;
        for (int i=1; i<si; i++ ){
            pop();
        }
    }

    public void swap(int a, int b) {

        int temp;
        temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    public void print(){

        for (int i = 1; i <= size; i++) {
            System.out.print(heap[i] + " ");
        }
    }
    public int get_parent(int i) {

        return i/2;
    }
    public int get_left(int i) {

        return 2*i;
    }
    public int get_right(int i) {

        return 2*i + 1;
    }

    public static void main(String[] args) {

        String choice = "";

        MinHeap arr = new MinHeap(1);
        int size;
        boolean show = false;

        try( Scanner in = new Scanner(System.in)){
            while (!choice.equals("7")){
                System.out.println("\n\n");
                if (show){
                    System.out.println("Array: ");
                    arr.print();
                }


                System.out.println("\n1. Create an empty min-heap \n2. Enter integers and run build-heap \n3. Insert element \n4. Pop element \n5. Run heap-sort \n6. Compare heap-sort of length n with a standard library sorting algorithm \n7. Exit ");

                choice = in.nextLine();

                switch(choice){
                    case "1":
                        show = true;
                        System.out.println("Enter the max size of the heap: ");
                        size = in.nextInt();
                        arr = new MinHeap(size);

                        break;
                    case "2":
                        show = true;
                        System.out.println("Enter the size of the heap: ");
                        int len = in.nextInt();
                        int elements[] = userEnteredArray();
                        arr = new MinHeap(len);
                        for (int i: elements){
                            arr.insert(i);
                        }
                        arr.build_heap();
                        break;
                    case "3":
                        System.out.println("Enter the element you want to insert: ");
                        int element = in.nextInt();
                        if (show){
                            arr.insert(element);
                            arr.build_heap();
                        }
                        else{
                            System.out.println("Min Heap not initialized");
                        }
                        break;
                    case "4":
                        if (show){
                            arr.pop();
                        }
                        else {
                            System.out.println("Min Heap not initialized");
                        }
                        break;
                    case "5":
                        if (show){
                            arr.heap_sort();
                        }
                        else {
                            System.out.println("Min Heap not initialized");
                        }
                        break;
                    case "6":
                        System.out.println(" Enter length n: ");
                        int n = in.nextInt();
                        int array[] = random_gen_array(n);
                        test_runtimes(array);


                        break;
                    case "7":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid input.");
                        break;

                }
            }
        }

    }

    public static int[] userEnteredArray() {
        Scanner in = new Scanner(System.in);
        //get user input
        System.out.print("Enter an array of integers (1, 2, 3, 4, 5): ");
        String userArray = in.nextLine();

        //adjust user input to be a list of only commas and ints
        Pattern cleanArray = Pattern.compile("[^-0-9,]");
        Matcher arrayMatcher = cleanArray.matcher(userArray);

        userArray = arrayMatcher.replaceAll("");

        //convert string array to int array
        String[] strArray = userArray.split(",");
        int[] intArray = new int[strArray.length];

        for (int i = 0; i < intArray.length; i++) {

            intArray[i] = Integer.parseInt(strArray[i]);

        }

        return intArray;

    }

    public static int[] random_gen_array(int n) {
        Random rand = new Random();

        int[] intArray = new int[n];

        //generate random values for the array
        for (int i = 0; i < intArray.length; i++) {

            intArray[i] = ThreadLocalRandom.current().nextInt(-1000, 1001);

        }

        return intArray;

    }

    public static void test_runtimes(int[] array){
        long startTime;
        long endTime;
        long nano_runtime;

        int len = array.length;
        MinHeap arr1 = new MinHeap(len);
        for (int i: array){
            arr1.insert(i);
        }
        arr1.build_heap();

        startTime = System.nanoTime();
        arr1.heap_sort();
        endTime = System.nanoTime();
        nano_runtime = (endTime - startTime);
        System.out.println("\nHeap Sort: ");
        print_runtime(nano_runtime);

        startTime = System.nanoTime();
        Arrays.sort(array);
        endTime = System.nanoTime();
        nano_runtime = (endTime - startTime);
        System.out.println("\nLibrary Sort: ");
        print_runtime(nano_runtime);

    }

    public static double print_runtime(long nano){
        double d_nano = nano;
        double milli = d_nano/1000000;
        double seconds = milli/1000;

        if(milli >1000){
            System.out.println("Runtime: "+ seconds + " seconds");
        }
        else{
            System.out.println("Runtime: "+ milli +" milliseconds");
        }
        return milli;
    }
}
