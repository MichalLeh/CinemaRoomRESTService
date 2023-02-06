package cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScreenRoom {
	@JsonProperty("total_rows")
    private int totalRows;
    @JsonProperty("total_columns")
	private int totalColumns;
	@JsonProperty("available_seats")
	private List<Seat> availableSeats = new ArrayList<>();
	@JsonIgnore
    private HashMap<String, Seat> purchasedSeats = new HashMap<>();
    
	public ScreenRoom(int totalRows, int totalColumns) {
		this.totalRows = totalRows;
		this.totalColumns = totalColumns;

		for (int i = 1; i <= totalRows; i++) {
			for (int j = 1; j <= totalColumns; j++) {
				availableSeats.add(new Seat(i, j));
			}
		}
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
	public List<Seat> getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(List<Seat> availableSeats) {
		this.availableSeats = availableSeats;
	}
	public void addAvailableSeat(Seat seat) {
		this.availableSeats.add(seat);
	}
	public void removeFromAvailableSeats(Seat seat) {
		this.availableSeats.remove(seat);
	}
	public void getSeat(Seat seat) {
		this.availableSeats.get(this.availableSeats.indexOf(seat));
	}

	public HashMap<String, Seat> getPurchasedSeats() {
        return purchasedSeats;
    }
    public void setPurchasedSeats(HashMap<String, Seat> purchasedSeats) {
        this.purchasedSeats = purchasedSeats;
    }
    public void addIntoPurchasedSeats(String token, Seat seat) {
        this.purchasedSeats.putIfAbsent(token, seat);
    }
}
