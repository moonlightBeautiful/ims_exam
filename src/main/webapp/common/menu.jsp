<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="navbar">
  <div class="navbar-inner">
    <a class="brand" href="main.jsp">首页</a>
    <ul class="nav">
      <li><a href="${pageContext.request.contextPath}/paper!list">在线考试</a></li>
      <li><a href="${pageContext.request.contextPath}/exam!getExams?exam.student.id=${currentUser.id}">成绩查询</a></li>
      <li><a href="#">修改密码</a></li>
      <li><a href="#">退出系统</a></li>
    </ul>
  </div>
</div>