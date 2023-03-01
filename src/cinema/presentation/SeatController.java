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

	/**
     * Get seats
     *
     * @return Retrieve every available seat in the screenRoom
     */
	@GetMapping("/seats")
	public ScreenRoom getSeat(){
		return screenRoom;
	}
	/**
     * Purchase a seat
     *
     * @param seat The seat to be purchased
     * @return A response entity with the status of the purchase
     */
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
	/**
     * Return purchased ticket
     *
     * @param ticket The ticket to be returned
     * @return A response entity with the status of the ticket purchase cancellation
     */
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
	/**
     * Get stats
     *
     * @param password Password key must include a super_secret value to return screen room stats
     * @return A response entity with the status
     */
	@PostMapping("/stats")
	public ResponseEntity<?> postStats(@RequestParam(required = false) String password){
		if (password == null || !password.equals("super_secret")){
			return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}
}
