package com.ims.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ims.dao.StudentDao;
import com.ims.model.PageBean;
import com.ims.model.Student;
import com.ims.util.PageUtil;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * @author: GaoXu
 * @date: 2020/5/24
 * @desc：请对类进行描述
 */
public class StudentAction extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private HttpServletRequest request;

    private StudentDao studentDao = new StudentDao();

    private String mainPage;
    private Student student;
    private String error;
    private List<Student> studentList;
    private String page;
    private int total;
    private String pageCode;

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
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

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
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

    public String preUpdatePassword() throws Exception {
        mainPage = "student/updatePassword.jsp";
        return SUCCESS;
    }

    public String updatePassword() throws Exception {
        Student s = studentDao.getStudentById(student.getId());
        s.setPassword(student.getPassword());
        studentDao.saveStudent(s);
        mainPage = "student/updateSuccess.jsp";
        return SUCCESS;
    }

    public String logout() throws Exception {
        request.getSession().invalidate();
        return "logout";
    }

    public String list() throws Exception {
        HttpSession session=request.getSession();
        if(StringUtils.isEmpty(page)){
            page="1";
        }
        if(student==null){
            Object o=session.getAttribute("student");
            if(o!=null){
                student=(Student)o;
            }else{
                student=new Student();
            }
        }else{
            session.setAttribute("student", student);
        }
        PageBean pageBean=new PageBean(Integer.parseInt(page),3);
        studentList=studentDao.getStudents(student,pageBean);
        total=studentDao.studentCount(student);
        pageCode= PageUtil.genPagination(request.getContextPath()+"/student!list", total, Integer.parseInt(page), 3);
        mainPage="student/studentList.jsp";
        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}
