package com.codegym.service;

import com.codegym.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductServiceDB implements IProductService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    @Override
    public int findMaxId() {
        return 0;
    }

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        //typedQuery
        String qr = "SELECT c FROM Product AS c";
        TypedQuery<Product> query = entityManager.createQuery(qr,Product.class);
        return query.getResultList();
    }

    @Override
    public void save(Product product) {
        Session session = null;
        Transaction transaction = null;
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(product);
        transaction.commit();
    }

    @Override
    public void update(int id, Product product) {

    }

    @Override
    public void delete(int id) {

    }
}
