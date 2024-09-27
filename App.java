import src.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("----------------Menu---------------");
            System.out.println("1: Create a new user: ");
            System.out.println("2: Read all users: ");
            System.out.println("3: Delete a user: ");
            System.out.println("0: Exit");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    showListUser();
                    break;
                case 3:
                    deleteUser();
                    break;
            }
        } while (choice != 0);
    }

    public static void createUser() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------- Create User --------------------");
        System.out.println("Enter the name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the email: ");
        String email = scanner.nextLine();

        User user = new User(name, email);

        // luu vao file
        File myFile = new File("data.txt");

        FileWriter writer = new FileWriter(myFile, true);

        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        String data = user.getName() + "," + user.getEmail();
        // su dung append them du lieu vao file tiep theo du lieu cu
        bufferedWriter.append(data);
        bufferedWriter.newLine();
        bufferedWriter.close();

    }

    public static void showListUser() throws FileNotFoundException {
        // doc file
        try {
            System.out.println("-------------List User-------------------");
            List<User> listUsers = readAllUserToFile();
            System.out.println("Total user: " + listUsers.size());
            for (User user : listUsers) {
                System.out.println(user);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<User> readAllUserToFile() throws IOException {
        File myFile = new File("data.txt");
        FileReader fileReader = new FileReader(myFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        List<User> listUsers = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            String name = data[0];
            String email = data[1];

            User user = new User(name, email);
            listUsers.add(user);
        }
        return listUsers;
    }

    public static void deleteUser() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter index user delete: ");
        int index = Integer.parseInt(scanner.nextLine());

        // b1 real all user
        List<User> listUsers = readAllUserToFile();
        // b2: xoa user trong list
        listUsers.remove(index);

        // luu vao file
        File myFile = new File("data.txt");

        FileWriter writer = new FileWriter(myFile, false);

        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        for (User user : listUsers) {
            String data = user.getName() + "," + user.getEmail();
            // su dung write them du lieu vao file
            bufferedWriter.write(data);
            bufferedWriter.newLine();

        }

        bufferedWriter.close();
    }
}
