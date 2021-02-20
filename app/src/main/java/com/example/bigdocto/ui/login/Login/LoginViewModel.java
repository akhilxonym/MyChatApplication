package com.example.bigdocto.ui.login.Login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;

import com.example.bigdocto.data.LoginRepository;
import com.example.bigdocto.data.LoginResponse;
import com.example.bigdocto.data.Result;
import com.example.bigdocto.data.model.LoggedInUser;
import com.example.bigdocto.R;
import com.example.bigdocto.ui.login.Constants;
import com.example.bigdocto.ui.login.Login.LoggedInUserView;
import com.example.bigdocto.ui.login.Login.LoginFormState;
import com.example.bigdocto.ui.login.Login.LoginResult;

import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    public SharedPreferences sharedpreferences;


    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        new LoginService().execute(username,password);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

     class LoginService extends AsyncTask<String,Void, LoginResponse> {
        @Override
        protected LoginResponse doInBackground(String... arg) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization","[object object]");
            Log.i("Username:","########"+arg[0]);
            HttpEntity<LoggedInUser> entity = new HttpEntity<LoggedInUser>(new LoggedInUser(arg[0],arg[1]),headers);
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            LoginResponse loginResponse=null;
            try{
                loginResponse = restTemplate.postForObject(Constants.URL+"auth/login", entity, LoginResponse.class);

            }catch(Exception e){

            }
           /* RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            LoggedInUser loggedInUser=(LoggedInUser) restTemplate.getForObject(Constants.URL,LoggedInUser.class);
            return loggedInUser;*/
            return loginResponse;
        }

        @Override
        protected void onPostExecute(LoginResponse loginResponse) {
//            Result<LoggedInUser> result =new Result.Success<>(loggedInUser);

            Result<LoggedInUser> result=null;
            if(loginResponse!=null){
                 result =new Result.Success<>(new LoggedInUser(loginResponse.getUserName(),loginResponse.getToken()));
            }
            if (result instanceof Result.Success) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                loginResult.setValue(new LoginResult(new LoggedInUserView(data.getUserNAme())));
            } else {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
//            Log.i("User############ ",loginResponse.getUserName() + "### " + loginResponse.getToken());
            //    Toast.makeText(LoginActivity.class,loggedInUser.getDisplayName(),Toast.LENGTH_LONG).show();
            super.onPostExecute(loginResponse);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}