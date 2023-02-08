package cinema.presentation;

import cinema.model.Stats;
import cinema.model.ScreenRoom;
import cinema.model.Seat;
import cinema.model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class SeatController {
	private final ScreenRoom screenRoom = new ScreenRoom(9, 9);
	private Stats stats = new Stats(screenRoom.getTotalRows(), screenRoom.getTotalColumns());
	public SeatController() {}

	// Returns information about the rows, columns, and available seats as a JSON object.
	@GetMapping("/seats")
	public ScreenRoom getSeat(){
		return screenRoom;
	}
	// Receives a seat as a JSON object and returns a JSON object with a token, row, column and price fields.
    // If the seat is taken or user pass a wrong row/column number, respond with a 400 (Bad Request) status code.
	@PostMapping("/purchase")
	public synchronized ResponseEntity<?> postSeat(@RequestBody Seat seat) {
		if ((seat.getRow() >= 1 && seat.getRow() <= 9) && (seat.getColumn() >= 1 && seat.getColumn() <= 9)) {
			if (screenRoom.getAvailableSeats().contains(seat)) {
				Ticket ticket = new Ticket(seat);

				screenRoom.removeFromAvailableSeats(seat);
				screenRoom.addIntoPurchasedSeats(ticket.getToken(), seat);

				stats.updateStats(-1, 1, seat.getPrice());

				return new ResponseEntity<>(ticket, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
		}
	}
	// Receives a ticket as a JSON object and returns a JSON object with a row, column and price fields. Also makes the seat available again.
	// If the ticket cannot be identified by the token, respond with a 400 status code.
	@PostMapping("/return")
	public synchronized ResponseEntity<?> postReturn(@RequestBody Ticket ticket){
		if(screenRoom.getPurchasedSeats().containsKey(ticket.getToken())){
			Seat returnedTicket = screenRoom.getPurchasedSeats().get(ticket.getToken());

			stats.updateStats(1, -1, -returnedTicket.getPrice());
			screenRoom.addAvailableSeat(returnedTicket);
			screenRoom.removeFromPurchasedSeats(ticket.getToken());

			return ResponseEntity.status(HttpStatus.OK)
					.body(Collections.singletonMap("returned_ticket", returnedTicket));
		}
		return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
	}
	// Returns the movie theatre if the URL parameters contain a password key with a super_secret value.
	// If the parameters don't contain a password key or a wrong value has been passed, respond with a 401 status code.
	@PostMapping("/stats")
	public ResponseEntity<?> postStats(@RequestParam(required = false) String password){
		if (password == null || !password.equals("super_secret")){
			return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}
}
