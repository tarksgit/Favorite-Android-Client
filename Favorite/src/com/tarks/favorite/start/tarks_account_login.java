package com.tarks.favorite.start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.Window;
import com.tarks.favorite.R;

public class tarks_account_login extends SherlockActivity {
	Button bt;
	Button bt2;
    String myId, myPWord, myTitle, mySubject, myResult; 
    
    private class Downloader extends AsyncTask<String, Void, Bitmap> {

		protected Bitmap doInBackground(String... param) {
			// TODO Auto-generated method stub
			  //Error Login
			
			return downloadBitmap(myId);
		}

		@Override
		protected void onPreExecute() {
			Log.i("Async-Example", "onPreExecute Called");
			//set Progressbar
			   setSupportProgressBarIndeterminateVisibility(true);
		}

		protected void onPostExecute(Bitmap result) {
			Log.i("Async-Example", "onPostExecute Called");
			//no more progress
			//set Progressbar
			   setSupportProgressBarIndeterminateVisibility(false);
			  if(myResult.matches("")){
            	  //Error Login
            	  AlertDialog.Builder builder1 = new AlertDialog.Builder(tarks_account_login.this);
  				builder1.setMessage(getString(R.string.error_login)).setPositiveButton(getString(R.string.yes), null).setTitle(getString(R.string.error));				
  				builder1.show();
              }else{
            	  //Save auth key to temp
           //Setting Editor
 			SharedPreferences edit = getSharedPreferences("temp",
 					MODE_PRIVATE);
 			SharedPreferences.Editor editor = edit.edit();
 			editor.putString("temp_id", myResult);															
 			editor.commit();
             Intent intent = new Intent(tarks_account_login.this, join.class);
	 	    	 startActivity(intent); 
	 	    	 finish();
              }
			  

		}

		private Bitmap downloadBitmap(String url ) {
			try {


    				//import EditText
    				EditText edit1 = (EditText) findViewById(R.id.editText1);
    				String s1 = edit1.getText().toString();

    				EditText edit2 = (EditText) findViewById(R.id.editText2);
    				String s2 = edit2.getText().toString();
    				
    				//md5 password value
    				String src = s2;
    				String enc = getMD5Hash(src);
    				// --------------------------
    				// URL 설정하고 접속하기
    				// --------------------------
    				URL url1 = new URL(
    						"http://tarks.net/app/favorite/member/tarks_account_check.php"); // URL
    																				// 설정
    				HttpURLConnection http = (HttpURLConnection) url1
    						.openConnection(); // 접속
    				// --------------------------
    				// 전송 모드 설정 - 기본적인 설정이다
    				// --------------------------
    				http.setDefaultUseCaches(false);
    				http.setDoInput(true); // 서버에서 읽기 모드 지정
    				http.setDoOutput(true); // 서버로 쓰기 모드 지정
    				http.setRequestMethod("POST"); // 전송 방식은 POST

    				// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
    				http.setRequestProperty("content-type",
    						"application/x-www-form-urlencoded");
    				// --------------------------
    				// 서버로 값 전송
    				// --------------------------
    				StringBuffer buffer = new StringBuffer();
    				buffer.append("authcode").append("=").append("642979")
    						.append("&"); // php 변수에 값 대입
    				buffer.append("id").append("=").append(s1).append("&");
    				buffer.append("password").append("=").append(enc);

    				OutputStreamWriter outStream = new OutputStreamWriter(
    						http.getOutputStream(), "EUC-KR");
    				PrintWriter writer = new PrintWriter(outStream);
    				writer.write(buffer.toString());
    				writer.flush();
    				// --------------------------
    				// 서버에서 전송받기
    				// --------------------------
    				 InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");  
    	              BufferedReader reader = new BufferedReader(tmp); 
    	              StringBuilder builder = new StringBuilder(); 
    	              String str; 
    	              
    	              while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다 
    	                   builder.append(str);  //구분자 추가 안함
    	                  // builder.append(str + "\n"); 
    	                   // View에 표시하기 위해 라인 구분자 추가 
    	              } 
    	              myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
    		
    			} catch (MalformedURLException e) { 
    	                // 
    	         } catch (IOException e) { 
    	                //  
    	         }

			return null;
		}

    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Can use progress
		 requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.tarks_account);
		  setSupportProgressBarIndeterminateVisibility(false);
		
		bt = (Button) findViewById(R.id.button1);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(tarks_account_login.this, join.class); 
				startActivity(intent);
				finish();
			}
		});
		
		bt2 = (Button) findViewById(R.id.button2);
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				  Uri uri = Uri.parse("http://tarks.net/index.php?mid=main&act=dispMemberSignUpForm");
				     Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				     startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// 메뉴 버튼 구현부분
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.accept, menu);
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.yes:
			
			

		
				//no err
			try{	
			//import EditText
			EditText edit1 = (EditText) findViewById(R.id.editText1);
			String s1 = edit1.getText().toString();

			EditText edit2 = (EditText) findViewById(R.id.editText2);
			String s2 = edit2.getText().toString();
			
			if(s1.matches("")){
				AlertDialog.Builder builder = new AlertDialog.Builder(tarks_account_login.this);
				builder.setMessage(getString(R.string.type_id)).setPositiveButton(getString(R.string.yes), null).setTitle(getString(R.string.notification));				
				builder.show();
			}else{
				// TODO Auto-generated method stub
					new Downloader()
							.execute();
			}
			} catch (Exception e){
			
				// Not Connected To Internet
				AlertDialog.Builder builder = new AlertDialog.Builder(tarks_account_login.this);
				builder.setMessage(getString(R.string.networkerrord))
						.setPositiveButton(getString(R.string.yes), null)
						.setTitle(getString(R.string.networkerror));
				builder.show();
				
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// md5 encrypt 암호화
	public static String getMD5Hash(String s) {
		MessageDigest m = null;
		String hash = null;

		try {
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(), 0, s.length());
			hash = new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hash;
	}

}
