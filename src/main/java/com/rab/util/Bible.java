package com.rab.util;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

@Component
public class Bible {

    @Getter
    @Setter
    public ArrayList<String> verses;

    public Bible(){
        loadFile();
    }

    @SneakyThrows
    public void loadFile(){
        Instant start = Instant.now();
        if(this.verses == null){
            this.verses = new ArrayList<>();
            File file = ResourceUtils.getFile("classpath:static/bible.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                try {
                    while (br.ready()) {
                        String verse = br.readLine();
                        verse = verse.replaceAll("\t"," ");
                        this.verses.add(verse);
                    }
                } finally {
                    br.close();
                }
            }
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("File Read Time "+timeElapsed);
    }
}
