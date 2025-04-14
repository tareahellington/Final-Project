package reservation;

public class Movies {
    private String title;
    private String[] showtimes;
    private int theaterNumber;

    public Movies(String title, String[] showtimes, int theaterNumber) {
        this.title = title;
        this.showtimes = showtimes;
        this.theaterNumber = theaterNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(String[] showtimes) {
        this.showtimes = showtimes;
    }

    public int getTheaterNumber() {
        return theaterNumber;
    }

    public void setTheaterNumber(int theaterNumber) {
        this.theaterNumber = theaterNumber;
    }

    @Override
    public String toString() {
        return title + " - Theater " + theaterNumber;
    }

}


