import java.io.IOException;
import java.util.logging.FileHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException {
        FileHandler users = new FileHandler( "src/users.txt");
       System.out.println(users);

    }
}
