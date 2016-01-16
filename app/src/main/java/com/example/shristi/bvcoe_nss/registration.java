package com.example.shristi.bvcoe_nss;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class registration extends AppCompatActivity {

    public static final MediaType FORM_DATA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/1FQuhnFs6J4udBMSO5xUw7vfjT97wRZlD0Ud2EnFIG9U/formResponse";
    //input element ids found from the live form page
    public static final String VOL_KEY="entry_542306955";
    public static final String NAME_KEY="entry_1829790587";
    public static final String DOB_KEY="entry_1932969273";
    public static final String SEM_KEY= "entry_838188908";
    public static final String BRANCH_KEY="entry_1924376059";
    public static final String ROLL_KEY="entry_950672578";
    public static final String NUMBER_KEY="entry_237759759";
    public static final String EMAIL_KEY="entry_969468342";
    public static final String ADDRESS_KEY="entry_646602927";
    public static final String BLOOD_KEY="entry_773235606";
    public static final String SKILLS_KEY="entry_882746069";

    private Context context ;
    private EditText NameText;
    private EditText DOBText;
    private Spinner SemText;
    private Spinner BranchText;
    private EditText RollText;
    private EditText PhoneText;
    private EditText EmailText;
    private EditText AddressText;
    private EditText BloodText;
    private EditText SkillText;
    private EditText vollText;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //save the activity in a context variable to be used afterwards
        context =this;

        //Get references to UI elements in the layout
        Button submitButton = (Button)findViewById(R.id.submitButton);

        NameText = (EditText)findViewById(R.id.NameText);
        DOBText= (EditText)findViewById(R.id.DOBText);
        RollText=(EditText)findViewById(R.id.RollText);
        SkillText=(EditText)findViewById(R.id.SkillText);
        BloodText=(EditText)findViewById(R.id.BloodText);
        AddressText=(EditText)findViewById(R.id.AddressText);
        PhoneText=(EditText)findViewById(R.id.PhoneText);
        EmailText = (EditText)findViewById(R.id.EmailText);
        SemText= (Spinner)findViewById(R.id.SemText);
        BranchText = (Spinner)findViewById(R.id.BranchText);
        vollText=(EditText)findViewById(R.id.vollText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Make sure all the fields are filled with values
                if(TextUtils.isEmpty(NameText.getText().toString()) ||
                        TextUtils.isEmpty(DOBText.getText().toString()) ||
                        TextUtils.isEmpty(RollText.getText().toString()) ||
                        TextUtils.isEmpty(AddressText.getText().toString()) ||
                        TextUtils.isEmpty(BloodText.getText().toString()) ||
                        TextUtils.isEmpty(SkillText.getText().toString()) ||
                        TextUtils.isEmpty(EmailText.getText().toString()) ||
                        TextUtils.isEmpty(PhoneText.getText().toString())||
                        TextUtils.isEmpty(vollText.getText().toString()) )
                {
                    Toast.makeText(context,"All fields are mandatory.",Toast.LENGTH_LONG).show();
                    return;
                }
                //Check if a valid email is entered
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(EmailText.getText().toString()).matches())
                {
                    Toast.makeText(context,"Please enter a valid email.",Toast.LENGTH_LONG).show();
                    return;
                }

                //Create an object for PostDataTask AsyncTask
                PostDataTask postDataTask = new PostDataTask();

                //execute asynctask
                postDataTask.execute(URL,vollText.getText().toString(),
                        NameText.getText().toString(),
                        DOBText.getText().toString(),
                        SemText.getSelectedItem().toString(),
                        BranchText.getSelectedItem().toString(),
                        RollText.getText().toString(),
                        PhoneText.getText().toString(),
                        EmailText.getText().toString(),
                        AddressText.getText().toString(),
                        BloodText.getText().toString(),
                        SkillText.getText().toString()
                );
            }
        });
    }

    //AsyncTask to send data as a http POST request
    private class PostDataTask extends AsyncTask<String,String, Boolean> {

        @Override
        protected Boolean doInBackground(String... contractData) {
            Boolean result = true;
            String url = contractData[0];
            String vol=contractData[1];
            String name = contractData[2];
            String dob = contractData[3];
            String email = contractData[8];
            String sem=contractData[4];
            String branch=contractData[5];
            String roll=contractData[6];
            String address=contractData[9];
            String blood =contractData[10];
            String phone=contractData[7];
            String skill=contractData[11];
            String postBody="";

            try {
                //all values must be URL encoded to make sure that special characters like & | ",etc.
                //do not cause problems
                postBody = VOL_KEY+"="+URLEncoder.encode(vol,"UTF-8") +"&" +
                        NAME_KEY +"=" + URLEncoder.encode(name,"UTF-8") +"&" +
                        DOB_KEY +"=" + URLEncoder.encode(dob,"UTF-8") +"&" +
                        SEM_KEY+"="+URLEncoder.encode(sem,"UTF-8")+"&"+
                        BRANCH_KEY + "=" + URLEncoder.encode(branch,"UTF-8")+"&"+
                        ROLL_KEY  +"=" + URLEncoder.encode(roll,"UTF-8") +"&" +
                        NUMBER_KEY + "=" + URLEncoder.encode(phone,"UTF-8")+"&"+
                        EMAIL_KEY + "=" + URLEncoder.encode(email,"UTF-8")+"&"+
                        ADDRESS_KEY+"=" + URLEncoder.encode(address,"UTF-8") +"&" +
                        BLOOD_KEY+"=" + URLEncoder.encode(blood,"UTF-8") +"&" +
                        SKILLS_KEY+"=" + URLEncoder.encode(skill,"UTF-8")
                ;
            } catch (UnsupportedEncodingException ex) {
                result=false;
            }



            try{
                //Create OkHttpClient for sending request
                OkHttpClient client = new OkHttpClient();
                //Create the request body with the help of Media Type
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder().url(url).post(body).build();
                //Send the request
                Response response = client.newCall(request).execute();
                System.out.println(response.body().string());
            }catch (IOException exception){
                result=false;
            }
            //return result;
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result){
            //Print Success or failure message accordingly
            Toast.makeText(context,result?"Registration done successfully!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
        }

    }

}
