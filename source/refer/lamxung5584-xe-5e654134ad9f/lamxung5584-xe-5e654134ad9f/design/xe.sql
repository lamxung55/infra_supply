CREATE TABLE `VEHICLE` (
`ID` decimal NOT NULL,
`BKS` varchar(255) NULL COMMENT 'Biển kiểm soát',
`NAME` varchar(255) NULL COMMENT 'Tên lái xe',
`PHONE` varchar(255) NULL COMMENT 'SĐT',
`FACTOR` decimal NULL COMMENT 'Hệ số chi trả',
PRIMARY KEY (`ID`) 
);

CREATE TABLE `PACKAGE` (
`ID` decimal NOT NULL,
`ID_SHIPPING` decimal NULL COMMENT 'ID của chuyến hàng',
`LOCATION_ID` decimal NULL COMMENT 'ID của Địa chỉ nhận',
`LOCATION_DETAILS` varchar(255) NULL COMMENT 'Địa chỉ chi tiết',
`FEE` decimal(65,0) NULL COMMENT 'Phí vận chuyển (tự tính theo công thức hoặc nhập tay)',
`IS_FEE_MANUAL` decimal(1,0) NULL COMMENT 'Tính phí vận chuyển theo hình thức nhập tay hay tự động',
`WEIGHT` decimal NULL COMMENT 'Cân nặng',
`HEIGHT` decimal NULL COMMENT 'Chiều cao',
`WIDTH` decimal NULL COMMENT 'Độ rộng',
`LENGTH` decimal NULL COMMENT 'Độ dài',
`SENDER_NAME` varchar(255) NULL COMMENT 'Họ tên người gửi',
`RECEIVER_NAME` varchar(255) NULL COMMENT 'Họ tên người nhận',
`CHARACTER` varchar(255) NULL COMMENT 'Đặc điểm ( free text: 1 gói đen, 1 gói đỏ...)',
`QUANTITY` decimal NULL COMMENT 'Số lượng',
PRIMARY KEY (`ID`) 
);

CREATE TABLE `SHIPPING` (
`ID` decimal(10,0) NOT NULL,
`ID_VEHICLE` decimal(10,0) NULL COMMENT 'ID của phương tiện chuyển',
`CREATE_TIME` date NULL COMMENT 'Ngày tạo',
`INCOME` decimal(65,0) NULL COMMENT 'Tổng thu = tổng phí từng gói hàng',
`REMAIN_INCOME` decimal(65,0) NULL COMMENT 'Thu nhập còn lại = tổng thu - % lái xe* tổng thu',
PRIMARY KEY (`ID`) 
);

CREATE TABLE `LOCATION` (
`ID` decimal(10,0) NOT NULL,
`NAME` varchar(255) NULL,
PRIMARY KEY (`ID`) 
);


ALTER TABLE `SHIPPING` ADD CONSTRAINT `fk_SHIPPING_VEHICLE_1` FOREIGN KEY (`ID_VEHICLE`) REFERENCES `VEHICLE` (`ID`);
ALTER TABLE `PACKAGE` ADD CONSTRAINT `fk_PACKAGE_SHIPPING_1` FOREIGN KEY (`ID_SHIPPING`) REFERENCES `SHIPPING` (`ID`);
ALTER TABLE `PACKAGE` ADD CONSTRAINT `fk_PACKAGE_LOCATION_1` FOREIGN KEY (`LOCATION_ID`) REFERENCES `LOCATION` (`ID`);

