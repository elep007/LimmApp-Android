package com.patrick.limm.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.patrick.limm.R;
import com.patrick.limm.model.Common;

public class ContactActivity extends AppCompatActivity {
    Context mContext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        addEventListener();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void addEventListener() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Contact Us");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_backbutton);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


    }

    private void sendMessage(){
        EditText _name=findViewById(R.id.editName);
        EditText _email=findViewById(R.id.editEmail);
        EditText _message=findViewById(R.id.editMessage);

        if(_name.getText().toString().trim().isEmpty()){
            _name.setError("Enter Name");
            return;
        }
        else{
            _name.setError(null);
        }
        if(_email.getText().toString().trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email.getText().toString()).matches()){
            _email.setError("Enter valid email");
            return;
        }
        else{
            _email.setError(null);
        }
        if(_message.getText().toString().trim().isEmpty()){
            _message.setError("Enter Message");
            return;
        }
        else{
            _message.setError(null);
        }

        final ProgressDialog progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObject json = new JsonObject();
        json.addProperty("sender", _name.getText().toString());
        json.addProperty("sender_email",_email.getText().toString());
        json.addProperty("message",_message.getText().toString());
        Ion.with(getBaseContext())
                .load(Common.getInstance().getBaseURL() + "sendmessage.php")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressDialog.dismiss();
                        String status=result.get("status").getAsString();
                        if(status.equals("ok")){
                            Toast.makeText(mContext,"Successfully sent",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(mContext,"Fail, Try again.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
