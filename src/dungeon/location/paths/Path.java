package dungeon.location.paths;

import java.util.Objects;

/**
 * The path class represents the connection between two locations. There exists one and only one
 * direct connection between 2 locations. All the paths are bidirectional and considered equal from
 * A to B and B to A.
 */
public class Path {

    private final LocationAddress locationOne;

    private final LocationAddress locationTwo;

    /**
     * Constructs an object of the Path class with taking the 2 locations that it connects as the
     * parameter. The paths are bidirectional and the equality of the paths is checked when both the
     * locations in the path are the same.
     *
     * @param locationOne One of the location in the path.
     * @param locationTwo The other location in the path that needs to be connected.
     */
    public Path(LocationAddress locationOne, LocationAddress locationTwo) {
        if (locationOne == null || locationTwo == null) {
            throw new IllegalArgumentException("Both the locations are required to be not null");
        } else if (locationOne.equals(locationTwo)) {
            throw new IllegalArgumentException("Path cannot exist between 2 same locations.");
        }

        this.locationOne = locationOne;
        this.locationTwo = locationTwo;
    }

    /**
     * Get the location address of one of the locations of the path.
     *
     * @return location address of locationOne.
     */
    public LocationAddress getLocationOne() {
        return locationOne;
    }

    /**
     * Get the location address of one of the locations of the path.
     *
     * @return location address of locationTwo.
     */
    public LocationAddress getLocationTwo() {
        return locationTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path path = (Path) o;

        return (locationOne.equals(path.locationOne) && locationTwo.equals(path.locationTwo))
                || (locationOne.equals(path.locationTwo) && locationTwo.equals(path.locationOne));
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationOne, locationTwo);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(locationOne).append(" <-> ").append(locationTwo).toString();
    }
}
