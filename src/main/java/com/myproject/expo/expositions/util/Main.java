package com.myproject.expo.expositions.util;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.regex.Pattern;

public class Main {
    private static final String EMAIL_CYRILLIC = "^(([\\p{IsCyrillic}]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    public static void main(String[] args) throws IOException {
        LocalDate today = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.SHORT).withLocale(new Locale("uk", "UA"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.US);

        String formattedDate = today.format(dateTimeFormatter);
        //  System.out.println("LONG format: " + formattedDate);

        // System.out.println(localTime.format(timeFormatter));

        String d = "5/13/22";
        String t = "11.02.22";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yy");
        //System.out.println(LocalDate.parse(d,formatter));

       // System.out.println(Pattern.compile("\\d{1,2}/\\d{2}/\\d{2}").matcher(d).matches());

        System.out.println(Pattern.compile(EMAIL_CYRILLIC).matcher("саша@gmail.com").matches());
        System.out.println("саша@gmail.com");

        FileReader file = new FileReader("test/plain.txt");
        BufferedReader reader =  new BufferedReader(file);
        String str = reader.readLine();
        System.out.println(str);
    }
}
