# Connect Four — Kotlin Android Application

A Connect Four Android application developed using **Kotlin and Jetpack Compose**, featuring an AI opponent powered by the Minimax algorithm with alpha-beta pruning, local two-player gameplay, customisable game settings, and player statistics.

The project explores Android application development, artificial intelligence, game logic, state management, and responsive user interface design.

## Project Overview

Connect Four is a two-player strategy game in which players take turns placing discs into a vertical grid. The objective is to connect four discs of the same colour horizontally, vertically, or diagonally.

This implementation supports both human-versus-human and human-versus-computer gameplay, alongside customisation features and game statistics.

### Key Features

- **Single-player mode:** Play against an AI opponent using Minimax search with alpha-beta pruning.
- **Local multiplayer:** Two players compete on the same device.
- **Customisable board sizes:** Choose between 6×5, 7×6, and 8×7 grids (columns × rows).
- **Player customisation:** Configure player names, avatars, and disc colours.
- **Game statistics:** Track completed games, wins, losses, draws, and win rates.
- **Undo functionality:** Reverse previous moves, including both player and AI moves in single-player mode.
- **Game reset:** Restart the current game without closing the application.
- **Responsive interface:** Supports portrait and landscape orientations.
- **State management:** Uses Android ViewModels and Jetpack Compose state management.

## Technical Implementation

### 1. Artificial Intelligence — Minimax with Alpha-Beta Pruning

The AI opponent is implemented in `AIPlayer.kt` using a depth-limited Minimax search algorithm.

Minimax evaluates potential future game states by considering moves from both players:

- **Maximising player:** The AI attempts to select moves that maximise its evaluation score.
- **Minimising player:** The human opponent is assumed to choose moves that minimise the AI's score.

The implementation applies **alpha-beta pruning** to eliminate branches that cannot improve the current decision, reducing unnecessary search operations.

The AI uses a maximum search depth of four, balancing decision quality and computational cost.

#### Heuristic Evaluation

Rather than relying exclusively on terminal game states, the AI uses a positional scoring heuristic.

Each board position is assigned a score based on the number of potential four-disc winning combinations that include that position.

The evaluation considers:

- Horizontal winning combinations
- Vertical winning combinations
- Diagonal winning combinations
- Board positions occupied by the AI and human player

The resulting evaluation favours positions that contribute to more potential winning combinations.

#### Immediate Threat Detection

Before performing Minimax search, the AI checks whether the human player has an immediate winning move.

If such a move is detected, the AI prioritises blocking it.

This combines direct threat detection with deeper move evaluation to improve defensive behaviour.

### 2. Game Logic and Board Management

The core gameplay state is managed by `GameViewModel.kt`.

The game board is represented as a two-dimensional collection of integers:

| Value | Meaning |
|---|---|
| `0` | Empty cell |
| `1` | Player One |
| `2` | Player Two / AI |

When a player selects a column, the application finds the lowest available cell and places the corresponding disc.

After each move, the game checks for:

- Horizontal connections of four discs
- Vertical connections of four discs
- Diagonal connections of four discs
- Draw conditions when the board is full

The application also manages turn progression, move history, game completion, and AI interaction.

#### Undo and Reset

The game maintains a move history to support undo functionality.

- In local multiplayer, undo reverses the most recent move.
- In single-player mode, undo can reverse the previous player and AI moves.

Undo is available while a game remains in progress.

The reset functionality clears the board and restarts the current game.

### 3. State Management

The application uses Android's `ViewModel` architecture to separate gameplay and settings state from the user interface.

**GameViewModel**

Responsible for:

- Board state and dimensions
- Current player and turn progression
- Move validation and history
- AI move execution
- Win and draw detection
- Game statistics
- Game reset and undo

**SettingsViewModel**

Responsible for:

- Player names
- Selected avatars
- Disc colours
- Player customisation state

Jetpack Compose observes state changes and updates the relevant interface components.

This separation helps organise gameplay logic independently from screen presentation.

### 4. User Interface — Jetpack Compose

The application interface is built using **Jetpack Compose and Material 3**.

The main screens include:

| Screen | Description |
|---|---|
| Main Menu | Navigation to single-player, multiplayer, statistics, and settings |
| Gameplay | Interactive game board, player indicators, move counter, and controls |
| Settings | Board size selection and player configuration |
| Player One Settings | Player One's name, avatar, and disc colour |
| Player Two Settings | Player Two's name, avatar, and disc colour |
| Statistics | Game results, win rates, and performance information |

The gameplay and statistics screens provide separate layouts for portrait and landscape orientations.

Navigation between screens is coordinated through the main activity using Compose state.

### 5. Game Statistics

The application tracks completed games and their outcomes.

Recorded statistics include:

- Total games played
- Player One wins
- Player Two wins
- Draws
- Player-specific losses
- Win percentages
- Draw percentage

Statistics are calculated from the recorded game outcomes and displayed through a dedicated interface.

The statistics are maintained in application memory through the ViewModel rather than being stored in a persistent database.

## Technologies Used

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Android SDK | Android application development |
| Jetpack Compose | Declarative user interface |
| Material 3 | UI components and styling |
| Android ViewModel | State and lifecycle management |
| Minimax Algorithm | AI decision-making |
| Alpha-Beta Pruning | Search optimisation |

The AI algorithm and core gameplay logic were implemented directly in Kotlin without an external game AI library.

## Repository Structure

This repository contains a curated selection of the original application's Kotlin source code and graphical assets.

```text
connect-four-kotlin/
├── README.md
│
├── src/
│   ├── AIPlayer.kt
│   ├── GamePlay.kt
│   ├── GameViewModel.kt
│   ├── MainActivity.kt
│   ├── MainMenu.kt
│   ├── PlayerOneSettings.kt
│   ├── PlayerTwoSettings.kt
│   ├── Settings.kt
│   ├── SettingsViewModel.kt
│   ├── Statistics.kt
│   │
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
└── assets/
    ├── arrowimg.png
    ├── circleimg.png
    ├── eqtriimg.png
    ├── starimg.png
    └── triangleimg.png
```

### Source File Descriptions

| File | Responsibility |
|---|---|
| `AIPlayer.kt` | Minimax search, alpha-beta pruning, heuristic evaluation, and win detection |
| `GamePlay.kt` | Gameplay interface, interactive board, controls, and orientation-specific layouts |
| `GameViewModel.kt` | Game state, move processing, AI integration, undo, reset, and statistics |
| `MainActivity.kt` | Application entry point and screen navigation |
| `MainMenu.kt` | Main menu interface and game mode selection |
| `Settings.kt` | General settings interface and board configuration |
| `SettingsViewModel.kt` | Player customisation state |
| `PlayerOneSettings.kt` | Player One configuration interface |
| `PlayerTwoSettings.kt` | Player Two configuration interface |
| `Statistics.kt` | Game statistics presentation |
| `theme/` | Application colour schemes, typography, and themes |
| `assets/` | Custom graphical resources used by the application |

## Repository Scope

This repository is intended as a **source-code showcase** of the application's implementation.

The original project was developed in Android Studio. For portfolio presentation, the repository has been simplified to focus on the application logic, AI implementation, interface components, and graphical assets.

Gradle build configuration, generated Android resources, and IDE-specific files have been omitted.

As a result, the repository is not directly buildable as a standalone Android Studio project without restoring the necessary project configuration and resource structure.

The original Kotlin implementation has been preserved.

## Design Considerations and Limitations

### AI Search Depth

The AI uses a depth-limited Minimax search with a maximum depth of four.

This limits computational overhead but does not guarantee optimal play in every situation.

### Heuristic-Based Decisions

The AI evaluates positions using potential winning combinations and simulated future moves.

As a result, the quality of its decisions depends on the heuristic evaluation and search depth.

### Statistics Persistence

Game statistics are stored in the ViewModel during application use.

They are not persisted through a database or other permanent storage mechanism.

### Application Build

The repository contains selected application source files rather than the complete Android Studio build environment.

It is therefore intended primarily for technical review and portfolio presentation.

## Development and Contributions

This application was originally developed as a group project for undergraduate Android development coursework.

I served as the **primary developer**, responsible for the major application components, including:

- Game board design and gameplay implementation
- Single-player AI using Minimax and alpha-beta pruning
- Local two-player functionality
- Win detection, move handling, and gameplay controls
- Game statistics tracking
- User interface design and implementation
- Application state management and integration

The project provided practical experience in Android development, algorithm design, artificial intelligence, modular programming, and managing interactive application state.

## Copyright

Copyright © 2026 Jian Heng Koo. All rights reserved for my original contributions.

The source code and documentation authored by me are presented for portfolio and educational review.

No licence is granted to copy, modify, redistribute, or commercially use my original materials without permission.

Any contributions or materials created by other authors remain subject to their respective rights.
