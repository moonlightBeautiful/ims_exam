package com.ims.dao;

import com.ims.model.Exam;
import com.ims.model.Question;
import com.ims.util.HibernateUtil;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

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

    public List<Exam> getExams(Exam s_exam) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        StringBuffer hql = new StringBuffer("from Exam exam");
        if (s_exam.getStudent() != null && StringUtils.isEmpty(s_exam.getStudent().getId())) {
            hql.append(" and exam.student.id='" + s_exam.getStudent().getId() + "'");
        }
        Query query = session.createQuery(hql.toString().replaceFirst("and", "where"));
        @SuppressWarnings("unchecked")
        List<Exam> examList = query.list();

        session.getTransaction().commit();
        return examList;
    }
}
