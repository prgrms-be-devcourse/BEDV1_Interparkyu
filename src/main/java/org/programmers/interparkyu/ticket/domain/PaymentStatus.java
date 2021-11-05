package org.programmers.interparkyu.ticket.domain;

import org.programmers.interparkyu.common.error.exception.StatusConflictException;

public enum PaymentStatus {

    WAITING {

        @Override
        public PaymentStatus cancel() {
            return PaymentStatus.CANCELED;
        }

        @Override
        public PaymentStatus complete() {
            return PaymentStatus.COMPLETED;
        }

    },

    COMPLETED {

        @Override
        public PaymentStatus cancel() {
            return PaymentStatus.CANCELED;
        }

        @Override
        public PaymentStatus complete() {
            throw new StatusConflictException("payment status is already competed");
        }

    },

    CANCELED {
        
        @Override
        public PaymentStatus cancel() {
            throw new StatusConflictException("payment status is already canceled");
        }

        @Override
        public PaymentStatus complete() {
            throw new StatusConflictException("canceled payment cannot be completed");
        }

    };

    abstract public PaymentStatus cancel();

    abstract public PaymentStatus complete();

}
