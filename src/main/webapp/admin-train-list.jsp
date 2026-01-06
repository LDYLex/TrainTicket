<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Tàu - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary"><i class="fas fa-subway"></i> Danh Sách Tàu</h2>
        <div>
            <a href="manage-train?action=add" class="btn btn-success fw-bold">
                <i class="fas fa-plus"></i> Thêm Tàu Mới
            </a>
            <a href="admin" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Quay lại Admin
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
            <table class="table table-hover table-bordered text-center align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Mã Tàu</th>
                    <th>Tên Đoàn Tàu</th>
                    <th>Hành Động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${trains}" var="t">
                    <tr>
                        <td>${t.trainId}</td>
                        <td class="fw-bold text-primary">${t.trainCode}</td>
                        <td>${t.trainName}</td>
                        <td>
                            <a href="#" onclick="confirmDelete(${t.trainId}, '${t.trainCode}')" 
                               class="btn btn-danger btn-sm">
                                <i class="fas fa-trash-alt"></i> Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                
                <c:if test="${empty trains}">
                    <tr>
                        <td colspan="4" class="text-muted fst-italic py-4">
                            Chưa có dữ liệu tàu nào. Hãy thêm mới!
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function confirmDelete(id, name) {
        if (confirm("Bạn có chắc chắn muốn xóa tàu [" + name + "] không?\nLưu ý: Không thể xóa nếu tàu đang có lịch trình hoạt động.")) {
            window.location.href = "manage-train?action=delete&id=" + id;
        }
    }
</script>
</body>
</html>