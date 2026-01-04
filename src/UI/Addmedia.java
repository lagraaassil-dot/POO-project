package UI;
import java.util.*;

import Models.Media;
import Models.Movie;
import Models.Documentary;
import Service.*;

public class Addmedia {
    private static final Scanner scanner = new Scanner(System.in);
public static void addmedia(MediaDatabase database, List<Media> mediaList) throws InterruptedException {
UIHelper.clearScreen();    
boolean adding=true;
while(adding){
UIHelper.clearScreen();    
UIHelper.printInfo("Choose the type of media to add:");
UIHelper.printSection("1", "🎬 Movie", UIHelper.BLUE);
UIHelper.printSection("2", "📺 Series", UIHelper.BLUE);
UIHelper.printSection("3", "🎥📚 Documentary", UIHelper.BLUE);
UIHelper.printSection("Q", "🔙 Back to Main Menu", UIHelper.BLUE);
String choice = scanner.nextLine().trim().toLowerCase();
if(choice.equalsIgnoreCase("Q")){
    adding=false;
    UIHelper.clearScreen();
    continue;
}
else if(choice.equals("1")){
    addMovie(database, mediaList);
    adding=false;
    continue;
}
else if(choice.equals("2")){
    AddSeries.addSeries(database, mediaList);
}
else if(choice.equals("3")){
   addDocumentary(database, mediaList);
}
else{
    UIHelper.printFailure("Invalid choice. Please try again.");
    Thread.sleep(1500);
    UIHelper.clearScreen();
}
}
}
private static void addMovie(MediaDatabase database, List<Media> mediaList) throws InterruptedException {
    boolean typing=true;
    String title="";
    String director="";
    int releaseYear=0;
    int duration=0;
    Movie_Series_Genres genre=Movie_Series_Genres.UNKNOWN;
    boolean theatricalRelease=false;
    //first check: movie+director name
    while(typing){
    UIHelper.clearScreen();    
    UIHelper.printInfo(
        "To add a new movie please enter its title and director name separated by a " +
        UIHelper.RESET + UIHelper.BOLD + UIHelper.UNDERLINE + UIHelper.YELLOW + "comma(',')"
    );

    System.out.println(UIHelper.GREEN + "Correct input: movie title,director name " + UIHelper.RESET);
    System.out.println(UIHelper.RED + "Wrong input: director name ,movie title" + UIHelper.RESET);

    String input = scanner.nextLine().trim();

    //  empty input
    if (input.isEmpty()) {
        UIHelper.printFailure("Input cannot be empty.");
        Thread.sleep(1500);
        continue;
    }

    String[] parts = input.split(",");

    //  must be exactly 2 parts
    if (parts.length != 2) {
        UIHelper.printFailure("Invalid input format. Please use: title,director");
        Thread.sleep(1500);
        continue;
    }

     title = parts[0].trim();
     director = parts[1].trim();

    //  empty title or director
    if (title.isEmpty() || director.isEmpty()) {
        UIHelper.printFailure("Title and director name cannot be empty.");
        Thread.sleep(1500);
        continue;
    }

    //  movie already exists (basic check by title)
    boolean flag=true;
    for (Media media : mediaList) {
        
        if (media instanceof Movie &&
            media.getTitle().equalsIgnoreCase(title)) {
            UIHelper.printFailure("A movie with this title already exists.");
        Thread.sleep(1500);
        flag=false;
        break;
        }
    }
    if(!flag)continue;
    //end of first check(movie+title)
    typing=false;
}
//second checks

while(true){
    UIHelper.clearScreen();    
    UIHelper.printInfo("Enter additional details for the movie:");
    System.out.print("1)Release Year: ");
       String yearInput = scanner.nextLine().trim();
   
   if (!InputChecks.isValidNumber(yearInput)) {
       UIHelper.printFailure("Please enter a valid year number.");
       Thread.sleep(1500);
       continue;
   }

     releaseYear = Integer.parseInt(yearInput);
    if (releaseYear < 1888 || releaseYear > Calendar.getInstance().get(Calendar.YEAR)) {
        UIHelper.printFailure("Please enter a valid release year between 1888 and " + Calendar.getInstance().get(Calendar.YEAR) + ".");
        Thread.sleep(1500);
        continue;
}
    break;
}
while(true){
    UIHelper.clearScreen();
    UIHelper.printInfo("Enter additional details for the movie:");
    System.out.print("2)Duration (in minutes): ");
   String DurationInput = scanner.nextLine().trim();
   
   if (!InputChecks.isValidNumber(DurationInput)) {
       UIHelper.printFailure("Please enter a valid duration number.");
       Thread.sleep(1500);
       continue;
   }

    duration = Integer.parseInt(DurationInput);
    if (duration <= 0) {
        UIHelper.printFailure("Duration must be a strictly positive integer.");
        Thread.sleep(1500);
        continue;
}
    if (duration > 10000) {
        UIHelper.printFailure("Movie is too long.");
        Thread.sleep(1500);
        continue;
}
break;
}
//check if its released in theaters
while(true){
    UIHelper.clearScreen();
    UIHelper.printInfo("Enter additional details for the movie:");
    System.out.println("Was your movie (" + title + ") released in a theatre (Y/N)?");
    String input = scanner.nextLine().trim().toLowerCase();
    if(input.equals("y")){
        theatricalRelease=true;
        break;
    }
    else if(input.equals("n")){
        //it was already set to false
        break;
    }
    UIHelper.printFailure("Invalid input, please enter Y for yes or N for no.");
    Thread.sleep(1500);
}
//genre selection
while (true) {
    UIHelper.clearScreen();
    UIHelper.printInfo("Enter additional details for the movie:");
    displayGenreMenu(true);
    System.out.print("\nEnter genre number: ");
    String input = scanner.nextLine().trim();
   
   if (!InputChecks.isValidNumber(input)) {
       UIHelper.printFailure("Please enter a valid genre number.");
       Thread.sleep(1500);
       continue;
   }
    int choice = Integer.parseInt(input);

        switch (choice) {
            case 1 -> genre = Movie_Series_Genres.ACTION;
            case 2 -> genre = Movie_Series_Genres.COMEDY;
            case 3 -> genre = Movie_Series_Genres.DRAMA;
            case 4 -> genre = Movie_Series_Genres.HORROR;
            case 5 -> genre = Movie_Series_Genres.ROMANCE;
            case 6 -> genre = Movie_Series_Genres.SCIENCEFICTION;
            case 7 -> genre = Movie_Series_Genres.THRILLER;
            case 8 -> genre = Movie_Series_Genres.ANIMATION;
            case 9 -> genre = Movie_Series_Genres.ADVENTURE;
            case 10 -> genre = Movie_Series_Genres.CRIME;
            default -> {
                UIHelper.printFailure("Invalid genre number.");
                Thread.sleep(1500);
                continue;
            }
        }
        break;
    }
//create movie object and add it to database
Movie newMovie = new Movie(title, director, releaseYear, genre, duration, theatricalRelease);
database.addMedia(newMovie);
UIHelper.printSuccess("Movie '" + title + "' added successfully!");
Thread.sleep(1500);
}

//add documentary
private static void addDocumentary(MediaDatabase database, List<Media> mediaList) throws InterruptedException {
    boolean typing=true;
    String title="";
    String director="";
    int releaseYear=0;
    int duration=0;
    Documentary_Genres genre=Documentary_Genres.UNKNOWN;
    String topic="";
    boolean isEducational=false;
    //first check: title+director name
    while(typing){
    UIHelper.clearScreen();    
    UIHelper.printInfo(
        "To add a new documentary please enter its title and director name separated by a " +
        UIHelper.RESET + UIHelper.BOLD + UIHelper.UNDERLINE + UIHelper.YELLOW + "comma(',')"
    );

    System.out.println(UIHelper.GREEN + "Correct input: documentary title,director name " + UIHelper.RESET);
    System.out.println(UIHelper.RED + "Wrong input: director name ,documentary title" + UIHelper.RESET);

    String input = scanner.nextLine().trim();

    //  empty input
    if (input.isEmpty()) {
        UIHelper.printFailure("Input cannot be empty.");
        Thread.sleep(1500);
        continue;
    }

    String[] parts = input.split(",");

    //  must be exactly 2 parts
    if (parts.length != 2) {
        UIHelper.printFailure("Invalid input format. Please use: title,director");
        Thread.sleep(1500);
        continue;
    }

     title = parts[0].trim();
     director = parts[1].trim();

    //  empty title or director
    if (title.isEmpty() || director.isEmpty()) {
        UIHelper.printFailure("Title and director name cannot be empty.");
        Thread.sleep(1500);
        continue;
    }

    //  documentary already exists (basic check by title)
    boolean flag=true;
    for (Media media : mediaList) {
        
        if (media instanceof Documentary &&
            media.getTitle().equalsIgnoreCase(title)) {
            UIHelper.printFailure("A documentary with this title already exists.");
        Thread.sleep(1500);
        flag=false;
        break;
        }
    }
    if(!flag)continue;
    //end of first check(director+title)
    typing=false;
}
//second checks
while(true){
    UIHelper.clearScreen();    
    UIHelper.printInfo("Enter additional details for the documentary:");
    System.out.print("1)topic: ");
     topic = scanner.nextLine().trim();
    if (topic.isEmpty()) {
        UIHelper.printFailure("Topic cannot be empty.");
        Thread.sleep(1500);
        continue;
}
    break;
}


while(true){
    UIHelper.clearScreen();    
    UIHelper.printInfo("Enter additional details for the docuemtary:");
    System.out.print("2)Release Year: ");

    String yearInput = scanner.nextLine().trim();
   
   if (!InputChecks.isValidNumber(yearInput)) {
       UIHelper.printFailure("Please enter a valid year number.");
       Thread.sleep(1500);
       continue;
   }

     releaseYear = Integer.parseInt(yearInput);
    if (releaseYear < 1888 || releaseYear > Calendar.getInstance().get(Calendar.YEAR)) {
        UIHelper.printFailure("Please enter a valid release year between 1888 and " + Calendar.getInstance().get(Calendar.YEAR) + ".");
        Thread.sleep(1500);
        continue;
}
    break;
}
while(true){
    UIHelper.clearScreen();
    UIHelper.printInfo("Enter additional details for the documentary:");
    System.out.print("3)Duration (in minutes): ");

    String DurationInput = scanner.nextLine().trim();
   
   if (!InputChecks.isValidNumber(DurationInput)) {
       UIHelper.printFailure("Please enter a valid duration number.");
       Thread.sleep(1500);
       continue;
   }

    duration = Integer.parseInt(DurationInput);
    if (duration <= 0) {
        UIHelper.printFailure("Duration must be a strictly positive integer.");
        Thread.sleep(1500);
        continue;
}
    if (duration > 10000) {
        UIHelper.printFailure("documentary is too long.");
        Thread.sleep(1500);
        continue;
}
break;
}
//check if its educational
while(true){
    UIHelper.clearScreen();
    UIHelper.printInfo("Enter additional details for the movie:");
    System.out.println("4)Is your documentary (" + title + ") Educational (Y/N)?");
    String input = scanner.nextLine().trim().toLowerCase();
    if(input.equals("y")){
        isEducational=true;
        break;
    }
    else if(input.equals("n")){
        //it was already set to false
        break;
    }
    UIHelper.printFailure("Invalid input, please enter Y for yes or N for no.");
    Thread.sleep(1500);
}
//genre selection
while (true) {
    UIHelper.clearScreen();
    UIHelper.printInfo("Enter additional details for the documentary:");
    displayGenreMenu(false);//to display documentary genres
    System.out.print("\nEnter genre number: ");

    String input = scanner.nextLine().trim();

   
   if (!InputChecks.isValidNumber(input)) {
       UIHelper.printFailure("Please enter a valid genre number.");
       Thread.sleep(1500);
       continue;
   }

    int choice = Integer.parseInt(input);

        switch (choice) {
            case 1 -> genre = Documentary_Genres.NATURE;
            case 2 -> genre = Documentary_Genres.SCIENCE;
            case 3 -> genre = Documentary_Genres.TRUE_CRIME;
            case 4 -> genre = Documentary_Genres.HISTORICAL;
            case 5 -> genre = Documentary_Genres.BIOGRAPHICAL;
            case 6 -> genre = Documentary_Genres.SPORTS;
            case 7 -> genre = Documentary_Genres.HEALTH;
            case 8 -> genre = Documentary_Genres.TECHNOLOGY;
            default -> {
                UIHelper.printFailure("Invalid genre number.");
                Thread.sleep(1500);
                continue;
            }
        }
        break;
    }
//create movie object and add it to database
Documentary newdocumentary = new Documentary(title, director, releaseYear, genre, topic, duration, isEducational);
database.addMedia(newdocumentary);
UIHelper.printSuccess("Documentary '" + title + "' added successfully!");
Thread.sleep(1500);
}



//type: true for movies/series, false for documentaries
protected static void displayGenreMenu(boolean isMovieOrSeries) {
    System.out.println(UIHelper.BOLD + UIHelper.MAGENTA + "\nSelect a Genre:" + UIHelper.RESET);

    if (isMovieOrSeries) {
        // 🎬 Movie / Series genres
        System.out.println(UIHelper.CYAN + "Movie / Series Genres:" + UIHelper.RESET);

        printGenreRow(
            new GenreOption(1, Movie_Series_Genres.ACTION),
            new GenreOption(2, Movie_Series_Genres.COMEDY),
            new GenreOption(3, Movie_Series_Genres.DRAMA),
            new GenreOption(4, Movie_Series_Genres.HORROR)
        );

        printGenreRow(
            new GenreOption(5, Movie_Series_Genres.ROMANCE),
            new GenreOption(6, Movie_Series_Genres.SCIENCEFICTION),
            new GenreOption(7, Movie_Series_Genres.THRILLER),
            new GenreOption(8, Movie_Series_Genres.ANIMATION)
        );

        printGenreRow(
            new GenreOption(9, Movie_Series_Genres.ADVENTURE),
            new GenreOption(10, Movie_Series_Genres.CRIME),
            null,
            null
        );

    } else {
        // 🎥 Documentary genres
        System.out.println(UIHelper.CYAN + "Documentary Genres:" + UIHelper.RESET);

        printGenreRow(
            new GenreOption(1, Documentary_Genres.NATURE),
            new GenreOption(2, Documentary_Genres.SCIENCE),
            new GenreOption(3, Documentary_Genres.TRUE_CRIME),
            new GenreOption(4, Documentary_Genres.HISTORICAL)
        );

        printGenreRow(
            new GenreOption(5, Documentary_Genres.BIOGRAPHICAL),
            new GenreOption(6, Documentary_Genres.SPORTS),
            new GenreOption(7, Documentary_Genres.HEALTH),
            new GenreOption(8, Documentary_Genres.TECHNOLOGY)
        );
    }
}



private static class GenreOption {
    int number;
    Genre genre;

    GenreOption(int number, Genre genre) {
        this.number = number;
        this.genre = genre;
    }
}

    
private static void printGenreRow(GenreOption g1, GenreOption g2, GenreOption g3, GenreOption g4) {
    GenreOption[] genres = { g1, g2, g3, g4 };

    for (GenreOption g : genres) {
        if (g != null) {
            String name = g.genre.toString().replace("_", " ");
            System.out.printf("%2d) %-18s", g.number, name);
        } else {
            System.out.print(" ".repeat(22));
        }
    }
    System.out.println();
}

}