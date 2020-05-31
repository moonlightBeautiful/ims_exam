package com.ims.action;

import com.ims.dao.ExamDao;
import com.ims.dao.QuestionDao;
import com.ims.model.Exam;
import com.ims.model.PageBean;
import com.ims.model.Question;
import com.ims.util.PageUtil;
import com.opensymphony.xwork2.ActionSupport;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: GaoXu
 * @date: 2020/5/24
 * @desc：请对类进行描述
 */
public class ExamAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private ExamDao examDao = new ExamDao();
    private QuestionDao questionDao = new QuestionDao();

    private HttpServletRequest request;

    private String mainPage;
    private Exam exam;
    private List<Exam> examList;
    private String page;
    private int total;
    private String pageCode;

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<Exam> getExamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
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

    public String saveExam() throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> parameterMapEntryIterator = parameterMap.entrySet().iterator();
        //总分，单选，多选
        int totalScore = 0, singleScore = 0, moreScore = 0;
        while (parameterMapEntryIterator.hasNext()) {
            Map.Entry<String, String[]> parameterMapEntry = parameterMapEntryIterator.next();
            String key = parameterMapEntry.getKey();
            String[] values = parameterMapEntry.getValue();
            if ("exam.student.id".equals(key) || "exam.paper.id".equals(key)) {
                continue;
            }
            String questionId = "";
            String answer = "";
            /*
             * 单选题 or 多选题
             */
            if ("r".equals(key.split("-")[1])) {
                questionId = key.split("-")[2];
                answer = values[0];
                singleScore += this.calScore(questionId, answer, "1");
            } else {
                questionId = key.split("-")[2];
                for (String s : values) {
                    answer += s + ",";
                }
                answer = answer.substring(0, answer.length() - 1);
                moreScore += this.calScore(questionId, answer, "2");
            }
        }
        totalScore = singleScore + moreScore;
        exam.setSingleScore(singleScore);
        exam.setMoreScore(moreScore);
        exam.setScore(totalScore);
        exam.setExamDate(new Date());

        examDao.saveExam(exam);
        mainPage = "exam/examResult.jsp";
        return SUCCESS;
    }

    public String getExams() throws Exception {
        examList = examDao.getExams(exam,null);
        mainPage = "exam/myExam.jsp";
        return SUCCESS;
    }

    public String list() throws Exception {
        HttpSession session = request.getSession();
        if (StringUtils.isEmpty(page)) {
            page = "1";
        }
        if (exam == null) {
            Object o = session.getAttribute("exam");
            if (o != null) {
                exam = (Exam) o;
            } else {
                exam = new Exam();
            }
        } else {
            session.setAttribute("exam", exam);
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
        examList = examDao.getExams(exam, pageBean);
        total = examDao.examCount(exam);
        pageCode = PageUtil.genPagination(request.getContextPath() + "/exam!list", total, Integer.parseInt(page), 3);
        mainPage = "exam/examList.jsp";
        return SUCCESS;
    }

    private int calScore(String questionId, String userAnswer, String type) throws Exception {
        Question question = questionDao.getQuestionById(questionId);
        if (userAnswer.equals(question.getAnswer())) {
            if ("1".equals(type)) {
                return 20;
            } else {
                return 30;
            }
        } else {
            return 0;
        }
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }
}
