package com.ims.action;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.dao.PaperDao;
import com.ims.dao.QuestionDao;
import com.ims.model.PageBean;
import com.ims.model.Paper;
import com.ims.model.Question;
import com.ims.util.PageUtil;
import com.ims.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private HttpServletRequest request;
    private QuestionDao questionDao = new QuestionDao();
    private PaperDao paperDao = new PaperDao();

    private String mainPage;
    private List<Question> questionList;
    private Question question;
    private String page;
    private int total;
    private String pageCode;
    private String questionId;
    private List<Paper> paperList;

    public List<Question> getQuestionList() {
        return questionList;
    }

    private String title;

    public List<Paper> getPaperList() {
        return paperList;
    }

    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Question getQuestion() {
        return question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getQuestionById() throws Exception {
        question = questionDao.getQuestionById(questionId);
        mainPage = "question/questionShow.jsp";
        return SUCCESS;
    }

    public String delete() throws Exception {
        question = questionDao.getQuestionById(questionId);
        questionDao.delete(question);
        Map resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseUtil.write(objectMapper.writeValueAsString(resultMap), ServletActionContext.getResponse());
        return null;
    }

    public String preSave() throws Exception {
        paperList = paperDao.getPapers();
        if (StringUtils.isNotEmpty(questionId)) {
            question = questionDao.getQuestionById(questionId);
            title = "修改试卷信息";
        } else {
            title = "添加试卷信息";
        }
        mainPage = "question/questionSave.jsp";
        return SUCCESS;
    }

    public String save() throws Exception {
        if (StringUtils.isNotEmpty(questionId)) {
            question.setId(Integer.parseInt(questionId));
        }
        questionDao.save(question);
        return "save";
    }

}
