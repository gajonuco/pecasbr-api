package com.gabriel_nunez.oficina_mecanica.integration.dto;

import java.time.LocalDate;

public record DTORequest(String billingType, String chargeType, Callback callback, LocalDate endDate, Integer dueDateLimitDays,
                String name, String description, Double value) {

                    public record Callback(String successUrl, Boolean autoRedirect) {
                    }
}
