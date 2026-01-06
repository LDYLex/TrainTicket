<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch Trình Chạy Tàu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .schedule-header {
            background-color: #2c3e50;
            color: white;
            padding: 40px 0;
            margin-bottom: 30px;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>


<div class="schedule-header text-center">
    <h1>Lịch Trình Tàu Chạy</h1>
    <p>Cập nhật các chuyến tàu sắp khởi hành</p>
    <a href="home" class="btn btn-outline-light btn-sm mt-2">← Quay về Trang chủ</a>
</div>

<div class="container">
	<div class="card mb-4 shadow-sm">
        <div class="card-body bg-light">
            <form action="schedule" method="get" class="row g-3 align-items-end">
                
                <div class="col-md-4">
                    <label class="form-label fw-bold">Ga Đi</label>
                    <select name="fromStation" class="form-select">
                        <option value="">-- Tất cả --</option>
                        <c:forEach items="${stations}" var="s">
                            <option value="${s.stationId}" ${param.fromStation == s.stationId.toString() ? 'selected' : ''}>
                                ${s.stationName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-4">
                    <label class="form-label fw-bold">Ga Đến</label>
                    <select name="toStation" class="form-select">
                        <option value="">-- Tất cả --</option>
                        <c:forEach items="${stations}" var="s">
                            <option value="${s.stationId}" ${param.toStation == s.stationId.toString() ? 'selected' : ''}>
                                ${s.stationName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100 fw-bold">
                         Lọc Vé
                    </button>
                </div>
                
                <div class="col-md-2">
                    <a href="schedule" class="btn btn-outline-secondary w-100">Xóa lọc</a>
                </div>
            </form>
        </div>
    </div>
    <div class="card shadow">
        <div class="card-body">
            <c:if test="${empty schedules}">
                <div class="alert alert-warning text-center">
                    Hiện chưa có lịch trình nào sắp tới.
                </div>
            </c:if>

            <c:if test="${not empty schedules}">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-primary text-center">
                            <tr>
                                <th>Mã Tàu</th>
                                <th>Ga Đi</th>
                                <th>Ga Đến</th>
                                <th>Ngày Giờ Đi</th>
                                <th>Ngày Giờ Đến</th>
                                <th>Giá Vé (Từ)</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${schedules}" var="s">
                                <tr>
                                    <td class="text-center fw-bold text-primary">${s.trainCode}</td>
                                    <td>${s.departureStation}</td>
                                    <td>${s.arrivalStation}</td>
                                    
                                    <td class="text-center">
                                        <fmt:formatDate value="${s.departureTime}" pattern="HH:mm dd/MM/yyyy"/>
                                    </td>
                                    
                                    <td class="text-center">
                                        <fmt:formatDate value="${s.arrivalTime}" pattern="HH:mm dd/MM/yyyy"/>
                                    </td>
                                    
                                    <td class="text-end fw-bold text-success">
                                        <fmt:formatNumber value="${s.priceAdult}" type="currency" currencySymbol="₫"/>
                                    </td>
                                    
                                    <td class="text-center">
                                        <a href="booking?scheduleId=${s.scheduleId}" class="btn btn-warning btn-sm fw-bold">
                                            Đặt Vé
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>