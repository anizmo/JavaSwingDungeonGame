package dungeon.location;

/**
 * This enum specifies the type of the location whether it is a cave or a tunnel. A location with 2
 * and exactly 2 openings is categorised as a tunnel whereas a location with 1, 3, or 4 openings is
 * categorised as a cave.
 */
public enum LocationType {
    CAVE, TUNNEL
}
