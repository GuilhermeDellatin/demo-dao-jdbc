package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        /*
        Department department = new Department(1, "Books");
        Seller seller = new Seller(1, "Bob", "bob@gmail.com", new Date(), 3000.0, department);
        */
        //Forma para instanciar o DAO, não vamos chamar um new nesse caso chamamos a DaoFactory
        //Também é a forma de fazer uma injeção de dependencia sem explicitar a implementação
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(3);


        System.out.println(seller);
    }
}
