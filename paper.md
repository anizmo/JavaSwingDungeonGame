# Summary

JavaSwingDungeonGame is an open-source graphical adventure game implementing:

1. **Procedural dungeon generation** using randomized Prim's algorithm with configurable:
   - Grid dimensions (rows × columns)
   - Wrapping/non-wrapping topology
   - Interconnectivity levels
   - Treasure/monster distribution

2. **Complex gameplay mechanics**:
   - Dual combat systems (ranged arrows + melee)
   - Monster AI (stationary vs mobile)
   - Environmental hazards (pits, thieves)
   - Fog-of-war exploration

3. **Model-View-Controller architecture** with:
   - Asynchronous Swing GUI
   - First-person + bird's-eye views
   - Input validation (100+ JUnit tests)

![Gameplay Demo](https://github.com/anizmo/DisplayOnly/blob/main/screens/6.%20Scroll.png?raw=true)

# Statement of Need

Traditional maze games often lack either configurability (e.g., Rogue-likes) or academic utility. This implementation bridges that gap by providing:

- **Research-ready codebase**: Clean OOP structure suitable for studying:
  - Procedural generation algorithms
  - GUI-state synchronization
  - Game AI patterns

- **Classroom adaptability**: Configurable via CLI arguments for:
  - CS1/2 assignments (Java OOP)
  - Algorithm courses (pathfinding/BFS)
  - Software engineering (MVC patterns)

- **Modding foundation**: Extensible through documented APIs for:
  - New monster types (`Monster` interface)
  - Custom collectibles (`Treasure` class)
  - Alternative maze generators (`DungeonFactory`)

# Features

## Core Components

| Module          | Functionality                          | Algorithms Used          |
|-----------------|----------------------------------------|--------------------------|
| `DungeonModel`  | Maze generation/state management       | Prim's, BFS              |
| `GameController`| Input handling/rules enforcement       | Observer pattern         |
| `SwingView`     | Dual-panel GUI rendering               | Custom painting          |

## Key Differentiators

1. **Wrapping Topology**  
   ```java
   // Example: Wrapping edge detection
   if (isWrapping) {
       row = (row + rows) % rows;
       col = (col + cols) % cols;
   }
   ```

2. **Smell Propagation System**
   ```java
   public SmellLevel calculateSmell(Player p, Monster m) {
       int distance = bfsDistance(p.getLocation(), m.getLocation());
       return (distance <= 2) ? SmellLevel.TERRIBLE : 
              (distance <= 4) ? SmellLevel.FAINT : SmellLevel.NONE;
   }
   ```

3. **Combat Resolution**  
   ![Combat Flowchart](https://github.com/anizmo/DisplayOnly/blob/main/screens/12.%20PlayerHandToHandBattle.png?raw=true)

# Installation

```bash
git clone https://github.com/anizmo/JavaSwingDungeonGame
cd JavaSwingDungeonGame
# Run with default settings
java -jar target/DungeonMaze.jar
```

**Configuration Flags**:
```bash
--rows 10          # Maze rows (default: 6)
--cols 15          # Maze columns (default: 8)
--wrapping         # Enable wrapping edges
--interconnectivity 3  # Extra paths between nodes
```

# Usage Examples

## Classroom Exercise (CS2)
```java
// Create non-wrapping maze with 20% treasure
DungeonFactory factory = new DungeonFactory()
    .setRows(8)
    .setCols(8)
    .setTreasurePercentage(20);
DungeonModel model = factory.create();
new GameController(model).start();
```

## Research Benchmarking
```java
// Time maze generation for different sizes
IntStream.range(10, 100).forEach(size -> {
    long start = System.nanoTime();
    new DungeonFactory().setRows(size).setCols(size).create();
    System.out.printf("%d,%d%n", size, System.nanoTime()-start);
});
```

# Community Guidelines

## Contribution
- Report bugs via [GitHub Issues](https://github.com/anizmo/JavaSwingDungeonGame/issues)
- Submit PRs to `develop` branch
- Follow Google Java Style Guide

## Support
Contact: [your.email@domain.com](mailto:your.email@domain.com)

## License
MIT (see [LICENSE](https://github.com/anizmo/JavaSwingDungeonGame/blob/main/LICENSE))

# Acknowledgements
Thanks to [Javatpoint](https://www.javatpoint.com/) for BFS reference implementation.

# References
- Dijkstra, E. W. (1959). A note on two problems in connexion with graphs. @article{dijkstra1959note
