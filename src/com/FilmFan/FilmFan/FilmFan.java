package com.FilmFan.FilmFan;

import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import static com.codename1.ui.CN.*;
import com.codename1.io.Log;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;

public class FilmFan {

    private Form current;
    private Resources theme;
    MultiButton mb;
    String apiKey="c49a7a301dc52e896c4e87a6ab660292";
    dataFetchingClass data_fetching_class;
    ArrayList json_data;
    String title;
    String poster_path;
    String release_date;
    Container homecontainer;
    FilmdataAdater filmdataAdapter;
    URLImage image_url;
    MultiButton[] film_list;
    String poster_url = "https://image.tmdb.org/t/p/w500";
    
    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a network error, Please try again later", "OK", null);
        });        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form homePage = new Form("Film Fan", new BoxLayout(BoxLayout.Y_AXIS));
        homecontainer = new Container(BoxLayout.y());
        homecontainer.setScrollable(true);
        //instance of data fetching from url class
        data_fetching_class = new dataFetchingClass();
        //instance of a class populating data to a list 
        filmdataAdapter = new FilmdataAdater();
        
        json_data= data_fetching_class.filmArrayJsonData();

        //Calling a method displaying data to listview
        filmdataAdapter.filmListAdapter(homePage,json_data,poster_url);
        
        homePage.show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

}
