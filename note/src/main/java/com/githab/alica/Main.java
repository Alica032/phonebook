package com.githab.alica;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private final PhoneBook book;
    private static final String email ="([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}";
    private static final String phone = "((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}";
    private static final String name = "([a-zA-Z]){1}([a-z]){1,15}";
    private Matcher matcher;
    private static final Pattern PatternAddRemove = Pattern.compile("^(add||remove) " + name + " "+ phone + " " + email + "$");
    private static final Pattern PatternSearch = Pattern.compile("^search" + " " + name + "$");

    public Main(PhoneBook book) {
        this.book = book;
    }

    private void parser(String s){
        if (s.equals("display")){
            book.display();
            return;
        }
        String[] splited;
        Matcher matcher = PatternSearch.matcher(s);
        if (matcher.matches()) {
            splited = s.split(" ");
            book.search(splited[1]);
            return;
        }

        matcher = PatternAddRemove.matcher(s);
        if (matcher.matches()) {
            splited = s.split(" ");
            if(splited[0].equals("add")) book.add(new Person(splited[1], splited[2], splited[3]));
            else book.remove(new Person(splited[1], splited[2], splited[3]));
            return;
        }

        System.out.println("Entered incorrect data  or command");
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while(!command.equals("exit")){
            try {
                parser(command);
            } catch(PhoneBookException ex) {
                System.out.println(ex.getMessage());}
            command = scanner.nextLine();
        }
    }

    public static void main(String[] args) throws IOException {
        File PhoneFile = new File("src/main/java/file.txt");
        PhoneBook book = PhoneBook.readFromCSV(PhoneFile);
        new Main(book).run();
        book.save(PhoneFile);
    }
}
