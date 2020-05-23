package com.ims.action;

import com.ims.dao.ExamDao;
import com.ims.dao.QuestionDao;
import com.ims.model.Exam;
import com.ims.model.Question;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: GaoXu
 * @date: 2020/5/23 22:37
 */
public class ExamAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private ExamDao examDao = new ExamDao();
    private QuestionDao questionDao = new QuestionDao();

    private HttpServletRequest request;

    private String mainPage;
    private Exam exam;
    private List<Exam> examList;

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
        examList = examDao.getExams(exam);
        mainPage = "exam/myExam.jsp";
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
