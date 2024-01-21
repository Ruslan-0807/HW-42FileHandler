import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandler {
        private String path;

        public static void createFiles(ArrayList<String> list) {
            for (String path: list) {
                File file = new File(path);
                try{
                    file.createNewFile();
                }catch (IOException e){
                    System.out.println("Файл создать не удалось: " + e.getMessage());
                }

            }
        }

        public FileHandler(String path) {
            this.path = path;
            createIfNotExists();
        }
        public String read() {
            try (Scanner scanner = new Scanner(new File(this.path))){
                String result = "";
                while (scanner.hasNextLine()){
                    String newLine = scanner.nextLine();
                    result += newLine + '\n';
                }
                scanner.close();
                return result;
            }catch (FileNotFoundException e){
                System.out.println("Файл не найден: " + e.getMessage());
            }
            return "";
        }

        private void createIfNotExists(){
            File file = new File(this.path);
            boolean result = create(this.path);
            if (result){
                System.out.println("Файл успешно создан.");
            }else{
                System.out.printf("Файл [%s] уже существует.%n", this.path);
            }
        }

        private boolean create(String path) {
            File file = new File(path);
            try{
                return file.createNewFile();
            } catch (IOException e){
                System.out.println(e.getMessage());
                return false;
            }
        }

        private String getLength(){
            int coef = 1000;
            File file = new File(this.path);
            long length = file.length();
            if (length < coef){
                return length + " bytes";
            }else if (length < Math.pow(coef, 2)){
                return String.format("%.2f KB", (double) length / coef);
            }else if (length < Math.pow(coef, 3)){
                return String.format("%.2f MB", (double) length / Math.pow(coef, 2));
            }else if (length < Math.pow(coef, 4)){
                return String.format("%.2f GB", (double) length / Math.pow(coef, 3));
            }else{
                return "ХВАТИТ!!";
            }
        }

        private String getLength2(){
            File file = new File(this.path);
            long length = file.length();

            HashMap<String, Double> sizePrefs = new HashMap<>();
            sizePrefs.put("bytes", 1.0);
            sizePrefs.put("KB", 1000.0);
            sizePrefs.put("MB", Math.pow(1000, 2));
            sizePrefs.put("GB", Math.pow(1000, 3));

            List<String> keyList = sizePrefs
                    .keySet()
                    .stream()
                    .sorted(Comparator.comparing(x -> sizePrefs.get(x)))
                    .collect(Collectors.toList());

            for (String pref : keyList){
                double resultSize = length / sizePrefs.get(pref);
                if (resultSize < 1000){
                    return  String.format("%.2f %s", resultSize, pref);
                }
            }
            return "ХВАТИТ!!";
        }

        public boolean rename(String newName){
            File newFile = new File(newName);
            File file = new File(this.path);
            if (!newFile.exists()){
                boolean result = file.renameTo(newFile);
                if (result){
                    this.path = newName;
                }
                return result;
            }
            return false;
        }


        // создать метод search, который открывает файл и возвращает ArrayList со строками

        public int search(String substring) {
                try (Scanner scanner = new Scanner(new File(this.path))){
                    int rowNum = 1;
                    while (scanner.hasNextLine()) {
                        String newLine = scanner.nextLine();
                        if (newLine.contains(substring)) {
                            return rowNum;
                        }
                        rowNum++;
                    }
                    scanner.close();
                    return -1;
                }catch (FileNotFoundException e){
                    System.out.println("Файл не найден: " + e.getMessage());
                }
                return -1;

            }

            //    доработать метод search таким образом, чтобы он принимал в качестве пргумента
//    искомую строку и возвращал номер строки. Если искомой строки нет,
//    то метод возвращает -1

            public void write(String string){
                try (FileWriter writer = new FileWriter(this.path, true)) {
                    writer.append("\n" + string);
                    System.out.println("Добавлена страка " + string);
                } catch (IOException e) {
                    System.out.println("Ошибка:" + e.getMessage());
                }
            }

         // создать метод writeFromTerminal
                // при вызове метода через терминал необходимо указывать строки и они должны добавляться
                // в файл. При указании строки "END" процесс должен остановиться
                public void writeFromTerminal() {
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        String row = scanner.nextLine();
                        if (row.equals("End")) {
                            break;
                        }
                        scanner.close();
                    }
                }


    //====================================1.
    public static void remove(String path) {
        File targetfile = new File(path);
        if (targetfile.delete()) {
            System.out.printf("Super", path);
        } else {
            System.out.println("Error");
        }
    }
    //============================================2.

    public void copyTo(String targetPath) {
        //
        String content = read();
        // создаем целевой файл
        FileHandler targetFileHandler = new FileHandler(targetPath);
        targetFileHandler.write(content);

    }
    //==================================================== 3.
    public void writeLines(ArrayList<String>lines)  {
        try {
            FileWriter writer = new FileWriter(this.path);
            for (String line: lines){
                writer.append(line + "/n");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //=================================================== 4.
//    }
//    public void readLines(int number,int quantityLines ){
//        try (Scanner scanner = new Scanner(this.path)) {
//
//            writer.append("\n" + string);
//            System.out.println("Добавлена страка " + string);
//        } catch (IOException e) {
//            System.out.println("Ошибка:" + e.getMessage());
//        }

    }

    //        public static void remove(String path) {
//            File targetfile = new File(path);
//            if (targetfile.delete()) {
//                System.out.printf("super", path);
//            } else {
//                System.out.println("tt");
//            }
//        }
        @Override
        public String toString() {
            File file = new File(this.path);
            return String.format(
                    "%s (%s) (path: %s)  %n",
                    file.getName(),
                    getLength2(),
                    file.getAbsolutePath()
            );
        }
    }





