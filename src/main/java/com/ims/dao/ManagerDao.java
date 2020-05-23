package com.ims.dao;

import com.ims.model.Manager;
import com.ims.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;


public class ManagerDao {

    public Manager login(Manager manager) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Manager as m where m.userName=:userName and m.password=:password");
        query.setString("userName", manager.getUserName());
        query.setString("password", manager.getPassword());
        Manager resultManager = (Manager) query.uniqueResult();
        session.getTransaction().commit();
        return resultManager;
    }
}
