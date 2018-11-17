
package com.FilmFan.FilmFan;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;


public class dataFetchingClass {
   
   String urlString;
   ArrayList Json_object_arrayList;
   Map<String,Object> film_details;
   String status_code;

public dataFetchingClass(){
    urlString="https://api.themoviedb.org/3/movie/now_playing?api_key=c49a7a301dc52e896c4e87a6ab660292&language=en-US&page=1";
}
//Method to consume json data of playing movies list
public ArrayList filmArrayJsonData(){

                try { 
                    ConnectionRequest req = new ConnectionRequest();
                    req.setUrl(urlString);
                    req.setPost(false);
                    req.addArgument("tagmode", "any");
                    req.addArgument("tags", "tag");
                    req.addArgument("format", "json");
                    NetworkManager.getInstance().addToQueueAndWait(req);
                    byte[] data = req.getResponseData();
                    if (data == null) {
                        throw new IOException("Network Err");
                    }else{
                    JSONParser parser = new JSONParser();
                    Map<String,Object> response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
                    Json_object_arrayList = (ArrayList)response.get("results");
                    }
                    
                } catch (IOException ex) {
                    //Dialog.show("Connection Error", "Please check your network", "OK", null);
                    ex.printStackTrace();
                }
     return Json_object_arrayList;
} 
// method fetching selected movie details
public Map<String,Object> filmDetailsFetching(String film_id,String api_key){
    String film_url = "https://api.themoviedb.org/3/movie/"+film_id+"?api_key="+api_key+"&language=en-US";
    System.out.println("the url sent: "+film_url);
        try { 
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(film_url);
        req.setPost(false);
        req.addArgument("tagmode", "any");
        req.addArgument("tags", "tag");
        req.addArgument("format", "json");
        NetworkManager.getInstance().addToQueueAndWait(req);
        byte[] data = req.getResponseData();
        if (data == null) {
            throw new IOException("Network Err");
        }else{
        JSONParser parser = new JSONParser();
        film_details = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
        }

    } catch (IOException ex) {
        ex.printStackTrace();
    }
    return film_details;
}
//Method to be called when rating a Movie
public String FilmratingRequestMethod(String movie_id,String api_key){
    
        String film_rate_url = "https://api.themoviedb.org/3/movie/"+movie_id+"/rating?api_key="+api_key;
        try { 
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(film_rate_url);
        req.setPost(true);
        req.addArgument("tagmode", "any");
        req.addArgument("tags", "tag");
        req.addArgument("format", "json");
        NetworkManager.getInstance().addToQueueAndWait(req);
        byte[] data = req.getResponseData();
        if (data == null) {
            throw new IOException("Network Err");
        }else{
        JSONParser parser = new JSONParser();
        Map<String,Object> response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
        status_code = (String)response.get("Status_code");
        }

    } catch (IOException ex) {
        //Dialog.show("Connection Error", "Please check your network", "OK", null);
        ex.printStackTrace();
    }
    return status_code;

}
}
