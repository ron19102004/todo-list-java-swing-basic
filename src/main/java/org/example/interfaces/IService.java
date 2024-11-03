package org.example.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IService<E> {
    ArrayList<E> findAll(long id) throws SQLException;
    E findById(long id) throws SQLException;
    void save(E e) throws SQLException;
    void update(E e) throws SQLException;
    void delete(long id) throws SQLException;
}
