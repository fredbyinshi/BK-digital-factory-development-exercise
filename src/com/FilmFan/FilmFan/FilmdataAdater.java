
package com.FilmFan.FilmFan;

import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.util.ArrayList;
import java.util.Map;




public class FilmdataAdater {
    String title;
    String poster_path;
    String release_date;
    Double average_rate;
    URLImage image_url;
    dataFetchingClass filmdetailsAdapter;
    String apiKey="c49a7a301dc52e896c4e87a6ab660292";
    String poster_url = "https://image.tmdb.org/t/p/w500";
    public FilmdataAdater() {
    }
    //Method handling to display movies on a list
   public void filmListAdapter(Form form,ArrayList json,String url_path){
       
    Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
        FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
        EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 3), false);
        InfiniteScrollAdapter.createInfiniteScroll(form.getContentPane(), () -> { 
            MultiButton[] film_list_button = new MultiButton[json.size()];
             for(int i = 0; i < json.toArray().length; i++){
                Map<String, Object> jso_array = (Map<String, Object>)json.get(i);
                title                   = (String)jso_array.get("title"); 
                poster_path             = (String)jso_array.get("poster_path"); 
                release_date            = (String)jso_array.get("release_date");
                average_rate            = (Double)jso_array.get("vote_average");
                Double film_id          = (Double)jso_array.get("id");
                System.out.println();
                System.out.println();
                System.out.println();
                image_url               = URLImage.createToStorage(placeholder, release_date,url_path+poster_path);    
                film_list_button[i]     = new MultiButton(title);
                film_list_button[i].setTextLine2("On: "+release_date+"       "+"Rate:"+average_rate);
                film_list_button[i].setIcon(image_url);
                film_list_button[i].setUIID(Double.toString(film_id));
                this.SelectingEventMethod(film_list_button[i]);
             }
         InfiniteScrollAdapter.addMoreComponents(form.getContentPane(), film_list_button, false);
         
        }, true); 
      
   } 
   ////Method handling how to display deatils of selected movie when a button is clicked
   public void SelectingEventMethod(MultiButton button){
       
    Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 10, p.getHeight() * 10), true); 
    
    button.addActionListener(e -> {
       Form film_details = new Form("Film Fan", new FlowLayout());
       Container film_details_container= new Container();
       String list_item_id = button.getUIID();
       System.out.println("I selected the Id: "+list_item_id);
       film_details_container.setScrollable(true);
       //calling a method fetching film details
       filmdetailsAdapter = new dataFetchingClass();
       Map<String,Object> film_detail_json   = filmdetailsAdapter.filmDetailsFetching(list_item_id,apiKey);
       String title        = (String)film_detail_json.get("original_title");
       String overview     = (String)film_detail_json.get("overview");
       String release_date = (String)film_detail_json.get("release_date");
       Double vote_average = (Double)film_detail_json.get("vote_average");
       Double popularity   = (Double)film_detail_json.get("popularity");
       String poster_path  = (String)film_detail_json.get("poster_path");
            
        image_url           = URLImage.createToStorage(placeholder, release_date,poster_url+poster_path);

        String film_rating="Released: "+release_date+"      "+"Popularity: "+popularity+"       "+"Average Votes: "+vote_average;
        SpanLabel poster_label = new SpanLabel("Overview");
        poster_label.setIcon(image_url);
        poster_label.setIconPosition(BorderLayout.NORTH);

        SpanLabel overview_title = new SpanLabel(title);
        SpanLabel overview_label = new SpanLabel(overview);
        SpanLabel film_rating_label = new SpanLabel(film_rating);

        overview_title.getAllStyles().setUnderline(true);
        poster_label.getAllStyles().setMargin(0, 0, 45, 0);
        overview_title.getAllStyles().setMargin(0, 45, 45, 0);
        overview_label.getAllStyles().setMargin(0, 45, 45, 0);
        film_rating_label.getAllStyles().setMargin(0, 45, 45, 0);
        Button rating_button = new Button("Rate this Movie"); 
        this.filmRateMothod(rating_button);
        film_details.add(FlowLayout.encloseCenterMiddle(poster_label));
        film_details.add(FlowLayout.encloseCenterMiddle(overview_title));
        film_details.add(FlowLayout.encloseCenterMiddle(overview_label));
        film_details.add(FlowLayout.encloseCenterMiddle(film_rating_label));
        film_details.add(FlowLayout.encloseCenterMiddle(rating_button));
        film_details.show();
     });
   }
   //method to handle movie rating click
   public void filmRateMothod(Button rate_button){
   rate_button.addActionListener(e -> {
      
   });
   }
}

