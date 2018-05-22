package suraj.dev.com.uploadfiles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3,imageView4;
    Button uploadImage,Choose;
    Bitmap photo1,photo2,photo3,photo4;
    private static final int CAMERA_REQUEST = 1888;
    String URL = "http://192.168.43.107/DemoUploadTwoImage/post.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML Declaration
        imageView1 = findViewById(R.id.mimageView);
        imageView2 =  findViewById(R.id.mimageView1);
        imageView3 = findViewById(R.id.mimageView2);
        imageView4 = findViewById(R.id.mimageView3);
        Choose = findViewById(R.id.choose);
        uploadImage =  findViewById(R.id.mButton);


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UploadTwoImages();
            }
        });
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void UploadTwoImages() {
        imageView1.buildDrawingCache();
        imageView2.buildDrawingCache();
        imageView3.buildDrawingCache();
        imageView4.buildDrawingCache();

       photo1 = imageView1.getDrawingCache();
        photo2 = imageView2.getDrawingCache();
        photo3 = imageView3.getDrawingCache();
        photo4 = imageView4.getDrawingCache();



        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Registration is in Process Please wait...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        pDialog.hide();
                        String result = response;
                        Log.e("Result", response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                pDialog.hide();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<>();
                String imageOne = getStringImage(photo1);
                String imageTwo = getStringImage(photo2);
                String imageThree = getStringImage(photo3);
                String imageFour = getStringImage(photo4);

                params.put("getdata", "UploadImage");


                params.put("insert_image_one", imageOne);
                params.put("insert_image_two", imageTwo);
                params.put("insert_image_three",imageThree);
                params.put("insert_image_four",imageFour);


                //Bank Information

                return params;
            }


        };

//Adding request to request queue
        VolleyAppController.getInstance().addToRequestQueue(stringRequest);

    }

    public void ChooseImg(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {


            if(imageView1.getDrawable()==null)
            {
                photo1 = (Bitmap) data.getExtras().get("data");
                imageView1.setImageBitmap(photo1);
                imageView1.setVisibility(View.VISIBLE);


            }
            else if(imageView2.getDrawable()==null)
            {
                photo2 = (Bitmap) data.getExtras().get("data");
                imageView2.setImageBitmap(photo2);
                imageView2.setVisibility(View.VISIBLE);
            }
            else if(imageView3.getDrawable()==null)
            {
                photo3 = (Bitmap) data.getExtras().get("data");
                imageView3.setImageBitmap(photo3);
                imageView3.setVisibility(View.VISIBLE);
            }
            else if(imageView4.getDrawable()==null)
            {
                photo4 = (Bitmap) data.getExtras().get("data");
                imageView4.setImageBitmap(photo4);
                imageView4.setVisibility(View.VISIBLE);
            }

            if(imageView1.getDrawable()!=null && imageView2.getDrawable()!=null && imageView3.getDrawable()!=null && imageView4.getDrawable()!=null)
            {
                Choose.setVisibility(View.GONE);

            }


        }
    }
}