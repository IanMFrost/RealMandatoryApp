package dk.easj.ianx0156.therealmandatoryapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RoomReservationActivity extends AppCompatActivity {

    private TextView messageView;
    private static final String uri = "https://anbo-roomreservation.azurewebsites.net/api/reservations/"; //"https://reqres.in/api/users/2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_reservation);
        messageView = findViewById(R.id.main_message_textview);
    }

    @Override
    protected void onStart(){
        super.onStart();

        GetReservationsTask okHttpHandler = new GetReservationsTask();
        okHttpHandler.execute(uri);
    }

    public void showMyResOnClick(View view) {
        startActivity(new Intent(RoomReservationActivity.this, MyOwnReservationActivity.class));

    }


    private class GetReservationsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                //OkHttpClient client = new OkHttpClient();
                // https://stackoverflow.com/questions/25953819/how-to-set-connection-timeout-with-okhttp
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();
                Log.d(CommonStuff.TAG, jsonString);
                return jsonString;
            } catch (Exception e) {
                Log.d("SHIT", e.getMessage());
                cancel(true);
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String jsonString) {
            Gson gson = new GsonBuilder().create();
            final Reservation[] reservations = gson.fromJson(jsonString, Reservation[].class);
            Log.d(CommonStuff.TAG, Arrays.toString(reservations));
            ArrayAdapter<Reservation> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, reservations);

            ListView listView = findViewById(R.id.mainListView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Reservation reservation = (Reservation) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent(getBaseContext(), AddReservationActivity.class);
                    intent.putExtra(SingleReservationActivity.RESERVATION, (Parcelable) reservation);
                    startActivity(intent);
                }
            });
        }

        @Override
        protected void onCancelled(String message) {
            messageView.setText(message);
        }
    }


}
