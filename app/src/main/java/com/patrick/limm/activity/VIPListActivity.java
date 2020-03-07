package com.patrick.limm.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

public class VIPListActivity extends AppCompatActivity {
    Context mContext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viplist);

        addEventListener();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void addEventListener() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("VIP List");
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

        findViewById(R.id.btnJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        findViewById(R.id.txtPolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://limmgroup.com/pages/privacy-policy"));
                startActivity(browserIntent);
            }
        });
    }

    private void sendMessage(){
        EditText _name=findViewById(R.id.editName);
        EditText _email=findViewById(R.id.editEmail);

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

        final ProgressDialog progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObject json = new JsonObject();
        json.addProperty("name", _name.getText().toString());
        json.addProperty("email",_email.getText().toString());

        Ion.with(getBaseContext())
                .load(Common.getInstance().getBaseURL() + "aweber/php/manage-subscriber.php")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressDialog.dismiss();
                        String status=result.get("status").getAsString();
                        if(status.equals("ok")){
                            Common.getInstance().showAlert(mContext,"Limm","You are successfully joined.");
                        }
                        else if(status.equals("exist")){
                            Common.getInstance().showAlert(mContext,"Limm","Your email is already registered.");
                        }
                        else{
                            Toast.makeText(mContext,"Fail, Try again.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
