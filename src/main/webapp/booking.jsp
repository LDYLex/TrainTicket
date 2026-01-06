<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt Vé Tàu - TrainBooking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        /* CSS cho Navbar */
        .navbar-custom {
            background-color: #003366; /* Màu xanh đậm đường sắt */
        }
        .navbar-custom .nav-link {
            color: rgba(255,255,255,0.8);
            font-weight: 500;
        }
        .navbar-custom .nav-link:hover, .navbar-custom .nav-link.active {
            color: #ffffff;
            border-bottom: 2px solid #ffcc00;
        }
        
        /* CSS cho Sơ đồ ghế (Demo) */
        .seat-map {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            border: 1px solid #ddd;
        }
        .seat-item {
            width: 40px;
            height: 40px;
            margin: 5px;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            line-height: 40px;
            font-size: 12px;
            font-weight: bold;
        }
        .seat-available { background-color: #d1e7dd; color: #0f5132; border: 1px solid #badbcc; }
        .seat-booked { background-color: #f8d7da; color: #842029; border: 1px solid #f5c2c7; cursor: not-allowed; }
        .seat-selected { background-color: #ffc107; color: #000; border: 1px solid #e0a800; }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark navbar-custom shadow sticky-top">
    <div class="container">
        <a class="navbar-brand fw-bold" href="home"><i class="fas fa-train"></i> TrainBooking</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="schedule"><i class="far fa-calendar-alt"></i> Xem Lịch Trình</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="check-ticket"><i class="fas fa-ticket-alt"></i> Kiểm Tra Vé</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="refund"><i class="fas fa-undo"></i> Trả Vé</a>
                </li>
            </ul>
            
            <ul class="navbar-nav">
                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle active" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user-circle"></i> Xin chào, ${sessionScope.user.fullName}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="profile">Thông tin cá nhân</a></li>
                            <li><a class="dropdown-item" href="history">Lịch sử đặt vé</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-danger" href="logout">
                                <i class="fas fa-sign-out-alt"></i> Đăng xuất
                            </a></li>
                        </ul>
                    </li>
                </c:if>
                
                <c:if test="${empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="btn btn-warning btn-sm fw-bold text-dark" href="login.jsp">Đăng nhập</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<div class="container my-4">
    <div class="row">
        
        <div class="col-lg-8">
            <h4 class="mb-3 text-primary"><i class="fas fa-chair"></i> Chọn vị trí ngồi</h4>
            
            <ul class="nav nav-tabs" id="coachTab" role="tablist">
                <li class="nav-item">
                    <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#coach1">Toa 1 (Ngồi mềm)</button>
                </li>
                <li class="nav-item">
                    <button class="nav-link" data-bs-toggle="tab" data-bs-target="#coach2">Toa 2 (Giường nằm)</button>
                </li>
            </ul>

            <div class="tab-content seat-map mt-0" id="coachTabContent">
                <div class="tab-pane fade show active" id="coach1">
                    <p class="text-muted small text-center">Đầu toa</p>
                    <div class="d-flex flex-wrap justify-content-center">
                        <div class="seat-item seat-booked" title="Đã bán">1</div>
                        <div class="seat-item seat-booked" title="Đã bán">2</div>
                        <div class="seat-item seat-available" onclick="selectSeat(this, 3, 150000)">3</div>
                        <div class="seat-item seat-available" onclick="selectSeat(this, 4, 150000)">4</div>
                        <div class="w-100"></div> <div class="seat-item seat-available" onclick="selectSeat(this, 5, 150000)">5</div>
                        <div class="seat-item seat-available" onclick="selectSeat(this, 6, 150000)">6</div>
                    </div>
                </div>
                
                <div class="tab-pane fade" id="coach2">
                    <p>Sơ đồ toa 2...</p>
                </div>
            </div>
            
            <div class="mt-3 d-flex gap-3 justify-content-center small">
                <div><span class="badge bg-success border">&nbsp;</span> Còn trống</div>
                <div><span class="badge bg-danger border">&nbsp;</span> Đã đặt</div>
                <div><span class="badge bg-warning text-dark border">&nbsp;</span> Đang chọn</div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="card shadow border-0">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Thông tin vé</h5>
                </div>
                <div class="card-body">
                    <p><strong>Hành khách:</strong> ${sessionScope.user.fullName}</p>
                    <p><strong>Chuyến tàu:</strong> SE1</p> <p><strong>Hành trình:</strong> Hà Nội <i class="fas fa-arrow-right"></i> Sài Gòn</p>
                    <p><strong>Thời gian:</strong> 08:00 05/01/2026</p>
                    <hr>
                    
                    <h6>Ghế đã chọn:</h6>
                    <div id="selected-seats-list" class="mb-3 text-muted fst-italic">
                        Chưa chọn ghế nào...
                    </div>

                    <div class="d-flex justify-content-between fw-bold fs-5 text-danger">
                        <span>Tổng tiền:</span>
                        <span id="total-price">0 ₫</span>
                    </div>

                    <form action="payment" method="get" class="mt-3">
                        <input type="hidden" name="selectedSeatIds" id="inputSeatIds">
                        <input type="hidden" name="scheduleId" value="${param.scheduleId}">
                        
                        <button type="submit" class="btn btn-danger w-100 py-2 fw-bold" id="btnPayment" disabled>
                            THANH TOÁN NGAY <i class="fas fa-chevron-right"></i>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    let selectedSeats = [];
    let totalPrice = 0;

    function selectSeat(element, seatId, price) {
        if (element.classList.contains('seat-booked')) return;

        // Toggle chọn/bỏ chọn
        if (element.classList.contains('seat-selected')) {
            element.classList.remove('seat-selected');
            element.classList.add('seat-available');
            
            // Xóa khỏi mảng
            selectedSeats = selectedSeats.filter(s => s.id !== seatId);
            totalPrice -= price;
        } else {
            element.classList.remove('seat-available');
            element.classList.add('seat-selected');
            
            // Thêm vào mảng
            selectedSeats.push({id: seatId, price: price});
            totalPrice += price;
        }

        updateUI();
    }

    function updateUI() {
        const listDiv = document.getElementById('selected-seats-list');
        const priceSpan = document.getElementById('total-price');
        const btn = document.getElementById('btnPayment');
        const inputIds = document.getElementById('inputSeatIds');

        // Cập nhật danh sách hiển thị
        if (selectedSeats.length === 0) {
            listDiv.innerHTML = "Chưa chọn ghế nào...";
            btn.disabled = true;
        } else {
            listDiv.innerHTML = selectedSeats.map(s => 
                `<span class="badge bg-secondary me-1">Ghế ${s.id}</span>`
            ).join(<ul class="nav nav-tabs" id="coachTab" role="tablist">
            <c:forEach items="${coaches}" var="coach" varStatus="loop">
            <li class="nav-item">
                <button class="nav-link ${loop.first ? 'active' : ''}" 
                        data-bs-toggle="tab" 
                        data-bs-target="#coach-${coach.coachId}">
                    ${coach.coachCode}
                </button>
            </li>
        </c:forEach>
    </ul>

    <div class="tab-content seat-map mt-0" id="coachTabContent">
        
        <c:forEach items="${coaches}" var="coach" varStatus="loop">
            <div class="tab-pane fade ${loop.first ? 'show active' : ''}" id="coach-${coach.coachId}">
                <p class="text-muted small text-center mb-2">-- Đầu toa ${coach.coachCode} --</p>
                
                <div class="d-flex flex-wrap justify-content-center" style="gap: 10px;">
                    <c:forEach items="${coach.seats}" var="seat">
                        
                        <c:choose>
                            <c:when test="${seat.booked}">
                                <div class="seat-item seat-booked" title="Đã có người đặt">
                                    ${seat.seatNumber}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="seat-item seat-available" 
                                     id="seat-${seat.seatId}"
                                     onclick="selectSeat(this, ${seat.seatId}, ${seat.price})">
                                    ${seat.seatNumber}
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </div>
            </div>
        </c:forEach>
        
    </div>);
            btn.disabled = false;
        }

        // Cập nhật tiền
        priceSpan.innerText = totalPrice.toLocaleString('vi-VN') + ' ₫';
        
        // Cập nhật input ẩn để gửi đi
        inputIds.value = selectedSeats.map(s => s.id).join(',');
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>