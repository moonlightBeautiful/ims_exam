package com.ims.dao;

import com.ims.model.Question;
import com.ims.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

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
}
