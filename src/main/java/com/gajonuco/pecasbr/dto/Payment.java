/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.Payment
 */
package com.gajonuco.pecasbr.dto;

public class Payment {
    private String object;
    private String id;
    private String dateCreated;
    private String customer;
    private String subscription;
    private String installment;
    private String paymentLink;
    private String dueDate;
    private String originalDueDate;
    private float value;
    private float netValue;
    private String originalValue = null;
    private String interestValue = null;
    private String nossoNumero = null;
    private String description;
    private String externalReference;
    private String billingType;
    private String status;
    private String pixTransaction = null;
    private String confirmedDate;
    private String paymentDate;
    private String clientPaymentDate;
    private String installmentNumber = null;
    private String creditDate;
    private String custody = null;
    private String estimatedCreditDate;
    private String invoiceUrl;
    private String bankSlipUrl = null;
    private String transactionReceiptUrl;
    private String invoiceNumber;
    private boolean deleted;
    private boolean anticipated;
    private boolean anticipable;
    private String lastInvoiceViewedDate;
    private String lastBankSlipViewedDate = null;
    private boolean postalService;

    public String getObject() {
        return this.object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSubscription() {
        return this.subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getInstallment() {
        return this.installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getPaymentLink() {
        return this.paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getOriginalDueDate() {
        return this.originalDueDate;
    }

    public void setOriginalDueDate(String originalDueDate) {
        this.originalDueDate = originalDueDate;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getNetValue() {
        return this.netValue;
    }

    public void setNetValue(float netValue) {
        this.netValue = netValue;
    }

    public String getOriginalValue() {
        return this.originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getInterestValue() {
        return this.interestValue;
    }

    public void setInterestValue(String interestValue) {
        this.interestValue = interestValue;
    }

    public String getNossoNumero() {
        return this.nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalReference() {
        return this.externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getBillingType() {
        return this.billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPixTransaction() {
        return this.pixTransaction;
    }

    public void setPixTransaction(String pixTransaction) {
        this.pixTransaction = pixTransaction;
    }

    public String getConfirmedDate() {
        return this.confirmedDate;
    }

    public void setConfirmedDate(String confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public String getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getClientPaymentDate() {
        return this.clientPaymentDate;
    }

    public void setClientPaymentDate(String clientPaymentDate) {
        this.clientPaymentDate = clientPaymentDate;
    }

    public String getInstallmentNumber() {
        return this.installmentNumber;
    }

    public void setInstallmentNumber(String installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public String getCreditDate() {
        return this.creditDate;
    }

    public void setCreditDate(String creditDate) {
        this.creditDate = creditDate;
    }

    public String getCustody() {
        return this.custody;
    }

    public void setCustody(String custody) {
        this.custody = custody;
    }

    public String getEstimatedCreditDate() {
        return this.estimatedCreditDate;
    }

    public void setEstimatedCreditDate(String estimatedCreditDate) {
        this.estimatedCreditDate = estimatedCreditDate;
    }

    public String getInvoiceUrl() {
        return this.invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    public String getBankSlipUrl() {
        return this.bankSlipUrl;
    }

    public void setBankSlipUrl(String bankSlipUrl) {
        this.bankSlipUrl = bankSlipUrl;
    }

    public String getTransactionReceiptUrl() {
        return this.transactionReceiptUrl;
    }

    public void setTransactionReceiptUrl(String transactionReceiptUrl) {
        this.transactionReceiptUrl = transactionReceiptUrl;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isAnticipated() {
        return this.anticipated;
    }

    public void setAnticipated(boolean anticipated) {
        this.anticipated = anticipated;
    }

    public boolean isAnticipable() {
        return this.anticipable;
    }

    public void setAnticipable(boolean anticipable) {
        this.anticipable = anticipable;
    }

    public String getLastInvoiceViewedDate() {
        return this.lastInvoiceViewedDate;
    }

    public void setLastInvoiceViewedDate(String lastInvoiceViewedDate) {
        this.lastInvoiceViewedDate = lastInvoiceViewedDate;
    }

    public String getLastBankSlipViewedDate() {
        return this.lastBankSlipViewedDate;
    }

    public void setLastBankSlipViewedDate(String lastBankSlipViewedDate) {
        this.lastBankSlipViewedDate = lastBankSlipViewedDate;
    }

    public boolean isPostalService() {
        return this.postalService;
    }

    public void setPostalService(boolean postalService) {
        this.postalService = postalService;
    }
}

