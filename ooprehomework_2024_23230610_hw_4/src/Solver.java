import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Solver {
    private final Scanner scanner;
    private final ArrayList<Book> books = new ArrayList<>();
    private final HashMap<Integer, BookShelf> bookShelves = new HashMap<>();

    public Solver(Scanner scanner) {
        this.scanner = scanner;
    }

    public void solve() {
        int t = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符，避免后续 nextLine() 出现问题

        while (t-- != 0) {
            int op = scanner.nextInt();
            if (op == 1) {
                addBook();
            } else if (op == 2) {
                addBook2Shelf();
            } else if (op == 3) {
                cloneBookShelf();
            } else if (op == 4) {
                filter();
            } else if (op == 5) {
                join();
            } else if (op == 6) {
                addMagic();
            } else if (op == 7) {
                subMagic();
            } else if (op == 8) {
                checkNum1();
            } else if (op == 9) {
                checkNum2();
            } else if (op == 10) {
                checkNum3();
            }
        }
    }

    private void addBook() {
        String name = scanner.next();
        String magic = scanner.next();
        Book book = new Book(name, magic);
        books.add(book);
        scanner.nextLine(); //防止后续换行符干扰
    }

    private Book getBook(String name) {
        for (Book book : books) {
            if (book.getName().equals(name)) {
                return book;
            }
        }
        return null;
    }

    private void addBook2Shelf() {
        int id = scanner.nextInt();
        int num = scanner.nextInt();
        bookShelves.put(id, new BookShelf(id));
        for (int i = 0; i < num; i++) {
            String name = scanner.next();
            Book book = getBook(name);
            if (book != null) {
                books.remove(book);
                bookShelves.get(id).addBook(book);
            }
        }
        scanner.nextLine();
    }

    private void cloneBookShelf() {
        int id1 = scanner.nextInt();
        int id2 = scanner.nextInt();
        bookShelves.put(id2, bookShelves.get(id1).cloneBookshelf());
    }

    private void filter() {
        int id = scanner.nextInt();
        int num = scanner.nextInt();
        if (bookShelves.get(id) != null) {
            bookShelves.get(id).filter(num);
        }
    }

    private void join() {
        int id1 = scanner.nextInt();
        int id2 = scanner.nextInt();
        if (bookShelves.get(id1) != null && bookShelves.get(id2) != null) {
            bookShelves.get(id1).join(bookShelves.get(id2));
            bookShelves.remove(id2);
        }
    }

    private void addMagic() {
        int id = scanner.nextInt();
        String magic = scanner.next();
        if (bookShelves.get(id) != null) {
            bookShelves.get(id).addMagic(magic);
        }
    }

    private void subMagic() {
        int id = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        if (bookShelves.get(id) != null) {
            bookShelves.get(id).subMagic(a, b);
        }
    }

    private void checkNum1() {
        //统计编号为 {id} 的书架的书籍数量
        int id = scanner.nextInt();
        if (bookShelves.get(id) != null) {
            System.out.println(bookShelves.get(id).getNum1());
        }
    }

    private void checkNum2() {
        //统计编号为 {id} 的书架中魔咒包含子串 {str} 的书籍数量
        int id = scanner.nextInt();
        String s = scanner.next();
        if (bookShelves.get(id) != null) {
            System.out.println(bookShelves.get(id).getNum2(s));
        }
        scanner.nextLine();
    }

    private void checkNum3() {
        //统计未被编入书架的书籍数量
        System.out.println(books.size());
    }

}
