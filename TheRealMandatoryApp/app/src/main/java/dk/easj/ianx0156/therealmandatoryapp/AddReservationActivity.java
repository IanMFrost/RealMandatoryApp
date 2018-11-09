package dk.easj.ianx0156.therealmandatoryapp;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddReservationActivity extends AppCompatActivity {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    Calendar myCalendar= Calendar.getInstance();
    // public static final int SWIPE_THRESHOLD = 100;
    // public static final int SWIPE_VELOCITY_THRESHOLD = 100;
   // private GestureDetector gestureDetector;
    private float x1,x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
    }

    public void addReservationButtonClicked(View view) {
        EditText userIdField = findViewById(R.id.addReservation_userId_edittext);
        EditText roomIdField = findViewById(R.id.addReservation_roomId_edittext);
        EditText fromTimeField = findViewById(R.id.addReservation_fromTime_edittext);
        EditText toFimeField = findViewById(R.id.addReservation_toTime_edittext);
        EditText purposeField = findViewById(R.id.addReservation_purpose_edittext);

        String userId = userIdField.getText().toString();
        String roomId = roomIdField.getText().toString();
        String fromTime = fromTimeField.getText().toString();
        String toTime = toFimeField.getText().toString();
        String purpose = purposeField.getText().toString();

        TextView messageView = findViewById(R.id.addReservation_message_textview);

        try { // create JSON document
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("roomId", roomId);
            jsonObject.put("fromTimeString", fromTime);
            jsonObject.put("toTimeString", toTime);
            jsonObject.put("purpose", purpose);
            String jsonDocument = jsonObject.toString();
            messageView.setText(jsonDocument);
            AddReservationTask task = new AddReservationTask();
            task.execute("https://anbo-roomreservation.azurewebsites.net/api/reservations", jsonDocument);
        } catch (JSONException ex) {
            messageView.setText(ex.getMessage());
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR,year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        }
    };



    private void done() {
        finish();
    }
/*
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
    */

    /*@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result =  false;
        float diffY = e1.getY() - e2.getY();
        float diffX = e1.getX() - e2.getX();

        //which was greater, movement across X or Y?

        if (Math.abs(diffX) > Math.abs(diffY)) {
            //left or right swipe
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                result = true;
            }
        }
        return result;
    }

    private void onSwipeLeft() {

    }
    private void onSwipeRight(){
        finish();
    }
    */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_UP) :
                x2 = event.getX();
                if (x2 > x1)
                {
                    finish();
                }
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    */


    private class AddReservationTask extends AsyncTask<String, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String uri = strings[0];
            String jsonString = strings[1];
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonString);
            Request request = new Request.Builder()
                    .url(uri)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            } catch (IOException ex) {
                Log.e(CommonStuff.TAG, ex.getMessage());
                return ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(CommonStuff.TAG, "Reservation added");
            done();
        }

        @Override
        protected void onCancelled(String s) {
            Log.d(CommonStuff.TAG, "Problem: Reservation add");
        }

    }
}
