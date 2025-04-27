<div align="center">

# Java Swing Game Template

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Forks](https://img.shields.io/github/forks/anizmo/JavaSwingDungeonGame?style=social)](https://github.com/anizmo/JavaSwingDungeonGame/network/members)
[![Stars](https://img.shields.io/github/stars/anizmo/JavaSwingDungeonGame?style=social)](https://github.com/anizmo/JavaSwingDungeonGame/stargazers)

</div>

A modular, extensible Java Swing-based Dungeon Maze Game demonstrating the **Model-View-Controller (MVC)** design pattern, **Test-Driven Development (TDD)**, and clean architecture principles.

This project serves as a **template** for building your own grid-based Java Swing games or desktop applications.

---

## ✨ Features

- Clean separation of concerns with MVC architecture
- Java Swing-based GUI rendering
- Reusable model classes for players, enemies, items, and tiles
- JUnit 5 test cases for core logic
- Easily extensible for new features: enemies, items, maps

---

## 📸 Game Screenshots

| Start Screen | Dungeon Maze Gameplay |
|:------------:|:----------------------:|
| ![Start Screen](https://github.com/anizmo/DisplayOnly/blob/main/screens/6.%20Scroll.png?raw=true) | ![Gameplay Screen](assets/gameplay_screen.png) |

*(Screenshots show the initial setup and live gameplay!)*

> **Looking to play the full game?** Check out the [GAMEPLAY.md](GAMEPLAY.md)!

---

## 🏗️ Project Structure

```bash
JavaSwingDungeonGame/
├── src/
│   ├── model/         # Core game logic: Player, Enemy, Tile, Items, etc.
│   ├── view/          # GamePanel - responsible for rendering graphics
│   ├── controller/    # GameController - handles user input and game coordination
│   └── Main.java      # Entry point of the game
├── tests/             # JUnit tests for unit testing model logic
├── assets/            # Screenshots and visual assets
├── README.md
├── GAMEPLAY.md        # Game-specific instructions and controls
├── LICENSE
└── paper.md           # Research paper for JOSS submission
```

---

## 🚀 Quick Start

1. **Clone the repository:**

```bash
git clone https://github.com/anizmo/JavaSwingDungeonGame.git
cd JavaSwingDungeonGame
```

2. **Open the project in your favorite IDE** (IntelliJ, Eclipse, VSCode).

3. **Run the game:**

Run `Main.java`. A dungeon window should open where you can move the player using arrow keys.

4. **Run tests:**

JUnit tests are located in the `tests/` directory. You can run them using your IDE's testing framework or CLI.

---

## 🎮 Want to Play the Dungeon Game?

If you're looking to **play the original Dungeon Maze Game** or read about the game controls, objectives, and gameplay rules, please check out the [GAMEPLAY.md](GAMEPLAY.md) file!

---

## 🛠️ How to Build Your Own Game

This project is designed for reusability. You can fork this repo and:

### Customize the Model

- Add new **Item** classes (`Weapon`, `Potion`, etc.).
- Add new **Enemy** behaviors by extending the `Enemy` class.
- Modify the `Tile` class to include new properties like water, fire, or magic portals.

```java
public class MagicTile extends Tile {
    private boolean isMagicPortal;

    @Override
    public void draw(Graphics g, int x, int y, int tileSize) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, tileSize, tileSize);
    }
}
```

### Extend the Controller

- Handle new key events or user actions.
- Implement inventory management, combat mechanics, or multiplayer inputs.

```java
case KeyEvent.VK_I -> model.openInventory();
```

### Enhance the View

- Add animations, status bars, or menus in `GamePanel`.
- Create new panels or popups for inventories, stats, and level selection.

```java
JOptionPane.showMessageDialog(frame, "You found a treasure!");
```

### Improve Testing

- Write additional JUnit tests as you expand functionality.
- Ensure high code coverage with tools like JaCoCo.

---

## 🧩 Why Use This Project?

- **Template Ready**: No need to set up MVC architecture from scratch.
- **Tested Core**: Reliable player movement, collision, and inventory logic.
- **Educational Value**: Great for students learning Java Swing and architecture patterns.
- **Extendable**: Designed with extensibility in mind—easy to add new features.

---

## 📋 Requirements

- Java 11 or above
- JUnit 5 (for running tests)

---

## 📚 Documentation

Detailed architecture and examples are available in the [paper.md](paper.md) file.

You can also explore the codebase directly:

- `model/Player.java`, `model/Tile.java`
- `view/GamePanel.java`
- `controller/GameController.java`

---

## 🤝 Contributing

Pull requests are welcome! Feel free to fork the repo and create your own versions.

When submitting a PR:
- Keep MVC structure intact.
- Include JUnit tests for new functionality.
- Update the README if necessary.

See [CONTRIBUTING.md](CONTRIBUTING.md) for more guidelines.

---

## 🧑‍💻 Author

**Anuj Ashok Potdar**  — [GitHub](https://github.com/anizmo)

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.



