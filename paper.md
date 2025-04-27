# Java Swing Dungeon Game: Design, Architecture, and Testing

## Abstract

The **Java Swing Dungeon Game** is a modular, extensible Java-based template for building grid-based games or desktop applications. This project demonstrates the **Model-View-Controller (MVC)** design pattern, **Test-Driven Development (TDD)** practices, and clean architecture principles. It provides a reusable codebase for developers looking to create their own dungeon maze games, study Java Swing, or experiment with software architecture and game development. This paper outlines the software design, development practices, and usage to assist developers in creating their own applications based on the project.

## Introduction

This project serves as a comprehensive **template** for creating Java Swing-based games and desktop applications. With a focus on **reusability**, the game provides a robust foundation for developers to build upon. By using **MVC architecture**, the project demonstrates a clean separation of concerns, enabling easy maintenance and scalability. Furthermore, the project incorporates **Test-Driven Development (TDD)**, ensuring the reliability of core game logic and providing a strong basis for further enhancements.

### Statement of Need

Many developers seeking to build grid-based or dungeon games face challenges in structuring their applications efficiently, especially when working with Java Swing. This project aims to address these challenges by providing a **modular**, **extensible**, and **well-documented** codebase. It is designed to support both newcomers to Java Swing and experienced developers who want to focus on adding new features without worrying about setting up the fundamental architecture.

Unlike many Java game frameworks, this project demonstrates an application of the **MVC pattern** in the context of a game, showcasing how it can be leveraged to create scalable and maintainable desktop applications. Moreover, the use of **JUnit tests** ensures the core functionality is well-tested, making it easier to extend the game with confidence.

## Software Description

The **Java Swing Dungeon Game** project is built around the **Model-View-Controller (MVC)** design pattern. This pattern divides the code into three distinct components:

1. **Model**: Contains the core game logic, including the player, enemies, items, and tiles.
2. **View**: Handles the graphical user interface, rendering the game world and player interactions.
3. **Controller**: Manages user input and updates the model and view accordingly.

This separation ensures that the game’s logic, UI, and user interactions are kept independent, allowing for easy modifications and extensions.

### Features

- **Clean Architecture**: The application follows the **MVC** design pattern to separate concerns.
- **Testable Code**: The codebase includes **JUnit tests** to verify core game logic, ensuring stability as new features are added.
- **Extensible**: The game is designed for easy customization—developers can add new enemies, items, or features with minimal changes to the existing codebase.

## Codebase Structure

```
JavaSwingDungeonGame/
├── src/
│   └── dungeon/
│       ├── drivers/                  # Entry point of the game
│       ├── location/                 # Cave and tunnel logic
│       ├── maze/                     # Maze generation and logic for keeping it connected
│       ├── obstacles/                # Obstacle models and their logic
│       ├── player/                   # Player-related logic (movement, interactions)
│       ├── view/                     # GamePanel - responsible for rendering game graphics
│       └── RandomGenerator.java      # Encapsulated random generation logic for game elements
├── test/                             # JUnit tests for core model logic
├── assets/                           # Screenshots, icons, and other visual assets
├── README.md                         # Main project documentation (setup, features, etc.)
├── GAMEPLAY.md                       # Detailed game instructions and controls
├── LICENSE                           # Project's open-source license (e.g., MIT)
└── paper.md                          # Research paper
```

### Usage

1. **Clone the repository:**
   ```bash
   git clone https://github.com/anizmo/JavaSwingDungeonGame.git
   cd JavaSwingDungeonGame
   ```

2. **Open the project in your favorite IDE** (IntelliJ, Eclipse, VSCode).

3. **Run the game**:
   Launch `Main.java` to start the game. Use the arrow keys to navigate through the dungeon.

4. **Run tests**:
   The tests are located in the `tests/` directory. Run them using your IDE’s testing framework or the command line.

## Software Documentation

For detailed documentation on how the software is structured and how you can extend it, see the **README.md** and **GAMEPLAY.md** files in the repository. These resources provide instructions for both **playing the game** and **building your own version** with new features and mechanics.

## Testing

This project follows **Test-Driven Development (TDD)** principles. The core logic of the game is tested using **JUnit 5**, providing confidence in the reliability of its functionality. Below is an example of a test case for the **Player** model:

```java
@Test
public void testPlayerMovement() {
    Player player = new Player(0, 0);
    player.move(Direction.RIGHT);
    assertEquals(1, player.getX());
    assertEquals(0, player.getY());
}
```

### Validation

The game logic has been validated using **JUnit** tests for the **Player**, **Enemy**, and **Item** models. Additional tests ensure that the **Controller** responds correctly to user inputs and updates the game state.

## Related Work

While there are many open-source Java game libraries available, this project distinguishes itself by integrating **MVC architecture** into a game framework. This approach makes it easier to maintain and extend. Other game frameworks, such as **LibGDX** or **jMonkeyEngine**, provide more advanced game development tools but often come with a steeper learning curve. This project focuses on providing an easily understandable structure suitable for learning **Java Swing**, software architecture, and basic game mechanics.

**Java Swing** is a well-known library for building graphical user interfaces, but it is rarely used for game development. This project provides a lightweight framework for game development with **Java Swing**, making it a useful resource for developers looking to explore the possibilities of Swing in game development.

## License

This project is licensed under the **MIT License**. You are free to modify and distribute the code under the terms of the license.

## References

1. **Model-View-Controller (MVC)** – The MVC pattern is a widely used software design pattern that helps in organizing code by separating the user interface from the business logic. This makes the codebase easier to maintain and scale.
2. **JUnit 5** – A widely used testing framework for Java, providing annotations and assertions to write clean and efficient unit tests.

