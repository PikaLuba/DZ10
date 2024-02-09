package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.*;
import java.util.Collections;




public class Main {
    public static void main(String[] args) {

    //---------------- 1
    System.out.println("---------- 1");

        final String FONO_REGEX = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\-]{7,10}$";
        Pattern pattern = Pattern.compile(FONO_REGEX);

        String path1 = "./files/file.txt";
        File file = new File(path1);
        FileTest(file);
        try (FileReader reader = new FileReader(path1)) {

            Scanner scan = new Scanner(reader);
            int i = 1;
            while (scan.hasNextLine()) {
                // Pattern Matcher
                String fono = scan.nextLine();
                Matcher matcher = pattern.matcher(fono);
                if (matcher.find())
                  System.out.println(fono);
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //----------------------- 2
        System.out.println("---------- 2 ");

        List <String> files = new ArrayList <>();
        int length = 0;
        int k = 0;

        try {
            String path2 = "./files/fileJ.txt";
            File file2 = new File(path2);
            FileTest(file2);
            BufferedReader reader = new BufferedReader(new FileReader(path2));
            String line = reader.readLine();

            while (line != null) {
                if ( k != 0) {
                    files.add(line);
                    length++;
                }

                line = reader.readLine();
                k++;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        List <Person> persons = new ArrayList <>();

        for (int j = 0; j < length; j++) {
            String str = files.get(j);
            String pars[] = str.split(" ");
            int priz = Integer.parseInt(pars[1]);

            Person person1 = new Person(pars[0], priz);
            persons.add(person1);
        }

        String path2 = "./files/user.json";
        File file2 = new File(path2);
        FileTest(file2);
        String fileName = path2;

        Path path = Paths.get(fileName);

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonElement tree = gson.toJsonTree(persons);
            gson.toJson(tree, writer);
            System.out.println("user.json - created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //----------------------- 3
        System.out.println("---------- 3 ");

        String lineString = "";
        try {
            String path3 = "./files/words.txt";
            File file3 = new File(path3);
            FileTest(file3);
            BufferedReader reader = new BufferedReader(new FileReader(path3));
            String line = reader.readLine();

            while (line != null) {
                  lineString += line;
                  line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] words = lineString.split(" ");

        // сортируем массив
        Arrays.sort(words, Collections.reverseOrder());
        int count = 0;
        for (String word : words) {
            count++;
        }

        // перепис в новый без повторений и создаем новый массив Integer с кол-вом
        String[] arrString = new String[count];
        int[] arrInteger = new int[count];

        arrString[0] = words[0];
        String str = words[0];
        int ii = 1;
        int j = 1;

        for(int l = 1; l < words.length; ++l) {
            if (words[l].equals(str)) {
                        ii++;
            } else {
                        arrInteger[j-1] = ii;
                        arrString[j] = words[l];
                        str = words[l];
                        ii = 1;
                        j++;
            }
        }
        arrInteger[j-1] = ii;
        String[] arrStr = Arrays.copyOf(arrString,j);
        int[] arrInt = Arrays.copyOf(arrInteger,j);

        Map<String, Integer> hashMap = new HashMap<>();

        for (int l = 0; l <= arrStr.length-1; l++) {
            hashMap.put(arrStr[l], arrInt[l]);
        }

        for (Map.Entry entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " "
                    + entry.getValue());
        }

    }

    public  static  void FileTest(File file) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

class Person {
    private String name;
    private int age;

    public Person()
    {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

}


