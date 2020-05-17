package com.ims.action;

import java.util.ArrayList;
import java.util.List;

import com.ims.dao.PaperDao;
import com.ims.model.Paper;
import com.opensymphony.xwork2.ActionSupport;

public class PaperAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private PaperDao paperDao = new PaperDao();

    private String mainPage;

    private List<Paper> paperList = new ArrayList<Paper>();

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

    public String list() {
        paperList = paperDao.getPapers();
        mainPage = "exam/selectPaper.jsp";
        return SUCCESS;
    }


}
