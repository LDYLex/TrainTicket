<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Quản Trị Hệ Thống</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .admin-sidebar {
            min-height: 100vh;
            background-color: #343a40;
            color: white;
        }
        .admin-sidebar a {
            color: #adb5bd;
            text-decoration: none;
            padding: 10px 20px;
            display: block;
        }
        .admin-sidebar a:hover, .admin-sidebar a.active {
            background-color: #495057;
            color: white;
        }
        .stat-card {
            border-left: 5px solid;
            transition: transform 0.2s;
        }
        .stat-card:hover { transform: scale(1.02); }
    </style>
</head>
<body>

<div class="d-flex">
    <div class="admin-sidebar d-flex flex-column p-3" style="width: 250px;">
        <h3 class="text-warning text-center fw-bold mb-4">ADMIN CP</h3>
        
        <hr>
        <span class="text-uppercase small text-muted ms-3">Quản lý</span>
        <a href="manage-train"><i class="fas fa-train me-2"></i> Quản lý Tàu</a>
        <a href="manage-schedule"><i class="fas fa-calendar-alt me-2"></i> Quản lý Lịch trình</a>
        
        <hr>

        <a href="logout" class="text-danger"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
    </div>

    <div class="flex-grow-1 p-4 bg-light">
        <h2 class="mb-4 border-bottom pb-2">Quản lý tàu và lịch trình</h2>

        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card stat-card shadow-sm border-primary h-100">
                    <div class="card-body">
                        <h6 class="text-muted">Tổng số tàu</h6>
                        <h3>12</h3>
                    </div>
                </div>
            </div>         
        </div>

        <div class="row g-4">
            
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                        <h5 class="mb-0"><i class="fas fa-subway"></i> Quản lý Tàu</h5>
                    </div>
                    <div class="card-body">
                        <p>Danh sách các đoàn tàu, thêm mới hoặc xóa bỏ tàu ngừng hoạt động.</p>
                        <div class="d-grid gap-2">
                            <a href="manage-train?action=add" class="btn btn-outline-primary">
                                <i class="fas fa-plus-circle"></i> Thêm Tàu Mới
                            </a>
                            <a href="manage-train?action=list" class="btn btn-outline-secondary">
                                <i class="fas fa-list"></i> Xem Danh Sách & Xóa Tàu
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-success text-white d-flex justify-content-between align-items-center">
                        <h5 class="mb-0"><i class="fas fa-clock"></i> Quản lý Lịch Trình</h5>
                    </div>
                    <div class="card-body">
                        <p>Thiết lập giờ chạy, giá vé hoặc hủy chuyến tàu do sự cố.</p>
                        <div class="d-grid gap-2">
                            <a href="manage-schedule?action=add" class="btn btn-outline-success">
                                <i class="fas fa-plus-circle"></i> Thêm Chuyến Mới
                            </a>
                            <a href="manage-schedule?action=cancel" class="btn btn-outline-danger">
                                <i class="fas fa-ban"></i> Hủy Chuyến Tàu
                            </a>
                            <a href="schedule" target="_blank" class="btn btn-outline-secondary">
                                <i class="fas fa-eye"></i> Xem Lịch Trình (Client View)
                            </a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>