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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
                Department department = instanciateDepartment(resultSet);
                Seller seller = instanciateSeller(resultSet, department);
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
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement("Select seller.* ,department.name as depname from seller inner join department\n"
                    + "\ton seller.departmentid = department.id  order by name;");

            resultSet = statement.executeQuery();

            List<Seller> listSeller = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {
                //A lógica do Map implementada no findByDepartment também vai funcionar perfeitamente aqui para não repetir departamentos
                Department dept = map.get(resultSet.getInt("departmentid"));
                //Também posso reaproveitar a instanciação só chamando department em vez de declarar um novo Department dept..

                if(dept == null){
                    dept = instanciateDepartment(resultSet);
                    //Estou salvando no map caso o departamento não exista...
                    map.put(resultSet.getInt("departmentid"), dept);
                }
                Seller seller = instanciateSeller(resultSet, dept);
                listSeller.add(seller);
            }
            return listSeller;

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement("Select seller.* ,department.name as depname from seller inner join department\n"
                    + "\ton seller.departmentid = department.id where department.id = ? order by name;");

            statement.setInt(1, department.getId());
            resultSet = statement.executeQuery();

            List<Seller> listSeller = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {
                //Para não repetir o departamento, sempre que passar no while eu vou verificar se o departmentid ja existe
                //Se não existir ele retorna nulo..
                Department dept = map.get(resultSet.getInt("departmentid"));
                //Também posso reaproveitar a instanciação só chamando department em vez de declarar um novo Department dept..

                if(dept == null){
                     dept = instanciateDepartment(resultSet);
                     //Estou salvando no map caso o departamento não exista...
                     map.put(resultSet.getInt("departmentid"), dept);
                }
                Seller seller = instanciateSeller(resultSet, dept);
                listSeller.add(seller);
            }
            return listSeller;

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }


    private Seller instanciateSeller(ResultSet resultSet, Department department) throws SQLException{
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("id"));
        seller.setName(resultSet.getString("name"));
        seller.setEmail(resultSet.getString("email"));
        seller.setBaseSalary(resultSet.getDouble("basesalary"));
        seller.setBirthDate(resultSet.getDate("birthdate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instanciateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("departmentid"));
        department.setName(resultSet.getString("depname"));
        return department;
    }

}
