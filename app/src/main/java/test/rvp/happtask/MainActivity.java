package test.rvp.happtask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView coverRecycler;
    private CoverAdapter adapter;
    private ArrayList<CoverModel> arrayList;
    public HashMap<Integer,ArrayList<String>> hashMap;
    private ArrayList<String> videoList;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         initView();
        new FetchFromApiTask().execute();

    }

    private void initView() {
        coverRecycler = findViewById(R.id.recycler_cover);
        arrayList = new ArrayList<>();
        hashMap = new HashMap<>();


    }

    private class FetchFromApiTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            NetworkUtils networkUtils = new NetworkUtils();
            String jsonStr = networkUtils.getResponseFromHttpUrl("https://happeningapi.com/api/v1/android_challenge");
         //   Log.e("Response", "1Response from url: \n" + jsonStr);
            decodeJSON(jsonStr);
            return null;
        }

        private void decodeJSON(String jsonString) {

            if(jsonString!=null){
                try {
                    JSONObject object = new JSONObject(jsonString);
                    JSONArray jsonArray = object.getJSONArray("venues");
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject ob = jsonArray.getJSONObject(i);
                        String coverTitle = ob.getString("title");
                        Log.e("TAG", "            title is    <>"+coverTitle);

                        JSONArray barArray = ob.getJSONArray("bar_story");
                        JSONObject thumb= barArray.getJSONObject(0);
                        String coverUrl = thumb.getString("thumbnail_url");
                        Log.e("TAG", "            coverUrl is    <>"+coverUrl);

                        CoverModel coverModel = new CoverModel(coverUrl,coverTitle);
                        arrayList.add(coverModel);
                        videoList = new ArrayList<>();
                        for(int j=0;j<barArray.length();j++){

                            JSONObject vide = barArray.getJSONObject(j);
                            String url = vide.getString("video_url");
                            videoList.add(url);
                            Log.e("TAG","video url is "+url);
                        }
                        Log.e("tag","video sixe of i"+videoList.size()+"  i is"+i);
                        hashMap.put(i,videoList);
                    }


                }
                catch (JSONException e){
                    Log.e("exce", e.getMessage());
                }
            }
        }

        @Override
        protected void onPostExecute(Void param) {
            adapter = new CoverAdapter(MainActivity.this,arrayList, new CoverAdapter.ListItemClickListener() {
                @Override
                public void onListItemClick(int clickedItemIndex, int whichClick) {

                    Intent intent = new Intent(MainActivity.this,StoryActivity.class);
                    intent.putStringArrayListExtra("video list",hashMap.get(clickedItemIndex));
                    Log.e("tag","size "+hashMap.get(clickedItemIndex).size());
                    startActivity(intent);

                }
            });
            coverRecycler.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            coverRecycler.setLayoutManager(layoutManager);
            coverRecycler.setAdapter(adapter);

        }

    }



}
