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
        List<Paper> paperList = (List<Paper>) query.list();
        session.getTransaction().commit();
        return paperList;
    }

    public Paper getPaper(String paperId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Paper paper = (Paper) session.get(Paper.class, Integer.parseInt(paperId));
        session.getTransaction().commit();
        return paper;
    }

    public void delete(Paper paper) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(paper);
        session.getTransaction().commit();
    }
}
