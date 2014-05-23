/*
 * Copyright 2014 Groupon, Inc
 * Copyright 2014 The Billing Project, LLC
 *
 * Groupon licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.payment.core.sm;

import java.math.BigDecimal;
import java.util.UUID;

import javax.annotation.Nullable;

import org.killbill.billing.account.api.Account;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.payment.api.PaymentApiException;
import org.killbill.billing.payment.api.TransactionType;
import org.killbill.billing.payment.dao.DirectPaymentTransactionModelDao;
import org.killbill.billing.payment.plugin.api.PaymentInfoPlugin;

public class DirectPaymentStateContext {

    // HACK
    protected UUID paymentMethodId;

    // Can be updated (e.g. for auth or purchase)
    protected UUID directPaymentId;
    // Stateful objects created by the callbacks and passed to the other following callbacks in the automaton
    protected DirectPaymentTransactionModelDao directPaymentTransactionModelDao;
    protected PaymentInfoPlugin paymentInfoPlugin;

    protected final String directPaymentExternalKey;
    protected final String directPaymentTransactionExternalKey;
    protected final Account account;
    protected final BigDecimal amount;
    protected final Currency currency;
    protected final TransactionType transactionType;

    // Use to create new transactions only
    public DirectPaymentStateContext(@Nullable final UUID directPaymentId, @Nullable final String directPaymentTransactionExternalKey, final TransactionType transactionType,
                                     final Account account, @Nullable final UUID paymentMethodId, final BigDecimal amount, final Currency currency) throws PaymentApiException {
        this(directPaymentId, null, directPaymentTransactionExternalKey, transactionType, account, paymentMethodId, amount, currency);
    }

    // Used to create new payment and transactions
    public DirectPaymentStateContext(@Nullable final UUID directPaymentId, @Nullable final String directPaymentExternalKey,
                                     @Nullable final String directPaymentTransactionExternalKey, final TransactionType transactionType,
                                     final Account account, @Nullable final UUID paymentMethodId, final BigDecimal amount, final Currency currency) throws PaymentApiException {
        this.directPaymentId = directPaymentId;
        this.directPaymentExternalKey = directPaymentExternalKey;
        this.directPaymentTransactionExternalKey = directPaymentTransactionExternalKey;
        this.transactionType = transactionType;
        this.account = account;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
        this.currency = currency;
    }

    public void setPaymentMethodId(final UUID paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public DirectPaymentTransactionModelDao getDirectPaymentTransactionModelDao() {
        return directPaymentTransactionModelDao;
    }

    public void setDirectPaymentTransactionModelDao(final DirectPaymentTransactionModelDao directPaymentTransactionModelDao) {
        this.directPaymentTransactionModelDao = directPaymentTransactionModelDao;
    }

    public PaymentInfoPlugin getPaymentInfoPlugin() {
        return paymentInfoPlugin;
    }

    public void setPaymentInfoPlugin(final PaymentInfoPlugin paymentInfoPlugin) {
        this.paymentInfoPlugin = paymentInfoPlugin;
    }

    public UUID getDirectPaymentId() {
        return directPaymentId != null ? directPaymentId : (directPaymentTransactionModelDao != null ? directPaymentTransactionModelDao.getDirectPaymentId() : null);
    }

    public String getDirectPaymentExternalKey() {
        return directPaymentExternalKey;
    }

    public String getDirectPaymentTransactionExternalKey() {
        return directPaymentTransactionExternalKey;
    }

    public Account getAccount() {
        return account;
    }

    public UUID getPaymentMethodId() {
        return paymentMethodId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}