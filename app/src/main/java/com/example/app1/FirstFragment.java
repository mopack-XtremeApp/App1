package com.example.app1;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FirstFragment extends Fragment {

    // globally
    private TextView myAwesomeTextView;
    private OkHttpClient client = new OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS).build();

    private Handler _handler = new Handler();

    public FirstFragment() {
        super();
    }


    void run(String url) {

        //RequestBody.c

        Request request = new Request.Builder()
                .url(url)
                //.post(RequBody.create())
                /*.addHeader()*/
                .build();

        try {
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FirstFragment", e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    final String ret = response.body().string();
                    _handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myAwesomeTextView.setText(ret);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myAwesomeTextView = view.findViewById(R.id.textview_first);


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                run("https://a458e845173b.ngrok.io/fabric/");

                //myAwesomeTextView.setText("222");
                //Log.i('123');
                /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
            }
        });
    }
}