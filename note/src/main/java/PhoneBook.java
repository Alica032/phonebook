 import java.io.*;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Set;

 public class PhoneBook {
     private HashMap<String, Set<Person>> words = new HashMap<String, Set<Person>>();

     public void add(Person person) throws  PhoneBookException {
         Set<Person> set = words.get(person.getName());
         if (set == null) {
             words.put(person.getName(), set = new HashSet<Person>());
         }
         if (set.contains(person)) {
             throw new PhoneBookException("Error: this contact already exists");
         }
         set.add(person);
     }

     public void remove(Person person)throws PhoneBookException {
     if (!contains(person)) {
         throw new PhoneBookException("Error: is not the contact");
     }
     words.get(person.getName()).remove(person);
 }

     public void display() {
         for (Set<Person> set : words.values()) {
             for(Person p: set)
                 System.out.print(p.toCSV());
         }
     }

     public void search(String name) {
         if (!words.keySet().contains(name)) {
             System.out.println("Is not the contact");
             return;
         }
         for(Person p: words.get(name))
             System.out.print(p.toCSV());
     }

     private boolean contains(Person person) {
         if (!words.containsKey(person.getName())) return false;
         return words.get(person.getName()).contains(person);
     }

     public void save() throws FileNotFoundException, UnsupportedEncodingException {
         PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("src/main/java/file.txt")), "utf-8"));
         try {
             for (Set<Person> set : words.values()) {
                 for(Person p: set)
                     out.print(p.toCSV());
             }
         } finally {
             out.close();
         }
     }

     public static PhoneBook readFromCSV(File PhoneFile) throws IOException {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(PhoneFile),"utf-8"));
         String line;
         PhoneBook book = new PhoneBook();
         try {
             while ((line = in.readLine()) != null) book.add(Person.readFromCSV(line));
         } finally {
             in.close();
         }
         return book;
     }
 }