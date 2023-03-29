/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fteam.controllers;

import com.fteam.models.NhanVien;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author dinhp
 */
@Controller
public class AuthController {

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Login(ModelMap model) {
        model.addAttribute("NhanVien", new NhanVien());
        return "login";
    }

    @Transactional
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, ModelMap model, @ModelAttribute("NhanVien") NhanVien NhanVien) {
        Session session = sessionFactory.openSession();
        String hql = "FROM NhanVien u WHERE u.tenDangNhap = :tenDangNhap AND u.matKhau = :matKhau";
        Query query = session.createQuery(hql);

        query.setParameter("tenDangNhap", NhanVien.getTenDangNhap());
        query.setParameter("matKhau", NhanVien.getMatKhau());

        List<NhanVien> list = query.list();
        if (!list.isEmpty()) {
            // Login successful
            NhanVien loggedInNhanVien = list.get(0);
            model.addAttribute("loggedInNhanVien", loggedInNhanVien);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("Ten", loggedInNhanVien.getTenNhanVien());
            httpSession.setAttribute("chucvu", loggedInNhanVien.getChucVu().getId());

            return "index";
        } else {
            // Login failed
            model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng !");
            return "login";
        }
    }

    @RequestMapping(value = "register")
    public String Register(ModelMap model) {
        model.addAttribute("NhanVien", new NhanVien());
        return "register";

    }

    @Transactional
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String Register(HttpServletRequest request, ModelMap model, @ModelAttribute("NhanVien") NhanVien NhanVien) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            tx = session.beginTransaction();
            session.save(NhanVien);
            tx.commit();
            model.addAttribute("message", "Thêm mới thành công !");
        } catch (HibernateException e) {           
            if (tx != null) {
                tx.rollback();
            }           
            e.printStackTrace();
            model.addAttribute("message", "Thêm mới thất bại !");
        } finally {
            session.close();
        }
        return "staff_management";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        // code xử lý đăng xuất
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // xóa session
        }
        return "redirect:/login"; // chuyển hướng tới trang đăng nhập
    }
}
