package com.ims.dao;

import com.ims.model.Student;
import com.ims.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;


public class StudentDao {

    public Student login(Student student) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Student as s where s.id=:id and s.password=:password");
        query.setString("id", student.getId());
        query.setString("password", student.getPassword());
        Student resultStudent = (Student) query.uniqueResult();
        session.getTransaction().commit();
        return resultStudent;
    }
}
