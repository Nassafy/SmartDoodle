package org.esir.smartdoodle.pad;
import net.gjerull.etherpad.client.EPLiteClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Pad {
    EPLiteClient client;
    String padId;
    String url;

    public Pad(String url, String apikey, String padId) {
        client = new EPLiteClient(url, apikey);
        this.padId = padId;
        this.url = url;
        checkPad();
    }

    public Pad(String url, String apikey) {
        client = new EPLiteClient(url, apikey);
        padId = UUID.randomUUID().toString();
        this.url = url;
        client.createPad(padId);
    }

    public String getId() {
        return padId;
    }

    public String getLink() {
        return url + "/p/" + padId;
    }

    public static void main(String[] args) {
        Pad pad = new Pad("http://148.60.11.233:3000", "473205ce80eba9fefc02de7401d64d71d4fda6db8fd1e066d71da3f4cc2ce723");
        pad.addUser("Anne");
        System.out.println(pad.getLink());
    }

    public void addUser(String user) {
        String str = client.getText(padId).get("text").toString();
        final String substring = "Liste des participants:\n";
        int index = str.indexOf(substring);
        if (index == -1) {
            str = substring;
            index = 0;
        }
        index += substring.length();
        String begin = str.substring(0, index);
        String end = str.substring(index);
        str = begin + user + "\n" + end;
        client.setText(padId, str);
    }

    private void checkPad() {
        Map result = client.listAllPads();
        List padIds = (List) result.get("padIDs");
        boolean exist = padIds.contains(padId);
        if(!exist) {
            client.createPad(padId);
        }

    }

}