import Service.*;
import UI.*;


//Before reading the Code Please read "README" first beceause it explains what all of the classes does.
//the file is very long yes but its way better than reading the whole code directly which is EVEN longer


/**
 * Main - Entry point for Media Management System
 * 
 * Handles:
 * - Application initialization
 * - Main menu loop
 * - Navigation between features
 */
public class Main {
    

    /**
     * Main entry point
     * @throws InterruptedException For sleep() calls
     */
    public static void main(String[] args) throws InterruptedException {
        // Initialize database
        MediaDatabase database = new MediaDatabase();
        
        // Display welcome header
        UIHelper.printHeader("MEDIA MANAGEMENT SYSTEM", "CONSOLE-BASED JAVA APPLICATION");
        Thread.sleep(2000);
        
        System.out.println(UIHelper.BOLD + "Press any key to continue..." + UIHelper.RESET);
        UIHelper.readKey();
        UIHelper.clearScreen();
        
        // Main application loop
        boolean running = true;
        
        while (running) {
            
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "MAIN MENU" + UIHelper.RESET);
            
            UIHelper.printInfo("Please Choose the number representing each option.");
            
            UIHelper.printSection("1", "📁 Browse Media", UIHelper.BLUE);
            UIHelper.printSection("2", "🔍 Search Media", UIHelper.BLUE);
            UIHelper.printSection("3", "➕ Add New Media", UIHelper.BLUE);
            UIHelper.printSection("4", "🗑️ Delete Media", UIHelper.BLUE);
            UIHelper.printSection("5", "📊 Statistics", UIHelper.BLUE);
            UIHelper.printSection("6", "🚪 Exit", UIHelper.BLUE);
            
            System.out.println();
            
            int menuChoice = UIHelper.readInt();
            
            switch (menuChoice) {
                case 1:
                    BrowseMedia.browseMedia(database, database.getAllMedia());
                    break;
                    
                case 2:
                    SearchMedia.searchMedia(database);
                    break;
                    
                case 3:
                    Addmedia.addmedia(database, database.getAllMedia());
                    break;
                    
                case 4:
                    DeleteMedia.deleteMedia(database, database.getAllMedia());
                    break;
                    
                case 5:
                    Statistics.displayStatistics(database);
                    break;
                    
                case 6:
                    System.out.println("Exiting...");
                    Thread.sleep(1000);
                    UIHelper.clearScreen();
                    running=false;
                    break;
                    
                default:
                    UIHelper.printFailure("Invalid choice. Please try again.");
                    Thread.sleep(1500);
                    continue;
            }
        }
        
        UIHelper.clearScreen();
        UIHelper.printSuccess("Thank you for using the Media Management System!");
        UIHelper.printFooter("Project made by Lagraa Yasser Assil");
    }
}