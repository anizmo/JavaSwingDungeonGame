package dungeon.location.paths;

import java.util.Objects;

/**
 * The row number and the column number of a location collectively represents the position of the
 * Location in the maze. This class represents the position of the location in the maze.
 */
public class LocationAddress {

    private final int rowNumber;

    private final int columnNumber;

    /**
     * Constructs a Location Address with the row number and the column number provided to it. Each
     * Location Address uniquely represents the location of a Cave or Tunnel in the Maze by its row
     * and column number.
     *
     * @param rowNumber    The row number of the location.
     * @param columnNumber The column number of the location.
     */
    public LocationAddress(int rowNumber, int columnNumber) {
        if (rowNumber < 0 || columnNumber < 0) {
            throw new IllegalArgumentException("Row number or column number cannot be negative.");
        }

        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * Creates a copy of the supplied location address.
     *
     * @param other location address to be copied.
     */
    public LocationAddress(LocationAddress other) {
        this.rowNumber = other.rowNumber;
        this.columnNumber = other.columnNumber;
    }

    /**
     * Get the row number of the location.
     *
     * @return rowNumber.
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * Get the column number of the location.
     *
     * @return rowNumber.
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationAddress that = (LocationAddress) o;

        return rowNumber == that.rowNumber && columnNumber == that.columnNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, columnNumber);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("(").append(rowNumber).append(", ")
                .append(columnNumber).append(')').toString();
    }
}
