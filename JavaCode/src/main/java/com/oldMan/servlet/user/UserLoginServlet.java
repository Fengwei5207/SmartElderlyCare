package com.oldMan.servlet.user;

import com.oldMan.bean.User;
import com.oldMan.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author XiaLuo
 * @version 1.0
 * @date 2024/1/2 10:23
 */
@WebServlet("/api/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");

        // 调用 DAO 中的方法进行登录验证
        UserDao userDao = new UserDao();
        User user = userDao.loginUser(phoneNumber, password);

        if (user != null) {
            // 登录成功，跳转到登录成功页面或其他页面
            HttpSession session = req.getSession();
            session.setAttribute("phoneNumber", user.getPhoneNumber());
            session.setAttribute("userName", user.getUserName());
            session.setAttribute("userRole", user.getUserRole());
            session.setAttribute("userId", String.valueOf(user.getUserId()));
            resp.sendRedirect("/index.html");
        } else {
            // 登录失败，返回登录页面并显示错误信息
            resp.sendRedirect("/login.html?error=invalid");
        }
    }
}
