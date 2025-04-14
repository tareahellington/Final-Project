package reservation;

public class Tickets {
    private Double ticketPrice;

    private Tickets(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public static void main(String[] args) {
        Tickets adultTicket = new Tickets(10.00);
        Tickets childTicket = new Tickets(5.00);
        Tickets seniorTicket = new Tickets(7.50);
        System.out.println("Adult Ticket Price: " + adultTicket.getTicketPrice());
        System.out.println("Child Ticket Price: " + childTicket.getTicketPrice());
        System.out.println("Senior Ticket Price: " + seniorTicket.getTicketPrice());
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket Price: $" + ticketPrice;
    }
}
