import java.util.HashMap;

public class Ex11_20220808049 {
    public static void main(String[] args) {
        System.out.println(subSequence("Welcome"));
        System.out.println(isSubstring("Welcome", "comen"));
        int[] arr = {-2, 0, 1, 3};

        System.out.println(numOfTriplets(arr, 2));
    }


    public static int numOfTriplets(int[] arr, int targetSum) {
        mergeSort(arr, 0, arr.length-1);
        int count = 0;

        for (int i = 0; i < arr.length - 2; i++) {
            int left = i + 1;
            int right = arr.length - 1;

            while (left < right) {
                int currentSum = arr[i] + arr[left] + arr[right];
                if (currentSum < targetSum) {
                    count += right - left;
                    left++;
                } else {
                    right--;
                }
            }
        }

        return count;
    }

    public static int kthSmallest(int[] arr, int k) {
        mergeSort(arr, 0, arr.length-1);
        return arr[k];
    }


    public static String subSequence(String str) {
        StringBuilder stb = new StringBuilder();
        stb.append(str.charAt(0));
        for (int i = 1; i < str.length() - 1; i++) {
            if (str.charAt(i) > stb.charAt(stb.length() - 1))
                stb.append(str.charAt(i));
        }
        System.out.println("n");
        return stb.toString();
    }

    public static int isSubstring(String str1, String str2) {
        if (str1.length() < str2.length())
            return -1;


        for (int i = 0; i < str1.length(); i++) {
            int str_counter = 0;
            boolean pointer = true;
            if (i + str2.length() <= str1.length()) {
                for (int j = i; j < i + str2.length(); j++) {
                    if (str1.charAt(j) != str2.charAt(str_counter++)) {
                        pointer = false;
                        break;
                    }
                }
            } else
                return -1;

            if (pointer)
                return i;

        }

        return -1;
    }

    public static void findRepeats(int[] arr, int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        StringBuilder stb = new StringBuilder();
        for (int j : arr) {
            map.put(j, map.getOrDefault(j, 0) + 1);
        }

        for (int k : map.keySet()) {
            int count = map.get(k);
            if (count > n)
                stb.append(k);
        }
        System.out.println(stb);
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);


            merge(arr, l, m, r);
        }
    }
}