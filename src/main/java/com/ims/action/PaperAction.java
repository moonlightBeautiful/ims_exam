package com.ims.action;

import java.util.*;

import com.ims.dao.PaperDao;
import com.ims.model.Paper;
import com.ims.model.Question;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author: GaoXu
 * @date: 2020/5/24
 * @desc：请对类进行描述
 */
public class PaperAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private PaperDao paperDao = new PaperDao();
    /**
     * 主要内容页
     */
    private String mainPage;

    /**
     * 所有的试卷
     */
    private List<Paper> paperList = new ArrayList<Paper>();
    /**
     * 试卷id
     */
    private String paperId;
    /**
     * 试卷
     */
    private Paper paper;
    /**
     * 单选
     */
    private List<Question> singleQuestionList = new ArrayList<Question>();
    /**
     * 多选
     */
    private List<Question> multipleQuestionList = new ArrayList<Question>();

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public List<Paper> getPaperList() {
        return paperList;
    }

    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public List<Question> getSingleQuestionList() {
        return singleQuestionList;
    }

    public void setSingleQuestionList(List<Question> singleQuestionList) {
        this.singleQuestionList = singleQuestionList;
    }

    public List<Question> getMultipleQuestionList() {
        return multipleQuestionList;
    }

    public void setMultipleQuestionList(List<Question> multipleQuestionList) {
        this.multipleQuestionList = multipleQuestionList;
    }

    public String list() {
        paperList = paperDao.getPapers();
        mainPage = "exam/selectPaper.jsp";
        return SUCCESS;
    }

    public String getPaperQuestions() throws Exception {
        paper = paperDao.getPaper(paperId);
        Set<Question> questionList = paper.getQuestions();
        Iterator<Question> it = questionList.iterator();
        while (it.hasNext()) {
            Question question = it.next();
            if ("1".equals(question.getType())) {
                singleQuestionList.add(question);
            } else {
                multipleQuestionList.add(question);
            }
        }
        singleQuestionList = this.getRandomQuestionList(singleQuestionList, 3);
        multipleQuestionList = this.getRandomQuestionList(multipleQuestionList, 2);
        mainPage = "exam/paper.jsp";
        return SUCCESS;
    }


    private List<Question> getRandomQuestionList(List<Question> questionList, int num) {
        List<Question> resultList = new ArrayList<Question>();
        Random random = new Random();
        if (num > 0) {
            for (int i = 1; i <= num; i++) {
                int n = random.nextInt(questionList.size());
                Question q = questionList.get(n);
                if (resultList.contains(q)) {
                    i--;
                } else {
                    resultList.add(q);
                }
            }
        }
        return resultList;
    }

}
