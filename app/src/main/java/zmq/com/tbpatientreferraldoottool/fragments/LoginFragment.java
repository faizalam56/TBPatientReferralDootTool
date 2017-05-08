package zmq.com.tbpatientreferraldoottool.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import zmq.com.tbpatientreferraldoottool.R;

/**
 * Created by zmq162 on 10/10/16.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private Toolbar toolbar;
    private LoginCommunicator communicator;
    private  EditText userId,password;
    private Button login;
    private ProgressDialog progressDialog;

    public void setLoginCommunicator(LoginCommunicator communicator){
        this.communicator = communicator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("TB Referral Tool");
//        toolbar.setLogo(R.drawable.icon);
        toolbar.setTitleTextColor(Color.WHITE);


        userId = (EditText) getActivity().findViewById(R.id.userId);
        password = (EditText) getActivity().findViewById(R.id.password);
        login = (Button) getActivity().findViewById(R.id.btn_signin);

//        login.setTransformationMethod(null);  //it is also use to prevent to write all text in capital letter.


        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(userId.getText().toString().length()<=0 && password.getText().toString().length()<=0){
            userId.requestFocus();
            Toast.makeText(getContext(),"Please enter username & password",Toast.LENGTH_SHORT).show();
        } else if(password.getText().toString().length()<=0){
            password.requestFocus();
            Toast.makeText(getContext(),"Please enter password",Toast.LENGTH_SHORT).show();
        }else if(userId.getText().toString().length()<=0 && password.getText().toString().length()>=0){
            userId.requestFocus();
            Toast.makeText(getContext(),"Please enter username",Toast.LENGTH_SHORT).show();
        }else {
            communicator.clickSingnIn(userId.getText().toString(), password.getText().toString());
        }

    }

    public interface LoginCommunicator{
         void clickSingnIn(String username, String password);
    }
}
