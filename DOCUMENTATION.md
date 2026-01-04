# Media Management System

## 📖 Introduction

The **Media Management System** is a comprehensive console-based Java application designed to help users organize, track, and manage their media collection. The system supports three types of media:

- **🎬 Movies** - Feature films with theatrical release information
- **📺 Series** - TV shows with multiple seasons and episodes
- **🎥 Documentaries** - Educational and informational content

### Key Features

✅ **Browse & Search** - View and filter your entire media collection  
✅ **Add Media** - Create new movies, series, and documentaries with detailed information  
✅ **Episode Management** - Add seasons and episodes to series with full tracking  
✅ **Rating System** - Rate individual media items and episodes (0-5 stars)  
✅ **Watch Tracking** - Mark media and episodes as watched  
✅ **Statistics** - View comprehensive statistics about your collection  
✅ **Delete Operations** - Remove media, seasons, or individual episodes  
✅ **Genre Filtering** - Organize by 10+ movie/series genres and 8+ documentary genres

---

## 🗂️ Project Structure

The project follows a clean **3-layer architecture** with clear separation of concerns:

```
Media-Management-System/
│
├── Main.java                    # Application entry point
│
├── Models/                      # Data models (entities)
│   ├── Media.java               # Abstract base class
│   ├── Movie.java               # Movie entity
│   ├── Series.java              # Series entity with episodes
│   ├── Episode.java             # Episode entity
│   └── Documentary.java         # Documentary entity
│
├── Service/                     # Business logic & utilities
│   ├── MediaDatabase.java       # Database operations
│   ├── MediaTypes.java          # Media type enumeration
│   ├── Genre.java               # Genre interface
│   ├── Movie_Series_Genres.java # Available genres for Movies and Series
│   ├── Documentary_Genres.java  # Available genres for Documentaries
│   └── InputChecks.java         # Input validation utilities
│
└── UI/                          # User interface classes
    ├── UIHelper.java            # UI utilities & formatting
    ├── Addmedia.java            # Add movies/documentaries
    ├── AddSeries.java           # Add/modify series
    ├── BrowseMedia.java         # Browse & view media
    ├── SearchMedia.java         # Search & filter media
    ├── DeleteMedia.java         # Delete operations
    └── Statistics.java          # Statistics display
```

---

## 📦 Package Breakdown

### 1️⃣ **Models Package** - Data Entities

The **Models** package contains all the data structures representing media items in the system.

#### **Media.java** (Abstract Base Class)
The foundation for all media types. Defines common properties and behaviors:

- **Properties**: title, director, release year, genre, rating, watch status, duration
- **Methods**: `displayInfo()`, `markAsWatched()`, `setRating()`, `getRatingStars()`
- **Abstract**: `getMediaType()` - must be implemented by subclasses
- **Key Feature**: Star rating system (★★★★★) with ANSI color formatting

#### **Movie.java**
Extends `Media` to represent feature films.

- **Additional Property**: `theatricalRelease` (boolean) - indicates if released in theaters
- **Genre Type**: Uses `Movie_Series_Genres`
- **Duration**: Single integer value in minutes
- **Display**: Shows all movie details including theatrical release status

#### **Series.java**
Extends `Media` to represent TV shows with seasons and episodes.

- **Properties**: 
  - `nbOfSeasons` - total number of seasons
  - `episodes` - List of Episode objects
- **Episode Management**:
  - `addEpisode()` - adds new episodes, auto-creates seasons if needed
  - `removeEpisode()` - removes episodes, auto-deletes empty seasons
  - `addSeason()` / `removeSeason()` - season operations
- **Rating System**: 
  - Individual episode ratings
  - Season average ratings
  - Series overall rating (calculated from all episodes)
- **Watch Tracking**:
  - Individual episode watch status
  - `isFullyWatched()` - checks if all episodes are watched
  - `markEpisodeAsWatched()` - marks specific episodes
- **Key Features**:
  - Auto-renumbering of episodes when one is deleted
  - Season shifting when a season is removed
  - Dynamic duration calculation from all episodes

#### **Episode.java**
Represents individual episodes within a series.

- **Properties**: title, episode number, season number, duration, rating, watch status
- **Numbering**: Episodes are numbered sequentially within each season (1, 2, 3...)
- **Rating**: Independent 0-5 star rating system
- **Display**: Shows episode info with watch status and rating

#### **Documentary.java**
Extends `Media` to represent documentaries.

- **Additional Properties**:
  - `topic` - subject matter of the documentary
  - `isEducational` - flag indicating educational value
- **Genre Type**: Uses `Documentary_Genres`
- **Display**: Shows documentary details with topic and educational status

---

### 2️⃣ **Service Package** - Business Logic & Utilities

The **Service** package handles all business operations, data management, and utility functions.

#### **MediaDatabase.java** (Core Business Logic)
Central database class that manages all media operations.

**Initialization**:
- Automatically populates database with 20 movies, 20 documentaries, and 3 series
- Each series comes with pre-populated episodes

**Media Management**:
- `addMedia()` / `addMovie()` / `addSeries()` / `addDocumentary()` - creation methods
- `removeMedia()` / `removeSeason()` / `removeEpisode()` - deletion methods
- `addEpisodeToSeries()` / `addSeasonToSeries()` - series management

**Search Operations**:
- `searchByType()` - filter by media type
- `searchByGenre()` - filter by genre
- `searchByTitle()` - search by title (case-insensitive, partial match)
- `searchByDirector()` - search by director name
- `searchByWatchedStatus()` - filter watched/unwatched

**Rating & Watch Operations**:
- `markAsWatched()` - marks media as watched
- `rateMedia()` - rates movies/documentaries
- `rateEpisode()` - rates specific episodes

**Statistics**:
- `getAverageRating()` - overall collection rating
- `getTotalMediaCount()` - total items count
- `getAllMedia()` - returns copy of entire collection

**Lookup Methods**:
- `getMediaByTitle()` - finds specific media
- `getSeriesByTitle()` - finds specific series
- `mediaExists()` - checks existence

#### **MediaTypes.java** (Enumeration)
Defines the three media types:
- `MOVIE`
- `SERIES`
- `DOCUMENTARY`

Used throughout the application for type checking and filtering.

#### **Genre.java** (Interface)
Contract for all genre implementations.

**Methods**:
- `getDisplayName()` - returns formatted genre name for display
- `matches(Genre other)` - checks if two genres match

#### **Movie_Series_Genres.java** (Enumeration)
Implements `Genre` interface for movies and series.

**Available Genres**:
- ACTION, COMEDY, DRAMA, HORROR, ROMANCE
- SCIENCEFICTION, THRILLER, ANIMATION
- ADVENTURE, CRIME, UNKNOWN

**Key Methods**:
- `getDisplayName()` - converts enum name to readable format (e.g., "SCIENCEFICTION" → "science fiction")
- `matches()` - compares genres for filtering

#### **Documentary_Genres.java** (Enumeration)
Implements `Genre` interface for documentaries.

**Available Genres**:
- NATURE, SCIENCE, TRUE_CRIME, HISTORICAL
- BIOGRAPHICAL, SPORTS, HEALTH, TECHNOLOGY, UNKNOWN

**Key Methods**:
- Same structure as Movie_Series_Genres
- Separate enum to maintain type safety

#### **InputChecks.java** (Validation Utilities)
Static utility class for input validation.

**Validation Methods**:
- `isValidNumber(String)` - validates positive integers
- `isValidDecimal(String)` - validates decimal numbers (for ratings)
- `isSimpleInteger(String)` - validates any integer (including negative)
- `isValidSelection(String)` - validates "X.Y" format for browse selections

**Formatting Methods**:
- `capitalizeWords(String)` - capitalizes first letter of each word

**Usage**: Called throughout UI classes to validate user input before processing

---

### 3️⃣ **UI Package** - User Interface

The **UI** package contains all user interface classes that handle display and user interaction.

#### **UIHelper.java** (Display Utilities)
Centralized utility class for all UI operations. **This is the heart of the visual presentation.**

**ANSI Color Codes**:
- `GREEN`, `RED`, `YELLOW`, `BLUE`, `MAGENTA`, `CYAN`, `WHITE`
- `RESET` - resets colors
- `BOLD`, `UNDERLINE` - text styles
- `BG_RED` - background color

**Display Methods**:
- `printHeader(title, subtitle)` - decorative header with border
- `printSection(number, title, color)` - menu section separator
- `printFooter(message)` - decorative footer
- `printMediaBox(media)` - displays media in a bordered box

**Message Methods**:
- `printSuccess(message)` - green success message with ✓
- `printFailure(message)` - red error message with background
- `printInfo(message)` - cyan info message with 💡

**Text Utilities**:
- `centerText(text)` - centers text on console (78 chars wide)
- `stripAnsi(text)` - removes ANSI codes for length calculation

**Screen & Input**:
- `clearScreen()` - clears console (prints 100 newlines)
- `readInt()` - reads validated integer input
- `readKey()` - waits for any key press
- `getScanner()` - returns Scanner instance

**Design Philosophy**: 
- All visual elements go through UIHelper
- Consistent 78-character console width
- Color-coded feedback (green = success, red = error, cyan = info)

#### **Addmedia.java** (Add Movies & Documentaries)
Handles creation of movies and documentaries.

**Main Entry Point**:
- `addmedia(database, mediaList)` - displays menu to choose media type

**Movie Creation Flow**:
1. **Title & Director** - comma-separated input validation
2. **Release Year** - validates range (1888 to current year)
3. **Duration** - validates positive integer (max 10000 minutes)
4. **Theatrical Release** - Y/N confirmation
5. **Genre Selection** - displays genre menu with numbers 1-10

**Documentary Creation Flow**:
1. **Title & Director** - same as movies
2. **Topic** - text input for subject matter
3. **Release Year** - validates range
4. **Duration** - validates positive integer
5. **Educational Status** - Y/N confirmation
6. **Genre Selection** - documentary genres 1-8

**Key Features**:
- Duplicate detection - prevents adding media with existing titles
- Input validation at every step
- Visual genre menu with `displayGenreMenu(isMovieOrSeries)`
- Retry loops for invalid input

**Genre Display**:
- `displayGenreMenu(boolean)` - shared method for genre selection
- `true` = Movie/Series genres
- `false` = Documentary genres

#### **AddSeries.java** (Add & Modify Series)
Extends `Addmedia` to handle series creation and episode management.

**Main Menu** (`addSeries`):
1. **Add a New Series** - creates empty series
2. **Add Episodes/Seasons to Existing Series** - modifies existing
3. **Back** - returns to main menu

**New Series Creation Flow**:
1. Title & Director input (comma-separated)
2. Release Year validation
3. Genre selection (Movie/Series genres)
4. Creates series with 0 seasons, 0 episodes
5. Optional: Add episodes immediately after creation

**Modify Existing Series**:
- Lists all series with season/episode counts
- User selects by number
- Opens modification interface

**Series Modification Interface**:
- **Auto-creates Season 1** if series has 0 seasons
- **Add New Season** - increments season count
- **Add Episode to Existing Season** - episode creation wizard

**Episode Addition Flow**:
1. **Season Selection** - auto-selects if only 1 season exists
2. **Episode Number** - auto-generated (next in sequence)
3. **Episode Title** - validates uniqueness within season
4. **Duration** - validates positive integer (max 500 minutes)

**Key Features**:
- Series model methods for episode management
- Duplicate title detection within seasons
- Auto-incrementing episode numbers
- Season auto-creation when adding first episode

**Inheritance**:
- Extends `Addmedia` to reuse `displayGenreMenu()`

#### **BrowseMedia.java** (View & Interact with Media)
Provides browsing and interaction with the media collection.

**Display Format**:
```
1) MOVIES (count)
   1. Title (Year) - Director
   2. Title (Year) - Director

2) SERIES (count)
   1. Title (Year) - Director

3) DOCUMENTARIES (count)
   1. Title (Year) - Director
```

**Selection System**:
- Format: `type.number` (e.g., `1.5` = 5th movie, `2.3` = 3rd series)
- Validates selection format with regex
- Filters by media type, then gets item by index

**Simple Media View** (Movies/Documentaries):
1. Displays media in decorated box
2. Options:
   - **1. Mark as Watched** - toggles watch status
   - **2. Rate** - prompts for rating (0-5)
   - **Q. Back** - returns to browse list

**Series View - Level 1** (Series Overview):
- Displays series info with season list
- Shows episodes per season and average rating
- User enters season number to drill down

**Series View - Level 2** (Season View):
- Lists all episodes in selected season
- Shows season stats (episode count, duration)
- User enters episode number to view details

**Series View - Level 3** (Episode View):
- Displays episode details
- Options:
   - **1. Mark as Watched** - marks episode (auto-marks series if all watched)
   - **2. Rate** - rates episode (updates series rating)
   - **Q. Back** - returns to season view

**Key Features**:
- Hierarchical navigation (Series → Season → Episode)
- Watch status color coding (magenta for watched)
- Series model methods for episode lookup
- Automatic series rating recalculation

#### **SearchMedia.java** (Search & Filter)
Advanced search and filtering interface with toggleable filters.

**Filter System**:

**Media Type Filters** (1-3):
- 1. Movie ☐/☑
- 2. Series ☐/☑
- 3. Documentary ☐/☑

**Genre Filters** (10-27):
- **Movie/Series Genres** (10-19): Only shown when Movie or Series type is active
- **Documentary Genres** (20-27): Only shown when Documentary type is active

**Input Modes**:
1. **Filter Toggle** - enter numbers separated by dots
   - **Type filters only**: `1.2` (Movie + Series)
   - **Genre filters only**: `10.14.15` (Action + Romance + Sci-Fi)
   - **Cannot mix**: `1.10` is NOT allowed (type + genre together)
2. **Text Search** - enter title or director name
   - Searches within active filters
   - Case-insensitive partial matching
3. **Empty Input** - press Enter
   - Shows all media matching active filters

**Search Logic**:
- Combines title and director searches (union)
- Applies type filters first, then genre filters
- Results are deduplicated using Set

**Post-Search Options**:
1. **New Search** - clears all filters, starts fresh
2. **Continue Searching** - keeps filters, refine search
3. **Confirm Search** - opens browse interface with results
4. **Back to Main Menu** - exits search

**Filter Validation**:
- Genre filters only available when corresponding type is active
- Example: Can't select Documentary genres if Documentary type not selected
- Invalid numbers ignored with warning message

**Key Features**:
- Visual checkbox indicators (☐ = off, ☑ = on)
- Dynamic genre menu based on active types
- Filter persistence across searches
- Integration with `BrowseMedia` for result interaction

#### **DeleteMedia.java** (Delete Operations)
Handles deletion at multiple levels with confirmation prompts.

**Main Interface**:
- Shows browse list (same format as BrowseMedia)
- Selection format: `type.number` (e.g., `1.5`)
- Routes to appropriate deletion handler

**Simple Media Deletion** (Movies/Documentaries):
1. Displays media in box
2. Confirmation prompt with warning
3. Deletes entire media item
4. Success/failure message

**Series Deletion - Level 1** (Series Menu):
- Shows series info and season list
- Options:
  - **1. Delete entire series** - removes all data
  - **2. Select season to manage** - drill down
  - **Q. Go back**

**Series Deletion - Level 2** (Season Menu):
- Shows episode list for selected season
- Options:
  - **1. Delete entire season** - removes all episodes, renumbers remaining seasons
  - **2. Select episode to delete** - drill down
  - **Q. Go back**

**Series Deletion - Level 3** (Episode Deletion):
- Lists episodes in season
- User selects episode number
- Confirmation prompt
- Deletes episode, renumbers remaining episodes
- **Auto-deletes season** if last episode removed

**Confirmation System**:
- All deletions require Y/N confirmation
- Red warning message: "⚠️ WARNING: This action cannot be undone!"
- Clearly states what will be deleted

**Key Features**:
- Hierarchical deletion (Series → Season → Episode)
- Auto-cleanup of empty seasons
- Episode renumbering after deletion
- Database method calls (not local deletion)
- Fresh media list on each loop iteration

#### **Statistics.java** (Statistics Display)
Comprehensive statistics dashboard for the media collection.

**Overall Statistics**:
- 📦 **Total Media Items** - count of all media
- ⭐ **Average Rating** - overall collection rating
- ⏱️ **Total Duration** - sum of all media (in hours and minutes)

**By Type Statistics**:
- 🎬 Movies count
- 📺 Series count + total episodes across all series
- 🎥 Documentaries count

**Ratings by Type**:
- Average rating for Movies
- Average rating for Series
- Average rating for Documentaries
- Shows "No ratings yet" if type has no rated items

**Watch Status**:
- ✓ Watched count with percentage
- ⏳ Unwatched count with percentage
- **Visual Progress Bar**: 
  ```
  [████████████████████████░░░░░░░░░░░░░░░░░░░░░░░░░░]
  ```
  - 50 characters wide
  - Green filled blocks (█) for watched
  - Gray empty blocks (░) for unwatched

**Top 5 Rated Media**:
- Lists top 5 highest-rated items
- Shows type icon (🎬/📺/🎥) + title + star rating
- Sorted descending by rating using bubble sort
- Only includes media with ratings > 0

**Sorting Algorithm**:
- `sortByRating()` - bubble sort implementation
- Sorts in descending order (highest to lowest)
- Handles empty lists gracefully

**Display Logic**:
- Each section separated by visual dividers
- Color-coded by media type (YELLOW/CYAN/GREEN)
- Returns to main menu on any key press

---

## 🚀 How Main.java Orchestrates the Application

### **Application Entry Point**

The `Main` class serves as the **orchestrator** of the entire application. It's responsible for:

1. **Initialization**
2. **Main Menu Loop**
3. **Feature Navigation**
4. **Graceful Exit**

### **Startup Sequence**

```java
public static void main(String[] args) throws InterruptedException {
    // 1. Initialize database with pre-populated data
    MediaDatabase database = new MediaDatabase();
    
    // 2. Display welcome header
    UIHelper.printHeader("MEDIA MANAGEMENT SYSTEM", 
                         "CONSOLE-BASED JAVA APPLICATION");
    Thread.sleep(2000); // 2-second pause
    
    // 3. Wait for user to continue
    UIHelper.readKey();
    UIHelper.clearScreen();
    
    // 4. Enter main loop
    // ...
}
```

### **Main Menu Loop**

The application runs in a continuous loop until the user chooses to exit:

```java
boolean running = true;

while (running) {
    // Clear screen and display menu
    UIHelper.clearScreen();
    UIHelper.centerText("MAIN MENU");
    
    // Display menu options
    UIHelper.printSection("1", "📁 Browse Media", UIHelper.BLUE);
    UIHelper.printSection("2", "🔍 Search Media", UIHelper.BLUE);
    // ... more options
    
    // Read user choice
    int menuChoice = UIHelper.readInt();
    
    // Route to appropriate feature
    switch (menuChoice) { ... }
}
```

### **Feature Routing**

Each menu option delegates to a specific UI class:

| Menu Choice | UI Class      | Method Called                  | Purpose                        |
|-------------|---------------|--------------------------------|--------------------------------|
| **1**       | `BrowseMedia` | `.browseMedia(database, list)` | Browse and interact with media |
| **2**       | `SearchMedia` | `.searchMedia(database)`       | Search and filter media        |
| **3**       | `Addmedia`    | `.addmedia(database, list)`    | Add new media                  |
| **4**       | `DeleteMedia` | `.deleteMedia(database, list)` | Delete media                   |
| **5**       | `Statistics`  | `.displayStatistics(database)` | View statistics                |
| **6**       | Exit          | `running = false`              | Exit application               |

### **Data Flow**

```
Main.java
    ↓
    ├─→ Creates MediaDatabase (Service)
    │       ↓
    │       └─→ Populates with Media objects (Models)
    │
    └─→ Calls UI classes (UI)
            ↓
            ├─→ Display via UIHelper
            ├─→ Validate via InputChecks (Service)
            ├─→ Modify via MediaDatabase (Service)
            └─→ Return to Main
```

### **Key Design Decisions**

1. **Single Database Instance**: One `MediaDatabase` object passed to all UI classes
2. **No Direct Model Access**: UI classes interact with Models only through MediaDatabase
3. **Separation of Concerns**: 
   - Main = Navigation
   - UI = User Interaction
   - Service = Business Logic
   - Models = Data Structure
4. **Error Handling**: Invalid inputs return to menu without crashing
5. **User Experience**: 
   - Thread.sleep() for message readability
   - clearScreen() between operations
   - Consistent error feedback

---

## 🎨 Design Patterns & Principles

### **1. Layered Architecture**
- **Presentation Layer** (UI) - User interface and interaction
- **Business Logic Layer** (Service) - Operations and validation
- **Data Layer** (Models) - Entity definitions

### **2. Single Responsibility Principle**
- Each class has one clear purpose
- `UIHelper` handles only display
- `InputChecks` handles only validation
- `MediaDatabase` handles only data operations

### **3. Inheritance Hierarchy**
```
Media (abstract)
    ├─→ Movie
    ├─→ Series
    └─→ Documentary

Addmedia
    └─→ AddSeries (extends for genre menu reuse)

Genre (interface)
    ├─→ Movie_Series_Genres (enum)
    └─→ Documentary_Genres (enum)
```

### **4. Polymorphism**
- `Media` base class allows treating all media types uniformly
- `displayInfo()` overridden in each subclass
- `getMediaType()` abstract method ensures type identification

### **5. Encapsulation**
- Private fields with public getters/setters
- Input validation in setter methods
- Internal helper methods (private) in UI classes

---

## 🔧 Technical Specifications

### **Requirements**
- **Java Version**: Java 8+ (uses lambda expressions, streams not required)
- **Dependencies**: None (pure Java, no external libraries)
- **Console**: UTF-8 support for emojis and special characters

### **Console Specifications**
- **Width**: 78 characters (standard terminal width)
- **Color Support**: ANSI escape codes
- **Clearing Method**: 100 newlines (cross-platform compatible)

### **Data Validation**
- **Release Years**: 1888 (first motion picture) to current year
- **Ratings**: 0.0 to 5.0 (enforced with clamping)
- **Durations**: Positive integers only
- **Movie Duration**: Max 10,000 minutes
- **Episode Duration**: Max 500 minutes

### **Pre-populated Data**
- **20 Movies**: Popular films across all genres
- **20 Documentaries**: Various topics and genres
- **3 Series**: Chernobyl (5 episodes), Loki (12 episodes), Moon Knight (6 episodes)

---

## 💡 Usage Tips

### **Browsing Media**
- Use format `type.number` (e.g., `2.3` for 3rd series)
- Watched items appear in magenta color
- Navigate series hierarchically: Series → Season → Episode

### **Searching Media**
- Toggle filters with numbers: `1.10.14` = Movies + Action + Romance
- Empty input shows all filtered media
- Text search looks in both title and director
- Genre filters only appear when relevant type is selected

### **Adding Series**
- Series created with 0 episodes initially
- Season 1 auto-created when adding first episode
- Episode numbers auto-increment per season
- Can add episodes immediately after series creation

### **Deleting Data**
- All deletions require confirmation
- Deleting last episode removes entire season
- Season numbers shift down after season deletion
- Episode numbers shift down after episode deletion

### **Rating System**
- Movies/Documentaries: Single rating per item
- Series: Rating calculated from all episode ratings
- Episodes: Individual ratings per episode
- Stars display: ★★★★★ (5-star maximum)

---






## 👨‍💻 Project Information

**Project Made By**: Lagraa Yasser Assil  
**Type**: Console-Based Java Application  
**Purpose**: Educational project demonstrating OOP principles and Java fundamentals  
**Architecture**: 3-Layer (UI, Service, Models)  
**Design**: Clean separation of concerns with modular components

---



**End of Documentation** 📖