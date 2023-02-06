package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.UUID;

public class Ticket {
    @JsonUnwrapped
    private String token;
    @JsonProperty(value = "ticket")
    private Seat seat;
    public Ticket(){}
    public Ticket(Seat seat) {
        this.token = UUID.randomUUID().toString();
        this.seat = seat;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Seat getSeat() {
        return seat;
    }
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
