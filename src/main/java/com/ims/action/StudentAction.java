package com.ims.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.dao.StudentDao;
import com.ims.model.PageBean;
import com.ims.model.Student;
import com.ims.util.PageUtil;
import com.ims.util.ResponseUtil;
import freemarker.template.utility.DateUtil;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        HttpSession session = request.getSession();
        if (StringUtils.isEmpty(page)) {
            page = "1";
        }
        if (student == null) {
            Object o = session.getAttribute("student");
            if (o != null) {
                student = (Student) o;
            } else {
                student = new Student();
            }
        } else {
            session.setAttribute("student", student);
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
        studentList = studentDao.getStudents(student, pageBean);
        total = studentDao.studentCount(student);
        pageCode = PageUtil.genPagination(request.getContextPath() + "/student!list", total, Integer.parseInt(page), 3);
        mainPage = "student/studentList.jsp";
        return SUCCESS;
    }

    public String preSave() throws Exception {
        if (StringUtils.isNotEmpty(student.getId())) {
            student = studentDao.getStudentById(student.getId());
            title = "修改学生信息";
        } else {
            title = "添加学生信息";
        }
        mainPage = "student/studentSave.jsp";
        return SUCCESS;
    }

    public String save() throws Exception {
        if (StringUtils.isEmpty(student.getId())) {
            student.setId("SD" + DateFormatUtils.format(new Date(), "yyyyMMddhhmmss"));
        }
        studentDao.saveStudent(student);
        return "save";
    }

    public String delete() throws Exception {
        student = studentDao.getStudentById(student.getId());
        studentDao.studentDelete(student);
        Map resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseUtil.write(objectMapper.writeValueAsString(resultMap), ServletActionContext.getResponse());
        return null;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}
