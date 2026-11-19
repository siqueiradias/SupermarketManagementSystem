
package supermarketmanagementsystem;

/**
 * Administrative department (HR, Finance, Accounting).
 *
 * @author diogodias
 */
public class BackOfficeDepartment extends Department {

    public BackOfficeDepartment(String departmentName) {
        super(departmentName);
    }

    @Override
    public String getDepartmentType() {
        return "Back-Office Department";
    }
}