package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    //A classe vai expor um método que retorna um tipo da interface
    //Mas internamente ela vai instanciar uma implementação
    //É um macete para não expor a implementação, deixar só a interface
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC();
    }



}
