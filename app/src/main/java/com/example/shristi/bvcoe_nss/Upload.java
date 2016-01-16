package com.example.shristi.bvcoe_nss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;

import java.io.File;

//uploading in gr app
public class Upload extends AppCompatActivity {
    Context context;
    String photoBrowseUrl;
    Cursor cursor;
    String imgDecodableString;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Backendless.initApp(this,"A0EC8CE8-BC06-EC54-FFE2-9B50776AAA00","AE1E553B-0836-C1E1-FFAF-96E976956000","v1");

        Button chooseBtn = (Button) findViewById(R.id.chooseBtn);

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Default.SELECT_PHOTO);
            }
        });

        Button registerButton = (Button) findViewById( R.id.registerButton );
        View.OnClickListener registerButtonClickListener = createRegisterButtonClickListener();
        registerButton.setOnClickListener(registerButtonClickListener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (resultCode == RESULT_OK) {
            final Uri selectedImage = imageReturnedIntent.getData();

            final File imageFile = new File(getRealPathFromURI(selectedImage));
            final String filePath = imageFile.getPath();
            progressDialog = ProgressDialog.show(Upload.this, "", "Loading", true);
            ImageView imgView = (ImageView) findViewById(R.id.imgView);
            // Set the Image in ImageView after decoding the String

            String[]  filePathColumn = { MediaStore.Images.ImageColumns.DATA };
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            imgView.setImageBitmap(BitmapFactory
                    .decodeFile(imgDecodableString));
            Backendless.Files.upload(imageFile, Default.DEFAULT_PATH_ROOT, new AsyncCallback<BackendlessFile>() {
                @Override
                public void handleResponse(BackendlessFile response) {
                    photoBrowseUrl = response.getFileURL();

                    Intent intent = new Intent();
                    intent.putExtra(Default.PHOTO_BROWSE_URL, photoBrowseUrl);
                    intent.putExtra(Default.FILE_PATH, filePath);
                    setResult(Default.ADD_NEW_PHOTO_RESULT, intent);
                    progressDialog.cancel();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    progressDialog.cancel();
                    Toast.makeText(Upload.this, "No Network Connection. Please check your internet connection and try again later", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
    private String getRealPathFromURI(Uri contentURI) {
        cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();

            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    /**
     * Sends a request to Backendless to register user.
     *
     * @param name        event number
     * password                   1
     *      link              image link
     *      name1           event's name
     *
     *class-   events description
     *
     */
    public void registerUser( String name,String password,String name1, String classes,String link,
                              AsyncCallback<BackendlessUser> registrationCallback )
    {
        BackendlessUser BALL = new BackendlessUser();
        BALL.setProperty("classes",classes);
        BALL.setProperty("password",password);
        BALL.setProperty("name", name);
        BALL.setProperty("name1",name1);
        BALL.setProperty("link",link);
        //Backendless handles password hashing by itself, so we don't need to send hash instead of plain text
       Backendless.UserService.register(BALL, registrationCallback);
    }

    /**
     * Creates a callback, containing actions to be executed on registration request result.
     * Sends result intent containing registered user's email to calling activity on success.
     *
     * @return a callback, containing actions to be executed on registration request result
     */
    public LoadingCallback<BackendlessUser> createRegistrationCallback()
    {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_register ) )
        {
            @Override
            public void handleResponse( BackendlessUser registeredUser )
            {
                super.handleResponse( registeredUser );
                Intent registrationResult = new Intent();
                setResult( RESULT_OK, registrationResult );
                Upload.this.finish();
            }
        };
    }

    /**
     * Creates a listener, which proceeds with registration on button click.
     *
     * @return a listener, handling registration button click
     */
    public View.OnClickListener createRegisterButtonClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v ) {


                EditText nameField = (EditText) findViewById(R.id.nameField);
                EditText eventField = (EditText) findViewById(R.id.eventField);
                EditText classField=(EditText)findViewById(R.id.classField);
               CharSequence name = nameField.getText();
                CharSequence name1 = eventField.getText();
                CharSequence password ="1";
                CharSequence link=photoBrowseUrl;
                CharSequence classes=classField.getText();
                LoadingCallback<BackendlessUser> registrationCallback = createRegistrationCallback();
                registrationCallback.showLoading();
                registerUser(name.toString(),
                       password.toString(),
                        name1.toString(),
                        classes.toString(),
                        link.toString(),
                        registrationCallback);
            }
        };
    }
}
