package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

/*As repetições dos departamentos que aparecem na execução estão corretas
  Estão de acordo com o diagrama e o que queremos exibir no resultado do programa */

public class Main {

    public static void main(String[] args) {

        /*
        Department department = new Department(1, "Books");
        Seller seller = new Seller(1, "Bob", "bob@gmail.com", new Date(), 3000.0, department);
        */
        //Forma para instanciar o DAO, não vamos chamar um new nesse caso chamamos a DaoFactory
        //Também é a forma de fazer uma injeção de dependencia sem explicitar a implementação
        System.out.println("=== TESTE 1: seller findById ===");
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TESTE 2: seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj: list) {
            System.out.println(obj);
        }

        System.out.println("\n=== TESTE 3: seller findAll ===");
        list = sellerDao.findAll();
        for (Seller obj: list) {
            System.out.println(obj);
        }

        System.out.println("\n=== TESTE 4: seller insert ===");
        Seller newSeller = new Seller(10, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserido, Novo id = " + newSeller.getId());

    }
}
