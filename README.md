# 🎬 Media Management System

A console-based Java application for organizing and tracking your movies, series, and documentaries collection.

---

## 📋 Overview

**Media Management System** helps you manage your personal media library with features for browsing, searching, rating, and tracking what you've watched.

### Supported Media Types
- 🎬 **Movies** - Feature films with theatrical release tracking
- 📺 **Series** - TV shows with seasons and episodes
- 🎥 **Documentaries** - Educational content with topics

---

## ✨ Features

| Feature                | Description                                    |
|------------------------|------------------------------------------------|
| **Browse**             | View all media organized by type               |
| **Search & Filter**    | Find media by title, director, type, or genre  |
| **Add Media**          | Create new movies, series, and documentaries   |
| **Episode Management** | Add/remove seasons and episodes for series     |
| **Rating System**      | Rate media and episodes (0-5 stars)            |
| **Watch Tracking**     | Mark items as watched                          |
| **Statistics**         | View collection stats, top rated, and progress |
| **Delete Operations**  | Remove media, seasons, or episodes             |

---

## 🗂️ Project Structure

```
Media-Management-System/
├── Main.java              # Entry point
├── Models/                # Data entities (Media, Movie, Series, etc.)
├── Service/               # Business logic (Database, Genres, Validation)
└── UI/                    # User interface (Browse, Search, Add, Delete, etc.)
```

### Architecture Layers
- **Models**: Data structures (Media, Movie, Series, Documentary, Episode)
- **Service**: Business logic and utilities (MediaDatabase, Genres, InputChecks)
- **UI**: User interface classes (Browse, Search, Add, Delete, Statistics)

---

## 🎮 How to Use

### Main Menu Options
1. **Browse Media** - View and interact with your collection
2. **Search Media** - Filter by type/genre or search by title/director
3. **Add New Media** - Create movies, series, or documentaries
4. **Delete Media** - Remove items from collection
5. **Statistics** - View analytics about your collection
6. **Exit** - Close the application

### Quick Tips
- **Browse Selection**: Use format `type.number` (e.g., `1.5` for 5th movie)
- **Search Filters**: 
  - Type filters: `1.2` (Movies + Series)
  - Genre filters: `10.15` (Action + Sci-Fi)
  - **Cannot mix**: Don't use `1.10` (type + genre together)
- **Series Navigation**: Browse → Select Series → Select Season → Select Episode
- **Auto-numbering**: Episodes and seasons renumber automatically when deleted

---

## 📊 Pre-loaded Content

The application comes with sample data:
- **20 Movies** - Popular films (Inception, The Matrix, etc.)
- **20 Documentaries** - Various topics (Planet Earth, The Last Dance, etc.)
- **3 Series** - Chernobyl, Loki, Moon Knight (with episodes)

---

## 🎨 Genre Categories

### Movies & Series (10 genres)
Action, Comedy, Drama, Horror, Romance, Science Fiction, Thriller, Animation, Adventure, Crime

### Documentaries (8 genres)
Nature, Science, True Crime, Historical, Biographical, Sports, Health, Technology

---

## 🔧 Technical Details

- **Language**: Java 8+
- **Dependencies**: None (pure Java)
- **Console**: UTF-8 support for emoojis, ANSI colors 
- **Architecture**: 3-layer (UI, Service, Models)

### Key Classes

| Package     | Key Classes                        | Purpose         |
|-------------|------------------------------------|-----------------|
| **Models**  | Media, Movie, Series, Episode      | Data structures |
| **Service** | MediaDatabase, InputChecks         | Business logic  |
| **UI**      | UIHelper, BrowseMedia, SearchMedia | User interface  |

---

## 💾 Data Management

### Validation Rules
- Release years: 1888 - current year
- Ratings: 0.0 - 5.0 stars
- Movie duration: max 10,000 minutes
- Episode duration: max 500 minutes

### Series Features
- Auto-create Season 1 when adding first episode
- Auto-delete empty seasons
- Episode/season renumbering after deletions
- Series rating calculated from all episodes

---

## 📈 Statistics Dashboard

View comprehensive analytics:
- Total media count by type
- Average ratings (overall and by type)
- Watched vs unwatched percentage with a Visual progress bar to illustrate it
- Top 5 rated media

---

## 🎯 Design Principles

✅ **Separation of Concerns** - Each layer has distinct responsibilities  
✅ **Single Responsibility** - Each class has one clear purpose  
✅ **Inheritance & Polymorphism** - Media hierarchy with shared behavior  
✅ **Encapsulation** - Private fields with public interfaces  
✅ **Input Validation** - Robust error handling throughout

---

## 🚀 Getting Started

1. Compile all `.java` files
2. Run `Main.java`
3. Welcome screen appears
4. Navigate using the main menu
5. Pre-loaded data is ready to explore

---

## 👨‍💻 Project Info

**Created by**: Lagraa Yasser Assil  
**Type**: Educational Project  
**Focus**: OOP, Java Collections, Console UI Design  

---

## 📝 Code Conventions

- **Classes**: PascalCase (`MediaDatabase`)
- **Methods**: camelCase (`addMedia()`)
- **Constants**: UPPER_SNAKE_CASE (`CONSOLE_WIDTH`)

---


**📖 For detailed documentation, see [DOCUMENTATION.md](DOCUMENTATION.md)**

---

