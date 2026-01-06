<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Tàu Mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5" style="max-width: 600px;">
    <div class="card shadow">
        <div class="card-header bg-success text-white">
            <h4 class="mb-0">Thêm Đoàn Tàu Mới</h4>
        </div>
        <div class="card-body p-4">
            <form action="manage-train" method="post">
                
                <div class="mb-3">
                    <label class="form-label fw-bold">Mã Tàu (Code)</label>
                    <input type="text" name="trainCode" class="form-control" placeholder="Ví dụ: SE1, TN2..." required>
                    <div class="form-text">Mã tàu nên viết ngắn gọn, in hoa.</div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">Tên Đoàn Tàu</label>
                    <input type="text" name="trainName" class="form-control" placeholder="Ví dụ: Thống Nhất 1..." required>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Số lượng Toa</label>
                        <input type="number" name="numCoaches" class="form-control" value="5" min="1" required>
                        <div class="form-text">Hệ thống sẽ tự tạo Toa 1, Toa 2...</div>
                    </div>
                    
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Số ghế mỗi Toa</label>
                        <input type="number" name="seatsPerCoach" class="form-control" value="20" min="10" required>
                        <div class="form-text">Ví dụ: 20 ghế/toa.</div>
                    </div>
                </div>

                <div class="d-flex justify-content-end gap-2 mt-4">
                    <a href="manage-train" class="btn btn-secondary">Hủy bỏ</a>
                    <button type="submit" class="btn btn-primary fw-bold">Lưu Thông Tin</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>