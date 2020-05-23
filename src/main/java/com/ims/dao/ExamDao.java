package com.ims.dao;

import com.ims.model.Exam;
import com.ims.model.Question;
import com.ims.util.HibernateUtil;
import org.hibernate.Session;

/**
 * @description:
 * @author: GaoXu
 * @date: 2020/5/23 23:05
 */
public class ExamDao {

    /**
     * 保存考试
     *
     * @param exam 要保存的考试
     * @return
     */
    public void saveExam(Exam exam) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(exam);
        session.getTransaction().commit();
    }
}
