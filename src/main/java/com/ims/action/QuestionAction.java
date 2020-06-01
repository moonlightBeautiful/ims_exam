package com.ims.action;


import com.ims.dao.QuestionDao;
import com.ims.model.PageBean;
import com.ims.model.Question;
import com.ims.util.PageUtil;
import com.opensymphony.xwork2.ActionSupport;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class QuestionAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private HttpServletRequest request;

    private QuestionDao questionDao = new QuestionDao();

    private List<Question> questionList;

    private String mainPage;

    private Question question;

    private String page;
    private int total;
    private String pageCode;


    public List<Question> getQuestionList() {
        return questionList;
    }


    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }


    public String getMainPage() {
        return mainPage;
    }


    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }


    public Question gettQuestion() {
        return question;
    }


    public void setQuestion(Question s_question) {
        this.question = s_question;
    }


    public String getPage() {
        return page;
    }


    public void setPage(String page) {
        this.page = page;
    }


    public int getTotal() {
        return total;
    }


    public void setTotal(int total) {
        this.total = total;
    }


    public String getPageCode() {
        return pageCode;
    }


    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }


    @Override
    public void setServletRequest(HttpServletRequest request) {
        // TODO Auto-generated method stub
        this.request = request;
    }

    public String list() throws Exception {
        HttpSession session = request.getSession();
        if (StringUtils.isEmpty(page)) {
            page = "1";
        }
        if (question == null) {
            Object o = session.getAttribute("s_question");
            if (o != null) {
                question = (Question) o;
            } else {
                question = new Question();
            }
        } else {
            session.setAttribute("s_question", question);
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
        questionList = questionDao.getQuestions(question, pageBean);
        total = questionDao.questionCount(question);
        pageCode = PageUtil.genPagination(request.getContextPath() + "/question!list", total, Integer.parseInt(page), 3);
        mainPage = "question/questionList.jsp";
        return SUCCESS;
    }
}
