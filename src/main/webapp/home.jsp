<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Hệ Thống Vé Tàu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        /* CSS cho Banner đẹp mắt */
        .hero-section {
            background: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), 
                        url('https://images.unsplash.com/photo-1474487548417-781cb714c2f0?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 100px 0;
            text-align: center;
        }
        .service-card {
            transition: transform 0.3s;
            cursor: pointer;
        }
        .service-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow sticky-top">
    <div class="container">
        <a class="navbar-brand fw-bold text-warning" href="home">
            <i class="fas fa-train"></i> VNR RAILWAY
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="home">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="schedule">Lịch trình</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="check-ticket">Kiểm tra vé</a>
                </li>
            </ul>

            <ul class="navbar-nav">
                
                <c:if test="${empty sessionScope.user}">
                    <li class="nav-item me-2">
                        <a href="register.jsp" class="btn btn-outline-light btn-sm">
                            <i class="fas fa-user-plus"></i> Đăng ký
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="login.jsp" class="btn btn-warning btn-sm fw-bold">
                            <i class="fas fa-sign-in-alt"></i> Đăng nhập
                        </a>
                    </li>
                </c:if>

                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-warning" href="#" role="button" data-bs-toggle="dropdown">
                            Hello, <b>${sessionScope.user.fullName}</b>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="profile">Thông tin tài khoản</a></li>
                            <li><a class="dropdown-item" href="history">Lịch sử đặt vé</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-danger" href="logout">
                                <i class="fas fa-sign-out-alt"></i> Đăng xuất
                            </a></li>
                        </ul>
                    </li>
                </c:if>

            </ul>
        </div>
    </div>
</nav>

<div class="hero-section">
    <div class="container">
        <h1 class="display-4 fw-bold">HÀNH TRÌNH VẠN DẶM</h1>
        <p class="lead mb-4">Đặt vé tàu trực tuyến nhanh chóng, tiện lợi và an toàn.</p>
        
        <a href="schedule" class="btn btn-warning btn-lg fw-bold px-5 py-3 rounded-pill shadow">
            <i class="fas fa-search"></i> TÌM VÉ TÀU NGAY
        </a>
    </div>
</div>

<div class="container my-5">
    <div class="text-center mb-5">
        <h2 class="fw-bold text-primary">DỊCH VỤ CỦA CHÚNG TÔI</h2>
        <p class="text-muted">Lựa chọn các chức năng bạn cần thao tác</p>
    </div>

    <div class="row g-4">
        <div class="col-md-3">
            <div class="card h-100 border-0 shadow-sm service-card text-center py-4">
                <div class="card-body">
                    <div class="fs-1 text-primary mb-3"><i class="fas fa-ticket-alt"></i></div>
                    <h5 class="card-title fw-bold">Đặt Vé Tàu</h5>
                    <p class="card-text small text-muted">Tìm kiếm lịch trình và chọn chỗ ngồi ưng ý.</p>
                    <a href="schedule" class="btn btn-outline-primary btn-sm stretched-link">Truy cập</a>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card h-100 border-0 shadow-sm service-card text-center py-4">
                <div class="card-body">
                    <div class="fs-1 text-success mb-3"><i class="fas fa-qrcode"></i></div>
                    <h5 class="card-title fw-bold">Kiểm Tra Vé</h5>
                    <p class="card-text small text-muted">Tra cứu thông tin vé điện tử đã đặt.</p>
                    <a href="check-ticket" class="btn btn-outline-success btn-sm stretched-link">Truy cập</a>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card h-100 border-0 shadow-sm service-card text-center py-4">
                <div class="card-body">
                    <div class="fs-1 text-warning mb-3"><i class="fas fa-credit-card"></i></div>
                    <h5 class="card-title fw-bold">Thanh Toán</h5>
                    <p class="card-text small text-muted">Thanh toán các vé giữ chỗ trả sau.</p>
                    <a href="history" class="btn btn-outline-warning btn-sm stretched-link">Truy cập</a>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card h-100 border-0 shadow-sm service-card text-center py-4">
                <div class="card-body">
                    <div class="fs-1 text-danger mb-3"><i class="fas fa-undo-alt"></i></div>
                    <h5 class="card-title fw-bold">Hủy / Trả Vé</h5>
                    <p class="card-text small text-muted">Thủ tục trả vé và hoàn tiền trực tuyến.</p>
                    <a href="refund" class="btn btn-outline-danger btn-sm stretched-link">Truy cập</a>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="bg-dark text-white text-center py-3 mt-5">
    <p class="mb-0">&copy; 2026 TrainBooking System. All rights reserved.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>