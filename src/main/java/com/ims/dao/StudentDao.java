package com.ims.dao;

import com.ims.model.PageBean;
import com.ims.model.Student;
import com.ims.util.HibernateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;


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

    public Student getStudentById(String id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Student student = (Student) session.get(Student.class, id);
        session.getTransaction().commit();
        return student;
    }

    public void saveStudent(Student student) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(student);
        session.getTransaction().commit();
    }

    public List<Student> getStudents(Student s_student, PageBean pageBean) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        StringBuffer hql = new StringBuffer("from Student");
        if (StringUtils.isNotEmpty(s_student.getId())) {
            hql.append(" and id like '%" + s_student.getId() + "%'");
        }
        if (StringUtils.isNotEmpty(s_student.getName())) {
            hql.append(" and name like '%" + s_student.getName() + "%'");
        }
        Query query = session.createQuery(hql.toString().replaceFirst("and", "where"));
        if (pageBean != null) {
            query.setFirstResult(pageBean.getStart());
            query.setMaxResults(pageBean.getPageSize());
        }
        @SuppressWarnings("unchecked")
        List<Student> studentList = (List<Student>) query.list();
        session.getTransaction().commit();
        return studentList;
    }

    public int studentCount(Student student) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        StringBuffer sql = new StringBuffer("select count(*) from t_student");
        if (StringUtils.isNotEmpty(student.getId())) {
            sql.append(" and id like '%" + student.getId() + "%'");
        }
        if (StringUtils.isNotEmpty(student.getName())) {
            sql.append(" and name like '%" + student.getName() + "%'");
        }
        Query query = session.createSQLQuery(sql.toString().replaceFirst("and", "where"));
        int count = ((BigInteger) query.uniqueResult()).intValue();
        session.getTransaction().commit();
        return count;
    }

    public void studentDelete(Student student) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(student);
        session.getTransaction().commit();
    }

}
