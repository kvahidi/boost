package com.example.nwhacks19.boost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.snapchat.kit.sdk.SnapCreative;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitCompletionCallback;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitSendError;
import com.snapchat.kit.sdk.creative.exceptions.SnapStickerSizeException;
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory;
import com.snapchat.kit.sdk.creative.media.SnapSticker;
import com.snapchat.kit.sdk.creative.models.SnapContent;
import com.snapchat.kit.sdk.creative.models.SnapLiveCameraContent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

import static java.security.AccessController.getContext;

public class CreateQuestActivity extends Activity implements SnapCreativeKitCompletionCallback {

    private SnapCreativeKitApi snapCreativeKitApi;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quest);
        // gets API to use
        snapCreativeKitApi = SnapCreative.getApi(this);

        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStickerSnap(createSticker());

            }
        });
    }

    private void sendStickerSnap(SnapSticker snapSticker){
        // gets live camera feed ready
        SnapLiveCameraContent snapLiveCameraContent = new SnapLiveCameraContent();

/*        snapSticker.setWidth(200);
        snapSticker.setHeight(200);

        snapSticker.setPosX(0.5f);
        snapSticker.setPosY(0.5f);*/
        // sets the sticker on the live camera feed
        //snapLiveCameraContent.setSnapSticker(snapSticker);
        String questText =( (EditText) findViewById(R.id.editText)).getText().toString();
        Log.d("debugtest", "QUESTTEXT IS " + questText);
        snapLiveCameraContent.setAttachmentUrl("https://snapcore.azurewebsites.net/api/ModifyCounter?code=9K2tpB3lgRHcdLtR5fJBcajFGLgjydwaaSCwLAP4R4Vb9/dK7fILmQ==&questMessage=" + questText  + "&questId=51");
        snapLiveCameraContent.setCaptionText(questText + " Sent: " + getCountFromDatabase() + " times!");
        // launches snapchat with the live feed + sticker
        snapCreativeKitApi.sendWithCompletionHandler(snapLiveCameraContent, CreateQuestActivity.this);
    }

    private String getQuestText() {
        return null;
    }

    private int getCountFromDatabase() {
        int returnInt = 0;
        String urlLink = "http://www.signasl.org/sign/" + word;
        URL url;
        try {
            url = new URL(urlLink);
            Log.d("ocr", "Opening url connection " + urlLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader httpInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String inputLine, video = "";
            while ((inputLine = httpInput.readLine()) != null) {
                returnInt = Integer.parseInt(inputLine);
            }
            httpInput.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnInt;
    }

        private SnapSticker createSticker(){
        // gets factory class to create SnapSticker
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(this);
        SnapSticker sticker = null;
            Log.d("AAAGH", this.getFilesDir().toString());

        return sticker;    }


    private File getStickerFromDrawable() throws IOException {
        InputStream ins = getResources().openRawResource(R.raw.stickerbg);
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));
        StringBuffer sb = null;
        String line;
        while((line = br.readLine()) != null){
            sb.append(line);
        }

        File f = new File(sb.toString());
        return f;
    }

    @Override
    public void onSendSuccess() {
        String urlLink = "https://snapcore.azurewebsites.net/api/ModifyCounter?code=9K2tpB3lgRHcdLtR5fJBcajFGLgjydwaaSCwLAP4R4Vb9/dK7fILmQ==&userName=" + ((EditText)findViewById(R.id.editText)).getText().toString() + "&questId=51";
        URL url;
        try {
            url = new URL(urlLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        }
         catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } {

        }
        Log.d("AAH", "snnap sent");
    }

    @Override
    public void onSendFailed(SnapCreativeKitSendError snapCreativeKitSendError) {

    }
}
