<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký - Hệ Thống Vé Tàu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .register-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 500px;
        }
    </style>
</head>
<body>

<div class="register-card">
    <h3 class="text-center mb-4 text-success fw-bold">TẠO TÀI KHOẢN</h3>

    <c:if test="${not empty error}">
        <div class="alert alert-danger text-center">
            ${error}
        </div>
    </c:if>

    <form action="register" method="post" onsubmit="return validatePassword()">
        <div class="mb-3">
            <label class="form-label">Họ và tên</label>
            <input type="text" name="fullname" class="form-control" 
                   value="${param.fullname}" required placeholder="Ví dụ: Nguyễn Văn A">
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" 
                   value="${param.email}" required placeholder="email@example.com">
        </div>

        <div class="mb-3">
            <label class="form-label">Tên đăng nhập</label>
            <input type="text" name="username" class="form-control" 
                   value="${param.username}" required minlength="3">
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" id="password" class="form-control" required minlength="6">
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">Nhập lại mật khẩu</label>
                <input type="password" id="confirm_password" class="form-control" required>
            </div>
        </div>
        <div id="msg" class="text-danger mb-3 small"></div>

        <button type="submit" class="btn btn-success w-100 py-2">Đăng Ký</button>
    </form>

    <div class="text-center mt-3">
        <span class="text-muted">Đã có tài khoản?</span>
        <a href="login.jsp" class="text-decoration-none fw-bold">Đăng nhập</a>
    </div>
</div>
<script>
    function validatePassword() {
        var password = document.getElementById("password").value;
        var confirm = document.getElementById("confirm_password").value;
        var msg = document.getElementById("msg");

        if (password !== confirm) {
            msg.innerText = "Mật khẩu xác nhận không khớp!";
            return false; // Chặn submit form
        }
        msg.innerText = "";
        return true;
    }
</script>

</body>
</html>