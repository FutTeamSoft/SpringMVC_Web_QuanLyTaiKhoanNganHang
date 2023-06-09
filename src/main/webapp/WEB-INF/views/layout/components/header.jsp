<%-- 
    Document   : header
    Created on : Mar 13, 2023, 4:41:31 PM
    Author     : dinhp
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="<c:url value="/assets/css/bootstrap.min.css" />"/>
        <link rel="stylesheet" href="<c:url value="/assets/fontawesome-free-6.3.0-web/css/all.min.css" />"/>
        <link rel="stylesheet" href="<c:url value="/assets/css/main.css" />"/>
        <link rel="stylesheet" href="<c:url value="/assets/css/base.css" />"/>


        <title>${pageTitle}</title>
    </head>
    <body class="nav-fixed">
        <nav class="topnav navbar navbar-dark navbar-expand bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand" href="<c:url value="/home"/>">FTeam Bank</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-lg order-1 order-lg-0 mr-xl-auto" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <ul class="navbar-nav ml-auto mr-3 mr-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-user fa-fw"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="<c:url value="/auth/change_password"/>">Đổi mật khẩu</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="<c:url value="/auth/logout"/>">Đăng xuất</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sidenav">
                    <div class="sidenav-menu">
                        <div class="nav">
                            <% if (session.getAttribute("chucvu").equals(1)) { %>
                            <div class="sidenav-menu-heading">Thống kê</div>
                            <a class="nav-link" href="<c:url value="/home/statistical"/>">
                                <div class="nav-link-icon"><i class="fas fa-chart-area"></i></div>
                                Thống kê
                            </a>
                            <% }%>
                            <div class="sidenav-menu-heading">Quản lý</div>    
                            <% if (session.getAttribute("chucvu").equals(1)) { %>
                            <a class="nav-link" href="<c:url value="/home/staff_management"/>">
                                <div class="nav-link-icon"><i class="fa-solid fa-clipboard-user"></i></div>
                                Nhân viên
                            </a>
                            <% }%>
                            <% if (session.getAttribute("chucvu").equals(2)) { %>
                            <a class="nav-link" href="<c:url value="/home/customer_management"/>">
                                <div class="nav-link-icon"><i class="fa-solid fa-users"></i></div>
                                Khách hàng
                            </a>
                            <div class="sidenav-menu-heading">Chức năng</div>

                            <a class="nav-link" href="<c:url value="/home/bank_card_management"/>">
                                <div class="nav-link-icon"><i class="fa-solid fa-money-check"></i></div>
                                Dịch vụ thẻ
                            </a>
                            <a class="nav-link" href="<c:url value="/home/transaction_money"/>">
                                <div class="nav-link-icon"><i class="fa-solid fa-coins"></i></div>
                                Nạp tiền/ Rút tiền
                            </a>
                            <a class="nav-link" href="<c:url value="/home/transfer_money"/>">
                                <div class="nav-link-icon"><i class="fa-solid fa-money-bill-transfer"></i></div>
                                Chuyển khoản
                            </a>
                            <a class="nav-link" href="<c:url value="/home/transaction_history"/>">
                                <div class="nav-link-icon"><i class="fa-solid fa-file-invoice"></i></div>
                                Sao kê
                            </a>

                            <% }%>

                        </div>
                    </div>
                    <div class="sidenav-footer">
                        <div class="small">Logged in as:</div>
                        <p>${sessionScope.Ten}
                        </p>
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>

