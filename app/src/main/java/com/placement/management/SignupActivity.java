package com.placement.management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.*;
import android.widget.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.text.Editable;
import android.text.TextWatcher;

public class SignupActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map1 = new HashMap<>();
	private String code = "";
	private String verification_id = "";
	
	private ArrayList<String> val = new ArrayList<>();
	
	private LinearLayout linear1;
	private ScrollView vscroll1;
	private ScrollView vscroll2;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private ImageView imageview1;
	private LinearLayout linear16;
	private ImageView imageview2;
	private TextView textview1;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private Button continuebtn;
	private Spinner spinner1;
	private ImageView imageview11;
	private ImageView imageview3;
	private EditText username;
	private ImageView imageview4;
	private EditText email;
	private ImageView imageview5;
	private EditText password;
	private TextView textview3;
	private TextView gottologin;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private LinearLayout linear12;
	private ImageView imageview6;
	private ImageView imageview7;
	private TextView textview4;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private Button createaccbtn;
	private ProgressBar progressbar1;
	private ImageView imageview8;
	private TextView textview7;
	private EditText phno;
	private LinearLayout getotpbtn;
	private TextView textview5;
	private ProgressBar progressbar2;
	private ImageView imageview10;
	private EditText otp;
	
	private FirebaseAuth auth;
	private OnCompleteListener<Void> auth_updateEmailListener;
	private OnCompleteListener<Void> auth_updatePasswordListener;
	private OnCompleteListener<Void> auth_emailVerificationSentListener;
	private OnCompleteListener<Void> auth_deleteUserListener;
	private OnCompleteListener<Void> auth_updateProfileListener;
	private OnCompleteListener<AuthResult> auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> auth_googleSignInListener;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private Intent i = new Intent();
	private DatabaseReference Students = _firebase.getReference("Students");
	private ChildEventListener _Students_child_listener;
	private PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneauth;
	private PhoneAuthProvider.ForceResendingToken phoneauth_resendToken;
	private AlertDialog.Builder d;
	private AlertDialog.Builder d2;
	private Intent n = new Intent();
	private DatabaseReference Admin = _firebase.getReference("Admin");
	private ChildEventListener _Admin_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.signup);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		vscroll2 = (ScrollView) findViewById(R.id.vscroll2);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		continuebtn = (Button) findViewById(R.id.continuebtn);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		imageview11 = (ImageView) findViewById(R.id.imageview11);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		username = (EditText) findViewById(R.id.username);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		email = (EditText) findViewById(R.id.email);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		password = (EditText) findViewById(R.id.password);
		textview3 = (TextView) findViewById(R.id.textview3);
		gottologin = (TextView) findViewById(R.id.gottologin);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		textview4 = (TextView) findViewById(R.id.textview4);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		createaccbtn = (Button) findViewById(R.id.createaccbtn);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		textview7 = (TextView) findViewById(R.id.textview7);
		phno = (EditText) findViewById(R.id.phno);
		getotpbtn = (LinearLayout) findViewById(R.id.getotpbtn);
		textview5 = (TextView) findViewById(R.id.textview5);
		progressbar2 = (ProgressBar) findViewById(R.id.progressbar2);
		imageview10 = (ImageView) findViewById(R.id.imageview10);
		otp = (EditText) findViewById(R.id.otp);
		auth = FirebaseAuth.getInstance();
		d = new AlertDialog.Builder(this);
		d2 = new AlertDialog.Builder(this);
		
		continuebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((username.getText().toString().length() > 0) && ((email.getText().toString().length() > 0) && (password.getText().toString().length() > 0))) {
					vscroll1.setVisibility(View.GONE);
					vscroll2.setVisibility(View.VISIBLE);
				}
				else {
					
				}
			}
		});
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				spinner1.setSelection((int)(_position));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		gottologin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), LoginActivity.class);
				startActivity(i);
			}
		});
		
		createaccbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (phno.getText().toString().length() > 0) {
					if (otp.getText().toString().length() > 0) {
						FirebaseAuth.getInstance().signInWithCredential(PhoneAuthProvider.getCredential(verification_id, code)).addOnCompleteListener(auth_phoneAuthListener);
						progressbar1.setVisibility(View.VISIBLE);
					}
					else {
						
					}
				}
				else {
					
				}
			}
		});
		
		getotpbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (phno.getText().toString().length() > 3) {
					if (phno.getText().toString().length() < 11) {
						PhoneAuthProvider.getInstance().verifyPhoneNumber(textview7.getText().toString().concat(phno.getText().toString()), 60, TimeUnit.SECONDS, SignupActivity.this, phoneauth);
						progressbar2.setVisibility(View.VISIBLE);
						createaccbtn.setEnabled(true);
					}
					else {
						phno.setError("Invalid phone number");
					}
				}
				else {
					phno.setError("please enter phone number");
				}
			}
		});
		
		otp.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				code = _charSeq;
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		_Students_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		Students.addChildEventListener(_Students_child_listener);
		
		phoneauth = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
			@Override
			public void onVerificationCompleted(PhoneAuthCredential _credential) {
				
			}
			
			@Override
			public void onVerificationFailed(FirebaseException e) {
				final String _exception = e.getMessage();
				progressbar1.setVisibility(View.GONE);
				d.setTitle("Registration failed");
				d.setMessage(_exception);
				d.setCancelable(false);
				d.setIcon(R.drawable.wrong);
				d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
			
			@Override
			public void onCodeSent(String _verificationId, PhoneAuthProvider.ForceResendingToken _token) {
				verification_id = _verificationId;
				Toastmaker.showMessage(getApplicationContext(), "OTP sent");
				progressbar1.setVisibility(View.GONE);
				progressbar2.setVisibility(View.GONE);
			}
		};
		
		_Admin_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		Admin.addChildEventListener(_Admin_child_listener);
		
		auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				if (_success) {
					auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(SignupActivity.this, _auth_create_user_listener);
				}
				else {
					d.setTitle("Registration failed");
					d.setMessage(_errorMessage);
					d.setIcon(R.drawable.wrong);
					d.setCancelable(false);
					d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					d.create().show();
					progressbar1.setVisibility(View.GONE);
				}
			}
		};
		
		auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					if (spinner1.getSelectedItemPosition() == 0) {
						map1 = new HashMap<>();
						map1.put("StudentName", username.getText().toString());
						map1.put("PhoneNumber", phno.getText().toString());
						map1.put("Email", email.getText().toString());
						map1.put("Password", password.getText().toString());
						Students.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map1);
						d2.setTitle("Registration Successful");
						d2.setMessage("You are Successfully registered. you can now use this Email & Password for Login.");
						d2.setIcon(R.drawable.correct_3);
						d2.setCancelable(false);
						d2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								n.setClass(getApplicationContext(), StudentScreenActivity.class);
								startActivity(n);
								finish();
							}
						});
						d2.create().show();
						progressbar2.setVisibility(View.GONE);
						progressbar1.setVisibility(View.GONE);
					}
					else {
						if (spinner1.getSelectedItemPosition() == 1) {
							map1 = new HashMap<>();
							map1.put("PlacementOfficerName", username.getText().toString());
							map1.put("PhoneNumber", phno.getText().toString());
							map1.put("Email", email.getText().toString());
							map1.put("Password", password.getText().toString());
							Admin.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map1);
							d2.setTitle("Registration Successful");
							d2.setMessage(_errorMessage);
							d2.setIcon(R.drawable.correct_3);
							d2.setCancelable(false);
							d2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									n.setClass(getApplicationContext(), PlacementOfficerActivity.class);
									startActivity(n);
									finish();
								}
							});
							d2.create().show();
							progressbar2.setVisibility(View.GONE);
							progressbar1.setVisibility(View.GONE);
						}
					}
				}
				else {
					d.setTitle("Registration failed");
					d.setMessage(_errorMessage);
					d.setIcon(R.drawable.wrong);
					d.setCancelable(false);
					d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					d.create().show();
					progressbar2.setVisibility(View.GONE);
					progressbar1.setVisibility(View.GONE);
				}
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	private void initializeLogic() {
		progressbar1.setVisibility(View.GONE);
		progressbar2.setVisibility(View.GONE);
		createaccbtn.setEnabled(false);
		val.add("Student Signup");
		val.add("Admin Signup");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, val));
		((ArrayAdapter)spinner1.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
