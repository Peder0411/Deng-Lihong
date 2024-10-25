package com.practice.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderTableInfoDTO {
    private Integer orderId;
    private Integer tableId;
    private Integer peopleCount;
    private String waiter;
    private BigDecimal totalAmount;
    private Integer status;
    private LocalDateTime orderTime;
    private LocalDateTime paymentTime;
    private LocalDateTime completionTime;
    private String notes;

    // TableInfo 字段
    private String tableNumber;
    private String tableImage;
    private String tableAddress;
    private String tableType;
}
