package org.programmers.interparkyu.ticket.domain;

import org.programmers.interparkyu.common.error.exception.StatusConflictException;

public enum ReservationStatus {

    NOT_RESERVED{
        @Override
        public ReservationStatus reserve() {
            throw new StatusConflictException("must wait for payment before reserve");
        }

        @Override
        public ReservationStatus waitForPayment() {
            return ReservationStatus.WAITING_FOR_PAYMENT;
        }

        @Override
        public ReservationStatus cancel() {
            return ReservationStatus.CANCELED;
        }

        @Override
        public ReservationStatus makeAvailable() {
            throw new StatusConflictException("seat already not reserved");
        }

    },

    WAITING_FOR_PAYMENT {
        @Override
        public ReservationStatus reserve() {
            return ReservationStatus.RESERVED;
        }

        @Override
        public ReservationStatus waitForPayment() {
            throw new StatusConflictException("already waiting for payment");
        }

        @Override
        public ReservationStatus cancel() {
            return ReservationStatus.CANCELED;
        }

        @Override
        public ReservationStatus makeAvailable() {
            throw new StatusConflictException("seat waiting for payment cannot be made available");
        }

    },

    CANCELED {
        @Override
        public ReservationStatus reserve() {
            throw new StatusConflictException("canceled seat cannot be reserved");
        }

        @Override
        public ReservationStatus waitForPayment() {
            throw new StatusConflictException("canceled seat cannot wait for payment");
        }

        @Override
        public ReservationStatus cancel() {
            throw new StatusConflictException("seat already canceled");
        }

        @Override
        public ReservationStatus makeAvailable() {
            return ReservationStatus.NOT_RESERVED;
        }

    },

    RESERVED {
        @Override
        public ReservationStatus reserve() {
            throw new StatusConflictException("seat already reserved");
        }

        @Override
        public ReservationStatus waitForPayment() {
            throw new StatusConflictException("seat already reserved");
        }

        @Override
        public ReservationStatus cancel() {
            return ReservationStatus.CANCELED;
        }

        @Override
        public ReservationStatus makeAvailable() {
            throw new StatusConflictException("seat waiting for payment cannot be made available");
        }

    };

    public abstract ReservationStatus reserve();

    public abstract ReservationStatus waitForPayment();

    public abstract ReservationStatus cancel();

    public abstract ReservationStatus makeAvailable();

}
