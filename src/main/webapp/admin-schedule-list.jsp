<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản Lý Lịch Trình</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-success"><i class="fas fa-calendar-alt"></i> Quản Lý Lịch Trình</h2>
        <div>
            <a href="manage-schedule?action=add" class="btn btn-success fw-bold">
                <i class="fas fa-plus"></i> Lên Lịch Mới
            </a>
            <a href="admin.jsp" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Về Admin
            </a>
        </div>
    </div>

    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-${sessionScope.msgType} alert-dismissible fade show">
            ${sessionScope.msg}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="msg" scope="session"/>
        <c:remove var="msgType" scope="session"/>
    </c:if>

    <div class="card shadow">
        <div class="card-body">
            <table class="table table-hover table-bordered align-middle text-center">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tàu</th>
                    <th>Hành Trình</th>
                    <th>Khởi Hành</th>
                    <th>Đến Nơi</th>
                    <th>Giá Vé</th>
                    <th>Xóa</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${schedules}" var="s">
                    <tr>
                        <td>${s.scheduleId}</td>
                        <td class="fw-bold text-primary">${s.trainCode}</td>
                        <td>
                            ${s.departureStation} <i class="fas fa-arrow-right text-muted"></i> ${s.arrivalStation}
                        </td>
                        <td><fmt:formatDate value="${s.departureTime}" pattern="HH:mm dd/MM/yyyy"/></td>
                        <td><fmt:formatDate value="${s.arrivalTime}" pattern="HH:mm dd/MM/yyyy"/></td>
                        <td class="text-end text-danger fw-bold">
                            <fmt:formatNumber value="${s.priceAdult}" type="currency" currencySymbol="₫"/>
                        </td>
                        <td>
                            <a href="manage-schedule?action=delete&id=${s.scheduleId}" 
                               onclick="return confirm('Bạn có chắc muốn hủy chuyến này?')" 
                               class="btn btn-danger btn-sm"><i class="fas fa-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>