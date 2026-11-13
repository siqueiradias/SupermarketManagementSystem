/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarketmanagementsystem;

/**
 *
 * @author diogodias
 */
public class OperationsDepartment extends Department {

    public OperationsDepartment(String departmentName) {
        super(departmentName);
    }

    @Override
    public String getDepartmentType() {
        return "Operations Department";
    }
}
