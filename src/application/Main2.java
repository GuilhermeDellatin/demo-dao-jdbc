package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TESTE 1: department findById ===");
        Department department = departmentDao.findById(1);
        System.out.println(department);


        System.out.println("\n=== TESTE 2: department findAll ===");
        List<Department> list = departmentDao.findAll();
        for (Department obj: list) {
            System.out.println(obj);
        }

        System.out.println("\n=== TESTE 3: department insert ===");
        Department newDepartment = new Department(12, "Developer");
        departmentDao.insert(newDepartment);
        System.out.println("Inserido, Novo id = " + newDepartment.getId());

        System.out.println("\n=== TESTE 4: department update ===");
        department = departmentDao.findById(5);
        department.setName("Testando");
        departmentDao.update(department);
        System.out.println("Update concluido");

        System.out.println("\n=== TESTE 5: department delete ===");
        System.out.println("Entre com um id para deletar um departamento: ");
        int id = scanner.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Departamento Deletado!");

        scanner.close();
    }
}
