package sundosoft.co.eco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    String mVerificationId;

    EditText inputName;
    EditText inputPhoneNumber;
    EditText inputAuthNumber;
    Button   btnSendAuth;
    Button   btnAuth;
    CheckBox cbxInfoPrivacy;
    CheckBox cbxInfoInvest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        Log.v("sundo", "PhoneAuthActivity 동작");

        inputName        = (EditText)findViewById(R.id.edtName);
        inputPhoneNumber = (EditText)findViewById(R.id.edtPhoneNumber);
        inputAuthNumber  = (EditText)findViewById(R.id.edtAuthNumber);
        cbxInfoPrivacy   = (CheckBox)findViewById(R.id.cbxInfoPrivacy);
        cbxInfoInvest    = (CheckBox)findViewById(R.id.cbxInfoInvest);
        btnSendAuth      = (Button)findViewById(R.id.btnSendAuth);
        btnAuth          = (Button)findViewById(R.id.btnAuth);
        btnSendAuth.setOnClickListener(btnSendAuthHandler);
        btnAuth.setOnClickListener(btnAuthHandler);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.v("sundo", "Firebase 인증 성공!");

                            FirebaseUser user = task.getResult().getUser();
                            Intent authIntent = new Intent();
                            authIntent.putExtra("user", user);
                            setResult(RESULT_OK, authIntent);
                            startActivity(new Intent(PhoneAuthActivity.this,MainMenuActivity.class));

                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.v("sundo", "Firebase 인증 실패!", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(), "잘못된 인증번호입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    View.OnClickListener btnSendAuthHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("sundo", cbxInfoPrivacy.isChecked() + "," + cbxInfoInvest.isChecked());

            if(inputName.getText().toString().length() <= 0) {
                Toast.makeText(PhoneAuthActivity.this, "성명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(inputPhoneNumber.getText().toString().length() != 11) {
                Toast.makeText(PhoneAuthActivity.this, "하이픈 없이 11자리의 휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!cbxInfoPrivacy.isChecked()) {
                Toast.makeText(PhoneAuthActivity.this, "개인정보 제공에 동의하셔야 인증절차가 실행됩니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!cbxInfoInvest.isChecked()) {
                Toast.makeText(PhoneAuthActivity.this, "조사원 정보 기억에 동의하셔야 인증절차가 실행됩니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            //임시
            startActivity(new Intent(PhoneAuthActivity.this,MainMenuActivity.class));
            finish();


            AlertDialog.Builder builder = new AlertDialog.Builder(PhoneAuthActivity.this);
            builder.setTitle("안내").setMessage("분명하지 않은 조사자 정보를 입력할 경우 조사자 정보가 처리되지 않을 수 있습니다. 작성하신 성명이 맞는지 한번 더 확인해주십시오. 입력하신 연락처로 인증번호를 전송하시겠습니까?");
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String inputValue = inputPhoneNumber.getText().toString();
                    StringBuilder builder = new StringBuilder(inputValue);
                    builder.setCharAt(0, '2');
                    inputValue = "+8" + builder.toString();
                    Log.v("sundo", "입력한 휴대폰번호 : " + inputValue);

                    if(inputValue.length() != 13) {
                        Toast.makeText(getApplicationContext(), "잘못된 휴대폰 번호입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    //테스트
//                    inputValue = "+821012345678";
//                    Intent temp = new Intent().putExtra("user", "user");
//                    setResult(RESULT_OK, temp);
//                    finish();
//                    if(1==1) return;

                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(inputValue)
                            .setTimeout(10L, TimeUnit.SECONDS)
                            .setActivity(PhoneAuthActivity.this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    // Save the verification id somewhere
                                    // ...


                                    // The corresponding whitelisted code above should be used to complete sign-in.
                                    //MainActivity.this.enableUserManuallyInputCode();
                                    mVerificationId = verificationId;
                                    Log.v("sundo", "인증번호전송완료 [mVerificationId : " + mVerificationId + "]");
                                    Toast.makeText(getApplicationContext(), "인증번호를 발송했습니다.", Toast.LENGTH_SHORT).show();
                                    inputAuthNumber.setVisibility(View.VISIBLE);
                                    btnAuth.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                                    super.onCodeAutoRetrievalTimeOut(s);

                                }

                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                    // Sign in with the credential
                                    // ...

                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    // ...
                                }
                            }).build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            });
            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    };

    View.OnClickListener btnAuthHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String inputValue = inputAuthNumber.getText().toString();
            Log.v("sundo", "입력한 인증번호 : " + inputValue);

            if(inputValue.length() <= 0) {
                Toast.makeText(getApplicationContext(), "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else  {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, inputValue);
                signInWithPhoneAuthCredential(credential);


            }
        }
    };



}