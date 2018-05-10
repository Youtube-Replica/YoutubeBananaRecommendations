package Model;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Recommendation {

    //Gets channels' info subscribed by user ID(user ID = _key and maps to array of channels' IDs)
    public static String getRecommendationByID(int id) {
        ArangoDB arangoDB = new ArangoDB.Builder().build();
        String dbName = "channelsDB";
        String collectionName = "channels";

        BaseDocument myDocument = arangoDB.db(dbName).collection(collectionName).getDocument("" + id, BaseDocument.class);

        ArrayList<JSONObject> watched_videos = new ArrayList<JSONObject>();
        watched_videos=(ArrayList<JSONObject>) myDocument.getAttribute("watched_videos");

        String favGenre;
        HashMap<String,Integer> watched_video_genres=new HashMap<String,Integer> ();
            for (JSONObject video :  watched_videos) {
                String genre=(String)video.get("category");
                if(watched_video_genres.containsKey(genre)) {
                    int count=watched_video_genres.get(genre);
                    watched_video_genres.remove(genre);
                    watched_video_genres.put(genre,++count);
                }
                else{
                    watched_video_genres.put(genre,1);
                }
            }
            favGenre=Collections.max(watched_video_genres.entrySet(), Map.Entry.comparingByValue()).getKey();

//        BaseDocument myDocument2 = arangoDB.db(dbName).collection("videos").getDocument("" + id, BaseDocument.class);
                return favGenre;


    }


}
