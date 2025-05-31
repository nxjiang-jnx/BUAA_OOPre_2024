import java.util.ArrayList;

public class BookShelf {
    private final int id;
    private final ArrayList<Book> books;

    public BookShelf(int id) {
        this.id = id;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public BookShelf cloneBookshelf() {
        BookShelf clone = new BookShelf(id);
        for (Book book : books) {
            clone.addBook(book.clone());
        }
        return clone;
    }

    public void filter(int num) {
        ArrayList<Book> removeBooks = new ArrayList<>();

        for (Book book : books) {
            String magic = book.getMagic();

            // 找出 '#' 字符的位置
            ArrayList<Integer> positions = new ArrayList<>();
            for (int i = 0; i < magic.length(); i++) {
                if (magic.charAt(i) == '#') {
                    positions.add(i);
                }
            }

            int complianceDegree = 0;

            // 遍历 '#' 位置的配对，提取子串
            for (int i = 0; i < positions.size() - 1; i++) {
                int start = positions.get(i);
                int end = positions.get(i + 1);

                // 提取以 '#' 开头和结尾的子串
                String substring = magic.substring(start, end + 1);

                // 检查子串中是否正好有两个 '#' 字符
                if (substring.chars().filter(ch -> ch == '#').count() != 2) {
                    continue;
                }

                // 去掉 '#' 字符以便分析
                String content = substring.replace("#", "");

                // 统计数字和字母的数量
                int digitCount = 0;
                int letterCount = 0;
                for (char c : content.toCharArray()) {
                    if (Character.isDigit(c)) {
                        digitCount++;
                    } else if (Character.isLetter(c)) {
                        letterCount++;
                    }
                }

                // 检查子串是否满足条件 C
                if (digitCount >= 1 && digitCount >= letterCount) {
                    complianceDegree++;
                }
            }

            // 如果合格度小于标准，则标记书籍以移除
            if (complianceDegree < num) {
                removeBooks.add(book);
            }
        }

        // 移除所有不符合标准的书籍
        books.removeAll(removeBooks);
    }

    public void join(BookShelf bookShelf) {
        for (Book book : bookShelf.books) {
            boolean hasBook = false;
            for (Book book1 : books) {
                //若两本书的名称和魔咒均相同，则hasbook为true
                if (book1.getName().equals(book.getName()) &&
                        book1.getMagic().equals(book.getMagic())) {
                    hasBook = true;
                    break;
                }
            }
            if (!hasBook) {
                books.add(book);
            }
        }
    }

    public void addMagic(String magic) {
        for (Book book : books) {
            book.addMagic(magic);
        }
    }

    public void subMagic(int a, int b) {
        for (Book book : books) {
            String magic = book.getMagic();
            String newMagic;

            //若 a > b，则返回空串
            if (a > b) {
                newMagic = "";
            } else {
                // 处理 a 和 b 的下标范围
                int start = Math.max(0, a);
                int end = Math.min(magic.length(), b + 1);

                // 如果 a 超出范围则返回空串
                if (start >= magic.length()) {
                    newMagic = "";
                } else {
                    // 截取字符串
                    newMagic = magic.substring(start, end);
                }
            }
            //更新书籍的魔咒
            book.setMagic(newMagic);
        }
    }

    public int getNum1() {
        return books.size();
    }

    public int getNum2(String s) {
        int res = 0;
        for (Book book : books) {
            if (book.contains(s)) {
                res++;
            }
        }
        return res;
    }
}
