package com.feng.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 2827360195836107171L;
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("收到请求参数[" + request.getParameter("username") + "]");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<h2>这是我的嵌入式Jetty测试结果-->用户访问Servlet成功</h2>");
        out.flush();
        out.close();
    }
}