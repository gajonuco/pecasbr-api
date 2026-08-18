package com.gajonuco.pecasbr.integration.dto;

import java.time.LocalDate;

public record DTORequest(String billingType, String chargeType, Callback callback, LocalDate endDate, Integer dueDateLimitDays,
                String name, String description, Double value) {

                    public record Callback(String successUrl, Boolean autoRedirect) {
                    }
}
