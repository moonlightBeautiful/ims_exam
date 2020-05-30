package com.ims.util;

public class PageUtil {


    /**
     * ����ҳ��html
     *
     * @param targetUrl   ҳ��ť����
     * @param totalNum    �ܼ�¼��
     * @param currentPage ��ǰҳ��
     * @param pageSize    ҳ���¼�ߴ�
     * @return
     */
    public static String genPagination(String targetUrl, int totalNum, int currentPage, int pageSize) {
        int totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        //��ҳ
        pageCode.append("<li><a href='" + targetUrl + "?page=1'>��ҳ</a></li>");
        //��һҳ
        if (currentPage == 1) {
            pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
        } else {
            pageCode.append("<li><a href='" + targetUrl + "?page=" + (currentPage - 1) + "'>��һҳ</a></li>");
        }
        //�м�ҳ  5��
        for (int i = currentPage - 2; i <= currentPage + 2; i++) {
            //�м�ҳ��Χ ��һҳ�����һҳ֮�䣬�����䡣
            if (i < 1 || i > totalPage) {
                continue;
            }
            if (i == currentPage) {
                pageCode.append("<li class='active'><a href='#'>" + i + "</a></li>");
            } else {
                pageCode.append("<li><a href='" + targetUrl + "?page=" + i + "'>" + i + "</a></li>");
            }
        }
        //��һҳ
        if (currentPage == totalPage) {
            pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
        } else {
            pageCode.append("<li><a href='" + targetUrl + "?page=" + (currentPage + 1) + "'>��һҳ</a></li>");
        }
        //βҳ
        pageCode.append("<li><a href='" + targetUrl + "?page=" + totalPage + "'>βҳ</a></li>");
        return pageCode.toString();
    }
}
