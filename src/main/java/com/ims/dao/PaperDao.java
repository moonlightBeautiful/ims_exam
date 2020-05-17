package com.ims.dao;

import java.util.List;

import com.ims.model.Paper;
import com.ims.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;


public class PaperDao {

    public List<Paper> getPapers() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Paper");
        return (List<Paper>) query.list();
    }
}
