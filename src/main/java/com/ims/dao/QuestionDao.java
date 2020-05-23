package com.ims.dao;

import com.ims.model.Question;
import com.ims.util.HibernateUtil;
import org.hibernate.Session;

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
}
