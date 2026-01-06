<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thêm Lịch Trình Mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5" style="max-width: 700px;">
    <div class="card shadow">
        <div class="card-header bg-success text-white">
            <h4 class="mb-0">Thiết Lập Chuyến Tàu Mới</h4>
        </div>
        <div class="card-body p-4">
            <form action="manage-schedule" method="post">
                
                <div class="mb-3">
                    <label class="form-label fw-bold">Chọn Tàu</label>
                    <select name="trainId" class="form-select" required>
                        <option value="">-- Chọn đoàn tàu --</option>
                        <c:forEach items="${trains}" var="t">
                            <option value="${t.trainId}">${t.trainCode} - ${t.trainName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Ga Đi</label>
                        <select name="depStation" class="form-select" required>
                            <c:forEach items="${stations}" var="s">
                                <option value="${s.stationId}">${s.stationName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Ga Đến</label>
                        <select name="arrStation" class="form-select" required>
                            <c:forEach items="${stations}" var="s">
                                <option value="${s.stationId}" ${s.stationName == 'Sài Gòn' ? 'selected' : ''}>
                                    ${s.stationName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Ngày Giờ Đi</label>
                        <input type="datetime-local" name="depTime" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Ngày Giờ Đến</label>
                        <input type="datetime-local" name="arrTime" class="form-control" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">Giá Vé Cơ Bản (VNĐ)</label>
                    <input type="number" name="price" class="form-control" placeholder="Ví dụ: 500000" min="0" step="1000" required>
                </div>

                <div class="d-flex justify-content-end gap-2 mt-4">
                    <a href="manage-schedule" class="btn btn-secondary">Hủy bỏ</a>
                    <button type="submit" class="btn btn-success fw-bold">Xác Nhận Lên Lịch</button>
                </div>
            </form>
        </div>
    </div>
</div>
<c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-${sessionScope.msgType} alert-dismissible fade show">
            <i class="fas fa-exclamation-circle"></i> ${sessionScope.msg}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="msg" scope="session"/>
    </c:if>
</body>
</html>