package dungeon.location;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import dungeon.location.paths.LocationAddress;
import dungeon.obstacles.Monster;
import dungeon.obstacles.Obstacle;
import dungeon.obstacles.ObstacleType;
import dungeon.obstacles.Otyugh;
import dungeon.obstacles.Pit;
import dungeon.obstacles.Thief;

/**
 * The MazeLocation class represents a single cell in the dungeon maze and it can be categorised as
 * either a cave or a tunnel depending on the number of openings. The MazeLocation can hold one or
 * one treasures in it if it is a Cave, a tunnel cannot hold a treasure.
 */
public class MazeLocation implements Location {

    private final Set<Collectible> collectibleList;

    private final Set<MovementDirection> openings;

    private final int rowNumber;

    private final int columnNumber;

    private final Set<Obstacle> obstacles;

    private boolean hasArrow;

    /**
     * Constructs a MazeLocation that represents either a cave or a tunnel in the maze depending on
     * the number of openings it has. The location is created with no openings and no treasures in
     * it. Only a cave can contain treasure in it but a tunnel cannot have a treasure.
     *
     * @param rowNumber    Represents the row number of the location in the maze grid.
     * @param columnNumber Represents the column number of the location in the maze grid.
     */
    public MazeLocation(int rowNumber, int columnNumber) {
        if (rowNumber < 0 || columnNumber < 0) {
            throw new IllegalArgumentException("Row or Column Number cannot be negative.");
        }
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.collectibleList = new LinkedHashSet<>();
        this.openings = new LinkedHashSet<>();
        this.obstacles = new LinkedHashSet<>();
    }

    /**
     * This is a copy constructor of the MazeLocation that creates a copy of the existing location
     * object, it is useful when returning the maze map to the driver class and it is required that
     * the location object should not be modified.
     *
     * @param other MazeLocation object to be copied.
     */
    public MazeLocation(Location other) {
        this.rowNumber = other.getAddress().getRowNumber();
        this.columnNumber = other.getAddress().getColumnNumber();
        this.collectibleList = other.getTreasures();
        this.openings = other.getOpenings();
        this.hasArrow = other.isHasArrow();
        this.obstacles = other.getAllObstacles();
    }

    @Override
    public LocationType getType() {
        if (openings.size() == 0) {
            throw new IllegalStateException("There should at least be one opening to every location");
        } else if (openings.size() == 2) {
            return LocationType.TUNNEL;
        } else {
            return LocationType.CAVE;
        }
    }

    @Override
    public Set<Obstacle> getAllObstacles() {
        return new LinkedHashSet<>(obstacles);
    }

    @Override
    public void addTreasure(Set<Collectible> collectibles) {
        if (collectibles.contains(Collectible.ARROW)) {
            throw new IllegalArgumentException("Arrows cannot be added as treasure");
        }
        if (getType() == LocationType.TUNNEL) {
            throw new IllegalStateException("Only Caves can hold treasures,"
                    + " tunnels cannot have treasures.");
        } else if (!collectibles.isEmpty()) {
            collectibleList.addAll(collectibles);
        } else {
            throw new IllegalStateException("Treasure List cannot be null or empty.");
        }

    }

    @Override
    public void addOpenings(MovementDirection movementDirection) {
        openings.add(movementDirection);
    }

    @Override
    public Set<MovementDirection> getOpenings() {
        return new TreeSet<>(openings);
    }

    @Override
    public Set<Collectible> getTreasures() {
        return new LinkedHashSet<>(collectibleList);
    }

    @Override
    public void markTreasureCollected(Collectible collectible) {
        this.collectibleList.remove(collectible);
    }

    @Override
    public void markArrowCollected() {
        if (!hasArrow) {
            throw new IllegalStateException("This location does not have an arrow");
        }
        this.hasArrow = false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Possible moves are ")
                .append(this.getOpenings()).append("\n");

        if (this.getType() == LocationType.TUNNEL
                && this.getTreasures().isEmpty()) {
            builder.append("The location is a Tunnel and there is no treasure in it.");
        } else if (this.getType() == LocationType.CAVE
                && this.getTreasures().isEmpty()) {
            builder.append("The location is a Cave with no treasure in it.");
        } else {
            builder.append("The location is a Cave with the following treasure in it: \n")
                    .append(this.getTreasures());
        }

        return builder.toString();
    }

    @Override
    public String getDirectionalSymbol() {
        StringBuilder builder = new StringBuilder();
        if (openings.contains(MovementDirection.WEST)) {
            builder.append("=");
        } else {
            builder.append(" ");
        }

        if (openings.contains(MovementDirection.NORTH)
                && !openings.contains(MovementDirection.SOUTH)) {
            builder.append("╩");
        } else if (openings.contains(MovementDirection.SOUTH)
                && !openings.contains(MovementDirection.NORTH)) {
            builder.append("╦");
        } else if (openings.contains(MovementDirection.NORTH)
                && openings.contains(MovementDirection.SOUTH)) {
            builder.append("╬");
        } else {
            builder.append("=");
        }

        if (openings.contains(MovementDirection.EAST)) {
            builder.append("=");
        } else {
            builder.append(" ");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MazeLocation that = (MazeLocation) o;
        return rowNumber == that.rowNumber && columnNumber == that.columnNumber
                && Objects.equals(collectibleList, that.collectibleList)
                && Objects.equals(openings, that.openings);
    }

    @Override
    public void addArrow() {
        hasArrow = true;
    }

    @Override
    public void addMonster(Monster monster) {
        if (getType() == LocationType.TUNNEL) {
            throw new IllegalStateException("Monsters can only be in Caves and not in Tunnels");
        }

        if (obstacles.stream().noneMatch(obstacle -> obstacle.getObstacleType()
                .equals(ObstacleType.OTYUGH))) {
            this.obstacles.add(monster);
        } else {
            throw new IllegalStateException("This cave already has an Otyugh,"
                    + " only one Otyugh can reside in a cave");
        }

    }

    @Override
    public void addPit(Pit pit) {
        if (getType() == LocationType.TUNNEL) {
            throw new IllegalStateException("Monsters can only be in Caves and not in Tunnels");
        }
        if (obstacles.stream().noneMatch(obstacle -> obstacle.getObstacleType()
                .equals(ObstacleType.PIT))) {
            this.obstacles.add(pit);
        } else {
            throw new IllegalStateException("This cave already has a pit,"
                    + " there is not enough space for more pits.");
        }
    }

    @Override
    public void addThief(Thief thief) {
        if (obstacles.stream().noneMatch(obstacle -> obstacle.getObstacleType()
                .equals(ObstacleType.THIEF))) {
            this.obstacles.add(thief);
        } else {
            throw new IllegalStateException("This location already has a thief,"
                    + " there is not enough space for more thief.");
        }
    }

    @Override
    public Monster getOtyugh() {
        List<Obstacle> monsters = obstacles.stream().filter(obstacle -> obstacle.getObstacleType()
                .equals(ObstacleType.OTYUGH)).collect(Collectors.toList());
        if (monsters.isEmpty()) {
            return null;
        } else {
            return (Otyugh) monsters.get(0);
        }
    }


    @Override
    public Thief getThief() {
        List<Obstacle> thieves = obstacles.stream().filter(obstacle -> obstacle.getObstacleType()
                .equals(ObstacleType.THIEF)).collect(Collectors.toList());
        if (thieves.isEmpty()) {
            return null;
        } else {
            return (Thief) thieves.get(0);
        }
    }

    @Override
    public Pit getPit() {
        List<Obstacle> pits = obstacles.stream().filter(obstacle -> obstacle.getObstacleType()
                .equals(ObstacleType.PIT)).collect(Collectors.toList());
        if (pits.isEmpty()) {
            return null;
        } else {
            return (Pit) pits.get(0);
        }
    }

    @Override
    public LocationAddress getAddress() {
        return new LocationAddress(rowNumber, columnNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectibleList, openings, rowNumber, columnNumber);
    }

    @Override
    public boolean isHasArrow() {
        return hasArrow;
    }

    @Override
    public void addObstacles(Set<Obstacle> obstacles) {
        if (obstacles == null) {
            throw new IllegalArgumentException("Null obstacles cannot be added");
        }
        this.obstacles.addAll(obstacles);
    }

    @Override
    public void makeThiefEscapeDungeon() {
        obstacles.removeIf(obstacle -> obstacle.getObstacleType() == ObstacleType.THIEF);
    }
}
