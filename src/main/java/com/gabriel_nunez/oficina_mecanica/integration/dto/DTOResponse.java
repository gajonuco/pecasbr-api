package com.gabriel_nunez.oficina_mecanica.integration.dto;

import java.time.LocalDate;

public record DTOResponse(String id, String name, Double value, Boolean active, String chargeType,
		String invoiceUrl, String billingType, String subscriptionCycle, String description,
		LocalDate endDate, Boolean deleted, Integer viewCount, Integer maxInstallmentCount,
		Integer dueDateLimitDays, Boolean notificationEnabled) {

}