package com.ims.util;

public class PageUtil {


    /**
     * 生成页码html
     *
     * @param targetUrl   页按钮请求
     * @param totalNum    总记录数
     * @param currentPage 当前页码
     * @param pageSize    页面记录尺寸
     * @return
     */
    public static String genPagination(String targetUrl, int totalNum, int currentPage, int pageSize) {
        int totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        //首页
        pageCode.append("<li><a href='" + targetUrl + "?page=1'>首页</a></li>");
        //上一页
        if (currentPage == 1) {
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        } else {
            pageCode.append("<li><a href='" + targetUrl + "?page=" + (currentPage - 1) + "'>上一页</a></li>");
        }
        //中间页  5个
        for (int i = currentPage - 2; i <= currentPage + 2; i++) {
            //中间页范围 第一页和最后一页之间，开区间。
            if (i < 1 || i > totalPage) {
                continue;
            }
            if (i == currentPage) {
                pageCode.append("<li class='active'><a href='#'>" + i + "</a></li>");
            } else {
                pageCode.append("<li><a href='" + targetUrl + "?page=" + i + "'>" + i + "</a></li>");
            }
        }
        //下一页
        if (currentPage == totalPage) {
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        } else {
            pageCode.append("<li><a href='" + targetUrl + "?page=" + (currentPage + 1) + "'>下一页</a></li>");
        }
        //尾页
        pageCode.append("<li><a href='" + targetUrl + "?page=" + totalPage + "'>尾页</a></li>");
        return pageCode.toString();
    }
}
