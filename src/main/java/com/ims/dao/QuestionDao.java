package com.ims.dao;

import com.ims.model.PageBean;
import com.ims.model.Question;
import com.ims.util.HibernateUtil;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

/**
 * @description:
 * @author: GaoXu
 * @date: 2020/5/23 23:05
 */
public class QuestionDao {

    /**
     * 通过问题id获取问题对象
     *
     * @param questionId
     * @return
     */
    public Question getQuestionById(String questionId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Question question = (Question) session.get(Question.class, Integer.parseInt(questionId));
        session.getTransaction().commit();
        return question;
    }

    public boolean existQuestionByPaperId(String paperId) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("from Question as q where q.paper.id=:paperId");
        query.setString("paperId", paperId);
        @SuppressWarnings("unchecked")
        List<Question> questionList = (List<Question>) query.list();

        session.getTransaction().commit();
        if (questionList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Question> getQuestions(Question s_question, PageBean pageBean) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        StringBuffer hql = new StringBuffer("from Question");
        if (StringUtils.isNotEmpty(s_question.getSubject())) {
            hql.append(" and subject like '%" + s_question.getSubject() + "%'");
        }
        Query query = session.createQuery(hql.toString().replaceFirst("and", "where"));
        if (pageBean != null) {
            query.setFirstResult(pageBean.getStart());
            query.setMaxResults(pageBean.getPageSize());
        }
        @SuppressWarnings("unchecked")
        List<Question> questionList = (List<Question>) query.list();
        session.getTransaction().commit();
        return questionList;
    }

    public int questionCount(Question s_question) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        StringBuffer sql = new StringBuffer("select count(*) from t_question");
        if (StringUtils.isNotEmpty(s_question.getSubject())) {
            sql.append(" and subject like '%" + s_question.getSubject() + "%'");
        }
        Query query = session.createSQLQuery(sql.toString().replaceFirst("and", "where"));
        int count = ((BigInteger) query.uniqueResult()).intValue();
        session.getTransaction().commit();
        return count;
    }

    public void delete(Question question) throws Exception {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(question);
        session.getTransaction().commit();
    }
}
