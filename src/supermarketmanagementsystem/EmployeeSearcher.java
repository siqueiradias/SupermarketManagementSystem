
package supermarketmanagementsystem;

import java.util.ArrayList;

/**
 *
 * @author diogodias
 */
public class EmployeeSearcher {

    // Recursive Binary Search: finds an employee by full name in a list
    // that is already sorted alphabetically. Returns the index, or -1 if not found.
    // Based on the recursive Binary Search shown in class.
    public static int binarySearch(ArrayList<Employee> employees, int left, int right,
            String targetName) {

        // Base case: the search area is empty, so the name is not in the list
        if (left > right) {
            return -1;
        }

        // Find the middle index
        int mid = left + (right - left) / 2;

        // Compare the middle employee's name with the target name
        int comparison = employees.get(mid).compareNameTo(targetName);

        if (comparison == 0) {
            return mid; // Employee found
        }

        // If the middle name comes after the target, search the left half
        if (comparison > 0) {
            return binarySearch(employees, left, mid - 1, targetName);
        }
        // Otherwise, search the right half
        else {
            return binarySearch(employees, mid + 1, right, targetName);
        }
    }
}