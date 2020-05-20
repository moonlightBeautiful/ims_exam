package com.ims.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ims.dao.StudentDao;
import com.ims.model.Student;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class StudentAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private HttpServletRequest request;

    private StudentDao studentDao = new StudentDao();

    private Student student;

    private String error;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Student getStudent() {
        return student;
    }


    public void setStudent(Student student) {
        this.student = student;
    }


    public String getError() {
        return error;
    }


    public void setError(String error) {
        this.error = error;
    }

    public String login() throws Exception {
        Student currentUser = studentDao.login(student);
        if (currentUser == null) {
            error = "准考证号或密码错误";
            return ERROR;
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", currentUser);
            return SUCCESS;
        }
    }
}
