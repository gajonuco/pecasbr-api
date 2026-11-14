package com.gajonuco.pecasbr.integration.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DTOResponse(String id, String name, BigDecimal value, Boolean active, String chargeType,
						  String invoiceUrl, String billingType, String subscriptionCycle, String description,
						  LocalDate endDate, Boolean deleted, Integer viewCount, Integer maxInstallmentCount,
						  Integer dueDateLimitDays, Boolean notificationEnabled) {

}