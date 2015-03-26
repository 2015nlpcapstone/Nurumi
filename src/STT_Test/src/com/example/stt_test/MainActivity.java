package com.example.stt_test;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// 150321 // yoon // for Speech to Text Demo 
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;


// 150322 // yoon // for send to Java TCPServer
import java.io.*;
import java.net.*;


public class MainActivity extends Activity {

    // 150321 // yoon // for Speech to Text Demo 
    Intent i; 
    SpeechRecognizer mRecognizer;
    TextView tv;
    private static final String TAG = "STT testing";
    
    
    // 150322 // yoon // for send to Java TCPServer
    String sentence;
    String modifiedSentence;
    BufferedReader inFromUser; 
    Socket clientSocket;
    DataOutputStream outToServer;
    BufferedReader inFromServer;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        // 150321 // yoon // for Speech to Text Demo 
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        tv = (TextView)findViewById(R.id.textView1);
        
        
        // 150322 // yoon // for TCP Clinet Socket
        inFromUser = new BufferedReader( new InputStreamReader(System.in));
        try {
          clientSocket = new Socket("localhost", 6789);
          outToServer = new DataOutputStream(clientSocket.getOutputStream());
          inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        
        // 150321 // yoon // STT Click Button event
        findViewById(R.id.button1).setOnClickListener(
            new Button.OnClickListener(){

              @Override
              public void onClick(View v) {
                // TODO Auto-generated method stub
                
                tv.setText("now Recording");
                mRecognizer.startListening(i);
              }
        });
        
        
        // 150321 // yoon // for Speech to Text Demo 
        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        
    }
    

    // 150321 // yoon // works during 'startListening'
    private RecognitionListener listener = new RecognitionListener() {

      @Override
      public void onRmsChanged(float rmsdB) {
          // TODO Auto-generated method stub
        Log.d(TAG, "onRmsChanged");
      }
      
     @Override
     public void onResults(Bundle results) {
         // TODO Auto-generated method stub
       Log.d(TAG, "onResults");
       
       String key = "";
       key = SpeechRecognizer.RESULTS_RECOGNITION;
       ArrayList<String> mResult = results.getStringArrayList(key);
       String[] rs = new String[mResult.size()];
       mResult.toArray(rs);
       tv.setText(""+rs[0]);  
         
     }
      
      @Override
      public void onReadyForSpeech(Bundle params) {
          // TODO Auto-generated method stub
          Log.d(TAG, "onReadyForSpeech");
           
      }
       
      @Override
      public void onPartialResults(Bundle partialResults) {
          // TODO Auto-generated method stub
        Log.d(TAG, "onPartialResults");

      }
       
      @Override
      public void onEvent(int eventType, Bundle params) {
          // TODO Auto-generated method stub
        Log.d(TAG, "onEvent");

      }
       
      @Override
      public void onError(int error) {
          // TODO Auto-generated method stub
        Log.d(TAG, "onError");
        tv.setText("errcode : " + error);  

      }
       
      @Override
      public void onEndOfSpeech() {
          // TODO Auto-generated method stub
        Log.d(TAG, "onEndOfSpeech");
        tv.setText("Speech end");  

      }
       
      @Override
      public void onBufferReceived(byte[] buffer) {
          // TODO Auto-generated method stub
        Log.d(TAG, "onBufferReceived");

      }
       
      @Override
      public void onBeginningOfSpeech() {
          // TODO Auto-generated method stub
        Log.d(TAG, "onBeginningOfSpeech");
        tv.setText("Speech begin");  
   
      }
  };
    
}
