package com.rab.service;


import com.rab.model.PacketToTransfer;
import com.rab.util.Bible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RestMessage {

    static int x = 0;

    public RestMessage(){

    }

    @Autowired
    public Bible bible;

    @GetMapping("/verse")
    public PacketToTransfer getVerse(){
        return getBibleVerse();
    }

    private PacketToTransfer getBibleVerse() {
        int randomNum = getRandomNum();
        final String verse = bible.getVerses().get(randomNum);
        PacketToTransfer pot = new PacketToTransfer();
        pot.setTitle("Bible Verse "+randomNum);
        pot.setVerse(verse);
        return pot;
    }

    private int getRandomNum() {
        return ThreadLocalRandom.current().nextInt(1, 31101 + 1);
    }

    @PostMapping(path = "/sendMessage")
    public PacketToTransfer sendMessage(@RequestBody PacketToTransfer packet){
        x++;
        // concatenate a counter just to differentiate
        packet.setTitle(packet.getTitle()+x);

        return packet;
    }

}
