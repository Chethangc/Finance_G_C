package com.example.financegc;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Login extends AppCompatActivity {

    Button login_button;
    TextView username;
    TextView password;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button = findViewById(R.id.login_button);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user;
                String pass;
                user = username.getText().toString();
                pass = password.getText().toString();
                try {
                    if (verifyLoginData(user, pass)) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }

            }
        });
    }

    public boolean verifyLoginData(String username, String password) throws IOException, JSONException {
        final boolean[] result = {false};
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String aws= getString(R.string.aws_database_ip);
        RequestBody formBody = new FormBody.Builder()
                .add("user", username)
                .add("pass", password)
                .build();
        String response = post("http://"+aws+"/dbscripts/VerifyUser.php",formBody);
        System.out.println(formBody);
        JSONObject decoded_response = new JSONObject(response);
        if(decoded_response.getString("status").equals("200")){
            result[0]=true;
        }
        return result[0];
    }

    public String post(String url, RequestBody formBody) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}