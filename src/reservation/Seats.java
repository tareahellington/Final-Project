package reservation;

public class Seats {
    private String seatNumber;
    private boolean isReserved;

    public Seats(String seatNumber, boolean isReserved) {
        this.seatNumber = seatNumber;
        this.isReserved = isReserved;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    @Override
    public String toString() {
        return "Seat " + seatNumber + (isReserved ? " (Reserved)" : " (Available)");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Seats seats = (Seats) obj;
        return seatNumber.equals(seats.seatNumber);
    }

    @Override
    public int hashCode() {
        return seatNumber.hashCode();
    }
}
