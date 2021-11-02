package org.programmers.interparkyu;

import org.programmers.interparkyu.error.exception.StatusConflictException;

public enum ReservationStatus {
    NOT_RESERVED{
        @Override
        public ReservationStatus reserve() {
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
        public ReservationStatus cancel() {
            return ReservationStatus.CANCELED;
        }

        @Override
        public ReservationStatus makeAvailable() {
            throw new StatusConflictException("seat waiting for payment cannot be made available");
        }
    };

    abstract public ReservationStatus reserve();
    abstract public ReservationStatus cancel();
    abstract public ReservationStatus makeAvailable();
}
