/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fteam.controllers;

import com.fteam.models.ChucVu;
import com.fteam.models.NhanVien;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dinhp
 */
@Controller
@RequestMapping("/home/")
public class StaffController {

    private String messageStaff = null;
    private String messageSuccessStaff = null;


    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @RequestMapping(value = "staff_management", method = RequestMethod.GET)
    public String searchStaff(HttpSession httpSession, HttpServletRequest request, ModelMap model) {
        model.addAttribute("pageTitle", "Quản lý nhân viên");
        if(messageStaff != null ||messageSuccessStaff !=null ){
            model.addAttribute("messageStaff", messageStaff);
            model.addAttribute("messageSuccessStaff", messageSuccessStaff);
            messageStaff=null;
            messageSuccessStaff=null;
        }

        String keyword = request.getParameter("searchstaff");
        if (keyword == null) {
            keyword = "";
        }
        try ( Session session = sessionFactory.openSession()) {

            String hql = "SELECT nv FROM NhanVien nv JOIN FETCH nv.chucVu WHERE lower(nv.tenNhanVien) LIKE :keyword";
            Query query = session.createQuery(hql);
            query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            List<NhanVien> nhanviens = query.list();
            List<NhanVien> searchResult = new ArrayList<>();
            // Search for staff with matching name
            for (NhanVien nhanVien : nhanviens) {
                if (nhanVien.getTenNhanVien().toLowerCase().contains(keyword.toLowerCase())) {
                    searchResult.add(nhanVien);
                }
            }
            if (searchResult.isEmpty()) {
                model.addAttribute("messageStaff", "Không tìm thấy nhân viên!");
                return "home/staff_management";
            } else {
                model.addAttribute("nhanviens", searchResult);
                return "home/staff_management";
            }
        } catch (Exception e) {
            // Handle any exceptions that occur
            e.printStackTrace();
            model.addAttribute("messageStaff", "Có lỗi khi tìm kiếm nhân viên!");
            return "home/staff_management";
        }
    }

    @Transactional
    @RequestMapping(value = "staff_management/delete", method = RequestMethod.POST)
    public String deleteStaff(@RequestParam("deleteStaffId") int staffId, HttpSession httpSession) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String updateQuery = "DELETE FROM NhanVien WHERE ID_NhanVien = :id";
            Query query = session.createQuery(updateQuery);
            query.setParameter("id", staffId);
            query.executeUpdate();
            System.out.print(staffId);
            tx.commit();
            messageSuccessStaff = "Xóa Thành Công!";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            messageStaff = "Không thể xóa tài khoản!";
        } finally {
            session.close();
        }
        return "redirect:/home/staff_management";
    }

    @Transactional
    @RequestMapping(value = "staff_management/edit", method = RequestMethod.POST)
    public String editStaff(@ModelAttribute("staff") NhanVien staff,
            HttpServletRequest request, ModelMap model,
            @RequestParam("editStaffId") int staffId,
            HttpSession httpSession) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int chucVuId = Integer.parseInt(request.getParameter("chucVu"));
            String editQuery = "UPDATE NhanVien SET TenNhanVien=:tenNhanVien, NamSinh=:namSinh, GioiTinh=:gioiTinh, ID_ChucVu=:chucVuId,"
                    + " DiaChi=:diaChi, Email=:email, SoDienThoai=:soDienThoai, TrangThaiTaiKhoan=:trangThaiTaiKhoan WHERE ID_NhanVien = :id";
            Query updateNhanVienQuery = session.createQuery(editQuery)
                    .setParameter("tenNhanVien", staff.getTenNhanVien())
                    .setParameter("chucVuId", chucVuId)
                    .setParameter("namSinh", staff.getNamSinh())
                    .setParameter("gioiTinh", staff.getGioiTinh())
                    .setParameter("diaChi", staff.getDiaChi())
                    .setParameter("email", staff.getEmail())
                    .setParameter("soDienThoai", staff.getSoDienThoai())
                    .setParameter("trangThaiTaiKhoan", staff.getTrangThaiTaiKhoan())
                    .setParameter("id", staffId);
            updateNhanVienQuery.executeUpdate();
            tx.commit();
            messageSuccessStaff = "Cập nhật thông tin nhân viên thành công!";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            messageStaff = "Không thể cập nhật thông tin nhân viên!";
        } finally {
            session.close();
        }
        return "redirect:/home/staff_management";
    }
}
