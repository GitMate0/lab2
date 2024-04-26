import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

class LibraryDriver
{
  public static void main(String[] args)
  {
    String filename = "lib1.serial";
    Library lib;
    
    Path filepath = Paths.get(filename);
    if (Files.exists(filepath))
    {
      lib = (Library) deSerializeObject(filename);

      System.out.println("Library: " + lib);

      System.out.println("Bookstores:");
      ArrayList<BookStore> bsa = lib.get_bookstores();
      for (int i = 0; i < bsa.size(); i++)
      {
        System.out.println();
        BookStore bs = bsa.get(i);
        System.out.println("Bookstore: " + bs);
        ArrayList<Book> books = bs.get_books();
        System.out.println("Books:");
        for (int j = 0; j < books.size(); j++)
        {
          System.out.println();
          Book b = books.get(j);
          System.out.println("Book: " + b);
          System.out.println("Edition year: " + b.get_edition_year());
          System.out.println("Edition number: " + b.get_edition_number());
          System.out.print("Authors: ");
          ArrayList<BookAuthor> authors = b.get_authors();
          for (int k = 0; k < authors.size(); k++)
          {
            System.out.print(authors.get(k) + ", ");
          }
          System.out.println();
        }
      }
      System.out.println("\nReaders:");
      ArrayList<BookReader> bra = lib.get_readers();
      for (int i = 0; i < bra.size(); i++)
      {
        System.out.println();
        BookReader br = bra.get(i);
        System.out.println("Reader: " + br);
        System.out.println("Registration number: " + br.get_id());
        ArrayList<Book> books = br.get_books();
        System.out.println("Books:");
        for (int j = 0; j < books.size(); j++)
        {
          System.out.println();
          Book b = books.get(j);
          System.out.println("Book: " + b);
          System.out.println("Edition year: " + b.get_edition_year());
          System.out.println("Edition number: " + b.get_edition_number());
          System.out.print("Authors: ");
          ArrayList<BookAuthor> authors = b.get_authors();
          for (int k = 0; k < authors.size(); k++)
          {
            System.out.print(authors.get(k) + ", ");
          }
          System.out.println();
        }
      }
    }
    else
    {
      lib = new Library("BigBookLib");
      for (int i = 1; i < 4; i++)
      {
        BookStore bs = new BookStore("BookStoreShelf " + i);
        for (int j = 1; j < 4; j++)
        {
          Book b = new Book("Book " + (i * 3 + j - 3), 2024, i * 3 + j - 3);
          for (int k = 1; k < 3; k++)
          {
            BookAuthor ba = new BookAuthor("Bookin", "Authorovych " + ((i - 1) * 6 + (j - 1) * 2 + k));
            b.add_author(ba);
          }
          bs.add_book(b);
        }
        lib.add_bookstore(bs);
      }

      for (int i = 1; i < 5; i++)
      {
        BookReader br = new BookReader("Bookola", "Readerenko " + i, i);
        for (int j = 1; j < (2 + (int)(Math.random() * 2)); j++)
        {
          BookStore bs = lib.get_bookstores().get((int)(Math.random() * 3));
          Book b = bs.get_books().get((int)(Math.random() * 3));
          br.add_book(b);
        }
        lib.add_reader(br);
      }
      serializeObject(filename, lib);
    }
  }

  public static void serializeObject(String filename, Object obj) {
    try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename))) {
      os.writeObject(obj);
      System.out.println("Object " + filename + " serialized successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Object deSerializeObject(String filename) {
    Object obj = null;
    try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename))) {
      obj = is.readObject();
      System.out.println("Object " + filename + " deserialized successfully");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return obj;
  }
}

abstract class Human implements Serializable
{
  private String firstname;
  private String lastname;

  public Human()
  {
    this.firstname = "";
    this.lastname = "";
  }

  public Human(String firstname, String lastname)
  {
    this.firstname = firstname;
    this.lastname = lastname;
  }
  
  public String toString() 
  {
    return firstname + " " + lastname;
  }

  public String get_firstname()
  {
    return firstname;
  }

  public void set_firstname(String new_firstname)
  {
    this.firstname = new_firstname;
  }

  public String get_lastname()
  {
    return lastname;
  }

  public void set_lastname(String new_lastname)
  {
    this.lastname = new_lastname;
  }
}

public class BookAuthor extends Human implements Serializable
{
  private static final long serialVersionUID = -6021374729204796701L;

  public BookAuthor()
  {
    super("", ""); // Викликаємо конструктор з параметрами для батьківського класу Human
  }

  public BookAuthor(String firstname, String lastname)
  {
    super(firstname, lastname);
  }
}

public class Book implements Serializable
{
  private String title;
  private ArrayList<BookAuthor> authors;
  private int edition_year;
  private int edition_number;

  public Book(String title, ArrayList<BookAuthor> authors, int edition_year, int edition_number)
  {
    this.title = title;
    this.authors = authors;
    this.edition_year = edition_year;
    this.edition_number = edition_number;
  }

  public Book(String title, int edition_year, int edition_number)
  {
    this.title = title;
    this.authors = new ArrayList<BookAuthor>();
    this.edition_year = edition_year;
    this.edition_number = edition_number;
  }

  public String toString() 
  {
    return title;
  }

  public String get_title()
  {
    return title;
  }

  public void set_title(String new_title)
  {
    this.title = new_title;
  }

  public int get_edition_year()
  {
    return edition_year;
  }

  public int get_edition_number()
  {
    return edition_number;
  }

  public void add_author(BookAuthor ba)
  {
    authors.add(ba);
  }

  public ArrayList<BookAuthor> get_authors()
  {
    return authors;
  }
}

public class BookStore implements Serializable
{
  private String name;
  private ArrayList<Book> books;

  public BookStore(String name, ArrayList<Book> books)
  {
    this.name = name;
    this.books = books;
  }

  public BookStore(String name)
  {
    this.name = name;
    this.books = new ArrayList<Book>();
  }

  public String toString() 
  {
    return name;
  }

  public void add_book(Book b)
  {
    books.add(b);
  }

  public ArrayList<Book> get_books()
  {
    return books;
  }
}

public class BookReader extends Human implements Serializable
{
  private int id;
  private ArrayList<Book> books;

  public BookReader(String firstname, String lastname, int id, ArrayList<Book> books)
  {
    super(firstname, lastname);
    this.id = id;
    this.books = books;
  }

  public BookReader(String firstname, String lastname, int id)
  {
    super(firstname, lastname);
    this.id = id;
    this.books = new ArrayList<Book>();
  }

  public void add_book(Book b)
  {
    books.add(b);
  }

  public ArrayList<Book> get_books()
  {
    return books;
  }

  public int get_id()
  {
    return id;
  }
}

public class Library implements Serializable
{
  private String name;
  private ArrayList<BookStore> bookstores;
  private ArrayList<BookReader> readers;

  public Library(String name, ArrayList<BookStore> bs, ArrayList<BookReader> r)
  {
    this.name = name;
    this.bookstores = bs;
    this.readers = r;
  }

  public Library(String name)
  {
    this.name = name;
    this.bookstores = new ArrayList<BookStore>();
    this.readers = new ArrayList<BookReader>();
  }

  public String toString() 
  {
    return name;
  }

  public void add_bookstore(BookStore bs)
  {
    bookstores.add(bs);
  }

  public void add_reader(BookReader br)
  {
    readers.add(br);
  }

  public ArrayList<BookStore> get_bookstores()
  {
    return bookstores;
  }

  public ArrayList<BookReader> get_readers()
  {
    return readers;
  }
}