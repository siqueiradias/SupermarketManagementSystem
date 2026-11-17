
package supermarketmanagementsystem;

import java.util.Scanner;

/**
 *
 * @author diogodias
 */
public class SupermarketManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Please enter the filename to read: ");
        String filename = sc.nextLine();

        EmployeeDatabase database = new EmployeeDatabase();

        if (database.loadFromFile(filename)) {
            System.out.println("File read successfully");
            System.out.println("Total employees loaded: " + database.getCount());

            // Temporary check - will be removed once the menu is built
            for (Employee employee : database.getEmployees()) {
                System.out.println(employee);
            }
        } else {
            System.out.println("File not found: " + filename);
        }
    }

}
