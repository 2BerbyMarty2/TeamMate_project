# TeamMate_project

## Overview

TeamMate_project is an intelligent team formation system designed for use by a University Gaming Club. The system provides automated grouping of participants based on skills and preferences parsed from input CSV files. Developed in Java, this project demonstrates robust Object-Oriented Design and applies key OOP principles and design patterns as part of university coursework.

## Features

- Reads participant and organizer data from `.csv` files.
- Automatically generates balanced teams based on predefined criteria (e.g., skill level, role preferences).
- Outputs team assignments to `teams_output.csv`.
- Includes logging for application actions and errors (`application_log.txt`).
- Unit tests for key logic modules.

## File Structure

```
TeamMate_project/
├── src/                 # Main source code
├── Test/                # Unit and integration tests
├── application_log.txt  # Log file for application events
├── organizers.csv       # Organizers data input
├── participants_sample.csv # Participants data input
├── teams_output.csv     # Generated teams output
├── .gitignore           # Files/directories to ignore in version control
├── ood_project.iml      # IntelliJ IDEA configuration file
└── real_passwords.csv   # List of authorized passwords
```

## Architecture

The core architecture consists of the following main classes (edit these to fit your implementation):

- `Participant`: Encapsulates participant information and attributes.
- `Organizer`: Manages organizer-specific data.
- `Team`: Represents a game team, provides methods to add/remove participants.
- `TeamFormationEngine`: Applies logic to assign participants to teams.
- `CSVParser`: Utilities for reading/writing CSV files.
- `Logger`: Handles logging to `application_log.txt`.

## Object-Oriented Principles Applied

- **Encapsulation**: Each class secures its own state and exposes public methods for interaction.
- **Inheritance**: Shared properties (e.g., `User` superclass for `Participant` and `Organizer`) reduce code duplication.
- **Polymorphism**: Methods behave differently based on the object type (e.g., custom sorting/comparison).
- **Abstraction**: Logic for team formation is isolated in dedicated classes/modules.

## Design Patterns Used

- **Factory Pattern**: Used to instantiate participant and organizer objects from CSV input.
- **Singleton Pattern**: Logger instance ensures centralized logging.
- **Strategy Pattern**: Allows switching between different team formation algorithms.

## Setup Instructions

1. **Clone the repository**
   ```sh
   git clone https://github.com/2BerbyMarty2/TeamMate_project.git
   cd TeamMate_project
   ```

2. **Open in IntelliJ IDEA**  
   *(or any Java IDE; ensure `.iml` file is loaded)*

3. **Place CSV input data**  
   - `participants_sample.csv` — List of participants
   - `organizers.csv` — List of organizers

4. **Run the main class**
   - Entry point: `src/.../Main.java` *(edit to your actual main file)*

5. **Output**
   - Generated teams and logs are saved to root-level CSV and TXT files.

## Testing

- All tests are located in the `Test` directory.
- Run using your IDE’s test runner, or with `mvn test` if using Maven.

## Contact

For questions, contact the author via [GitHub](https://github.com/2BerbyMarty2).

---

*This project is part of OOD coursework at [University Name]. Last updated: Dec 2025.*
