package com.self.SpringJDBCdemo.repository;


import com.self.SpringJDBCdemo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate template;

    public int saveUser(Users user) {
        String sql = "insert into users (name, age) values (?, ?)";
        return template.update(sql,user.getName(),(user.getAge()));
    }

    public List<Users> fetchAllUsers() {
        String sql = "select * from users";

//RowMapper is a functional interface: so lambda expression is used directly inside query method
        //public interface RowMapper<T> {
        //    T mapRow(ResultSet rs, int rowNum) throws SQLException;
        //}
        return template.query(sql,(rs,rowNum) -> {
            Users user = new Users();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            return user;
        });
    }

    public Users fetchUserById(int id) {

        String sql = "select * from users where id = ?";

        try {
            return template.queryForObject(sql, (rs, rowNum) -> {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                return user;
            },id);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public Object updateUser(int id, Users data) {
        String sql = "update users set name = ?, age = ? where id = ?";
        return template.update(sql,data.getName(),data.getAge(),id);
    }

    public Object deleteUser(int id) {
        String sql = "delete from users where id = ?";
        return template.update(sql,id);
    }
}
