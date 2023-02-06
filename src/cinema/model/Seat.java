package cinema.model;

public class Seat {
	
	private int row;
	
	private int column;
	
	private int price;

	public Seat(){}
	
	public Seat(int row, int column) {
		this.row = row;
		this.column = column;
		this.price = this.row <= 4 ? 10 : 8;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getPrice() {
		return this.row <= 4 ? 10 : 8;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }

        if (o == null || getClass() != o.getClass()) {
			return false;
		}

        Seat seat = (Seat) o;
        if (row != seat.row) {
			return false;
		}
        return column == seat.column;
    }
}
