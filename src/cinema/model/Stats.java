package cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {
    @JsonIgnore
    private int totalRows;
    @JsonIgnore
    private int totalColumns;
    @JsonProperty("current_income")
    private int currentIncome;
    @JsonProperty("number_of_available_seats")
    private int numberOfAvailableSeats;
    @JsonProperty("number_of_purchased_tickets")
    private int numberOfPurchasedTickets;
    public Stats(){}
    public Stats(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.numberOfAvailableSeats = totalRows * totalColumns;
        this.numberOfPurchasedTickets = 0;
        this.currentIncome = 0;
    }
    public void updateStats(int availableSeat, int purchasedTicket, int ticketPrice){
        setNumberOfAvailableSeats(availableSeat);
        setNumberOfPurchasedTickets(purchasedTicket);
        setCurrentIncome(ticketPrice);
    }
    public int getTotalRows() {
        return totalRows;
    }
    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
    public int getTotalColumns() {
        return totalColumns;
    }
    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }
    public int getCurrentIncome() {
        return currentIncome;
    }
    public void setCurrentIncome(int currentIncome) {
        this.currentIncome += currentIncome;
    }
    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }
    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats += numberOfAvailableSeats;
    }
    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }
    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets += numberOfPurchasedTickets;
    }
}
