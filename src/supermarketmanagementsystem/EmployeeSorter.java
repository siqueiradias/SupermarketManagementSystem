package supermarketmanagementsystem;

import java.util.ArrayList;

/**
 * Recursive Merge Sort that orders employees alphabetically by full name.
 *
 * @author diogodias
 */
public class EmployeeSorter {

    // Recursive Merge Sort: sorts the employees alphabetically by full name.
    // Based on the index-based Merge Sort shown in class.
    public static void mergeSort(ArrayList<Employee> employees, int left, int right) {
        // Base case: a section with one element (or none) is already sorted
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(employees, left, mid);       // sort the left half
            mergeSort(employees, mid + 1, right);  // sort the right half

            merge(employees, left, mid, right);    // combine the two sorted halves
        }
    }

    // Merges two sorted sections of the list: [left..mid] and [mid+1..right].
    private static void merge(ArrayList<Employee> employees, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        // Temporary arrays for each half (L and R in the class example)
        Employee[] leftHalf = new Employee[leftSize];
        Employee[] rightHalf = new Employee[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftHalf[i] = employees.get(left + i);
        }
        for (int j = 0; j < rightSize; j++) {
            rightHalf[j] = employees.get(mid + 1 + j);
        }

        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = left;

        // Take the alphabetically smaller name from either half.
        // Using <= keeps the sort stable for equal names.
        while (leftIndex < leftSize && rightIndex < rightSize) {
            Employee leftEmployee = leftHalf[leftIndex];
            Employee rightEmployee = rightHalf[rightIndex];

            if (leftEmployee.compareNameTo(rightEmployee.getFullName()) <= 0) {
                employees.set(mergedIndex, leftEmployee);
                leftIndex++;
            } else {
                employees.set(mergedIndex, rightEmployee);
                rightIndex++;
            }
            mergedIndex++;
        }

        // Copy any remaining employees from the left half
        while (leftIndex < leftSize) {
            employees.set(mergedIndex, leftHalf[leftIndex]);
            leftIndex++;
            mergedIndex++;
        }

        // Copy any remaining employees from the right half
        while (rightIndex < rightSize) {
            employees.set(mergedIndex, rightHalf[rightIndex]);
            rightIndex++;
            mergedIndex++;
        }
    }
}
