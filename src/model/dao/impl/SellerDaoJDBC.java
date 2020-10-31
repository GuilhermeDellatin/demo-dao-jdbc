package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/*Observação: Nossa classe Dao é responsável por pegar os dados do banco e transformar em objetos associados.*/
public class SellerDaoJDBC implements SellerDao {

    //Nosso Dao vai ter uma dependencia com a conexão
    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("Select seller.* ,department.name as depname from seller inner join department\n"
                    + "\ton seller.departmentid = department.id where seller.id = ?");

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            //Para testar se veio algum resultado, se a consulta não retornar nenhuma registro
            //Ela pula o if e retorna nulo.
            if (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("departmentid"));
                department.setName(resultSet.getString("depname"));

                Seller seller = new Seller();
                seller.setId(resultSet.getInt("id"));
                seller.setName(resultSet.getString("name"));
                seller.setEmail(resultSet.getString("email"));
                seller.setBaseSalary(resultSet.getDouble("basesalary"));
                seller.setBirthDate(resultSet.getDate("birthdate"));
                seller.setDepartment(department);
                return seller;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }


    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
