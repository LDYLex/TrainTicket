<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập - Hệ Thống Vé Tàu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 400px;
        }
    </style>
</head>
<body>

<div class="login-card">
    <h3 class="text-center mb-4 text-primary fw-bold">ĐĂNG NHẬP</h3>

    <c:if test="${param.status == 'success'}">
        <div class="alert alert-success text-center" role="alert">
            Đăng ký thành công! Vui lòng đăng nhập.
        </div>
    </c:if>

    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-danger text-center" role="alert">
            ${requestScope.error}
        </div>
    </c:if>

    <form action="login" method="post">
        <div class="mb-3">
            <label class="form-label text-secondary">Tên đăng nhập</label>
            <input type="text" name="username" class="form-control" 
                   value="${param.username}" required placeholder="Nhập username">
        </div>

        <div class="mb-3">
            <label class="form-label text-secondary">Mật khẩu</label>
            <input type="password" name="password" class="form-control" required placeholder="Nhập mật khẩu">
        </div>
		
		 <c:if test="${showCaptcha == true}">
		    <div>
		        <img src="simpleCaptcha.jpg" /> <br>
		        <input type="text" name="answer" placeholder="Nhập captcha" required>
		    </div>
		</c:if>
		<c:if test="${not empty Dn}">
			<div>${Dn}</div>
		</c:if>
        <button type="submit" class="btn btn-primary w-100 py-2 mt-2">Đăng Nhập</button>
    </form>

    <div class="text-center mt-3">
        <span class="text-muted">Chưa có tài khoản?</span>
        <a href="register.jsp" class="text-decoration-none fw-bold">Đăng ký ngay</a>
    </div>
    
    <div class="text-center mt-2">
        <a href="home" class="text-decoration-none text-secondary"><small>&larr; Quay về trang chủ</small></a>
    </div>
</div>

</body>
</html>