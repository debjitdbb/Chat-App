package com.applozic.mobicomkit.uiwidgets.async;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.listners.AlCallback;
import com.applozic.mobicommons.commons.core.utils.Utils;
import com.applozic.mobicommons.encryption.EncryptionUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ALFormDataAsyncTask extends AsyncTask<Void, Void, String> {
    private WeakReference<Context> context;
    private AlCallback callback;
    private String contentType;
    private String data;
    private String accept;
    private String url;
    private Exception exception = null;
    private static final String TAG = "ALFormDataPost";

    public ALFormDataAsyncTask(Context context, String url, String accept, String contentType, String data, AlCallback callback) {
        this.context = new WeakReference<>(context);
        this.callback = callback;
        this.url = url;
        this.contentType = contentType;
        this.data = data;
        this.accept = accept;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            return getPostResponse(url, contentType, accept, data);
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        if (callback != null) {
            if (response != null) {
                callback.onSuccess(response);
            } else {
                callback.onError(exception);
            }
        }
        super.onPostExecute(response);
    }

    public String getPostResponse(String urlString, String contentType, String accept, String data) throws Exception {
        Utils.printLog(context.get(), TAG, "Calling url: " + urlString);
        HttpURLConnection connection;
        URL url;
        try {
            if (!TextUtils.isEmpty(MobiComUserPreference.getInstance(context.get()).getEncryptionKey())) {
                data = EncryptionUtils.encrypt(MobiComUserPreference.getInstance(context.get()).getEncryptionKey(), data);
            }
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (!TextUtils.isEmpty(contentType)) {
                connection.setRequestProperty("Content-Type", contentType);
            }
            if (!TextUtils.isEmpty(accept)) {
                connection.setRequestProperty("Accept", accept);
            }
            connection.connect();

            if (data != null) {
                byte[] dataBytes = data.getBytes("UTF-8");
                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.write(dataBytes);
                os.flush();
                os.close();
            }
            BufferedReader br = null;
            if (connection.getResponseCode() == 200 || connection.getResponseCode() == 201) {
                InputStream inputStream = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            }
            StringBuilder sb = new StringBuilder();
            try {
                String line;
                if (br != null) {
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    br.close();
                }
            }
            Utils.printLog(context.get(), TAG, "Response : " + sb.toString());
            if (!TextUtils.isEmpty(sb.toString())) {
                if (!TextUtils.isEmpty(MobiComUserPreference.getInstance(context.get()).getEncryptionKey())) {
                    return EncryptionUtils.decrypt(MobiComUserPreference.getInstance(context.get()).getEncryptionKey(), sb.toString());
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utils.printLog(context.get(), TAG, "Http call failed");
        return null;
    }
}
