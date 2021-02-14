package com.company;

import java.util.Scanner;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main (String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Введите ссылку: ");
        String url = in.nextLine();
        in.close();
        Counter work = new Counter(url);
        work.counting();
    }

    public static class Counter {
        private final String url;
        private String str;
        private List<Map.Entry<String, Integer>> list;
        private String[] words;

        public Counter(String url) {
            this.url = url;
        }

        public  void pars(){
            try {
                URLConnection connection = new URL(url).openConnection();
                str = new Scanner(connection.getInputStream()).useDelimiter("\\Z").next();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Clear();
        }

        public void Clear(){
            str = str.replaceAll("- ", "");
            str = str.replaceAll("[-.,]", "");
            str = str.toLowerCase(Locale.ROOT);
        }

        public void counting(){
            pars();
            words = str.split("\\s+");
            Map<String, Integer> frequency = new HashMap<>();
            for (String word : words){
                boolean flag =false;
                for(String wordOfKey : frequency.keySet()){
                    if(word.equals(wordOfKey)){
                        flag =true;
                    }
                }
                if(flag){
                    frequency.put(word, frequency.get(word) + 1);
                } else {
                    frequency.put(word,1);
                }
            }
            list = frequency.entrySet().stream()
                    .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                    .collect(Collectors.toList());

            System.out.println("Количество слов в тексте: " + words.length);
            System.out.println("Частота встречаемости слов: " + list);
        }

    }

}
