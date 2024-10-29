package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class OrderTableInfoDTO {
    private Integer orderId;
    private Integer tableId;
    private Integer peopleCount;
    private String waiter;
    private BigDecimal totalAmount;
    private Integer status;

    private String orderTime;

    private String  paymentTime;

    private String  completionTime;
    private String notes;

    // TableInfo 字段
    private Integer id;
    private String tableNumber;
    private String tableImage;
    private String tableAddress;
    private String tableType;
}
