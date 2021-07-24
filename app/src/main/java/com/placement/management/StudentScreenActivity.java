package com.placement.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import android.net.Uri;
import java.io.File;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class StudentScreenActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private DrawerLayout _drawer;
	private HashMap<String, Object> map = new HashMap<>();
	private String studentname = "";
	private String studentemail = "";
	private String studentphonenumber = "";
	private String studentbranch = "";
	private HashMap<String, Object> map2 = new HashMap<>();
	private String file_name = "";
	
	private ArrayList<HashMap<String, Object>> company_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> download_doclist = new ArrayList<>();
	
	private LinearLayout basement;
	private BottomNavigationView bottomnavigationview1;
	private LinearLayout companies_list;
	private LinearLayout update_status;
	private LinearLayout download_docs;
	private LinearLayout topbar;
	private LinearLayout listviewbase;
	private LinearLayout linear56;
	private LinearLayout linear52;
	private TextView textview5;
	private TextView textview40;
	private ImageView imageview16;
	private ListView companies_listview;
	private LinearLayout linear63;
	private LinearLayout linear143;
	private ImageView imageview19;
	private TextView textview24;
	private LinearLayout linear385;
	private LinearLayout linear388;
	private TextView textview38;
	private TextView textview39;
	private LinearLayout linear3;
	private TextView student_nametxt;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private TextView textview1;
	private EditText branchtxt;
	private TextView textview3;
	private EditText companyname;
	private TextView textview36;
	private EditText packagepa;
	private TextView updatebtn;
	private LinearLayout linear37;
	private LinearLayout dwnldprogresslinear;
	private ListView download_listview;
	private ImageView imageview1;
	private TextView textview18;
	private TextView downloadprogresstext;
	private ProgressBar progressbar1;
	private LinearLayout _drawer_linear1;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear3;
	private TextView _drawer_textview1;
	private ImageView _drawer_imageview2;
	private TextView _drawer_logoutbtn;

	
	private DatabaseReference Registered_Students = _firebase.getReference("Registered_Students");
	private ChildEventListener _Registered_Students_child_listener;
	private DatabaseReference Companies_List = _firebase.getReference("Companies_List");
	private ChildEventListener _Companies_List_child_listener;
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
	private DatabaseReference Students = _firebase.getReference("Students");
	private ChildEventListener _Students_child_listener;
	private DatabaseReference Placed_Students = _firebase.getReference("Placed_Students");
	private ChildEventListener _Placed_Students_child_listener;
	private StorageReference Placed_Students_Docs = _firebase_storage.getReference("Placed_Students_Docs");
	private OnCompleteListener<Uri> _Placed_Students_Docs_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _Placed_Students_Docs_download_success_listener;
	private OnSuccessListener _Placed_Students_Docs_delete_success_listener;
	private OnProgressListener _Placed_Students_Docs_upload_progress_listener;
	private OnProgressListener _Placed_Students_Docs_download_progress_listener;
	private OnFailureListener _Placed_Students_Docs_failure_listener;
	private AlertDialog.Builder d;
	private DatabaseReference Placed_Students_File = _firebase.getReference("Placed_Students_File");
	private ChildEventListener _Placed_Students_File_child_listener;
	private Intent o = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.student_screen);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = (DrawerLayout) findViewById(R.id._drawer);ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(StudentScreenActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		basement = (LinearLayout) findViewById(R.id.basement);
		bottomnavigationview1 = (BottomNavigationView) findViewById(R.id.bottomnavigationview1);
		companies_list = (LinearLayout) findViewById(R.id.companies_list);
		update_status = (LinearLayout) findViewById(R.id.update_status);
		download_docs = (LinearLayout) findViewById(R.id.download_docs);
		topbar = (LinearLayout) findViewById(R.id.topbar);
		listviewbase = (LinearLayout) findViewById(R.id.listviewbase);
		linear56 = (LinearLayout) findViewById(R.id.linear56);
		linear52 = (LinearLayout) findViewById(R.id.linear52);
		textview5 = (TextView) findViewById(R.id.textview5);
		textview40 = (TextView) findViewById(R.id.textview40);
		imageview16 = (ImageView) findViewById(R.id.imageview16);
		companies_listview = (ListView) findViewById(R.id.companies_listview);
		linear63 = (LinearLayout) findViewById(R.id.linear63);
		linear143 = (LinearLayout) findViewById(R.id.linear143);
		imageview19 = (ImageView) findViewById(R.id.imageview19);
		textview24 = (TextView) findViewById(R.id.textview24);
		linear385 = (LinearLayout) findViewById(R.id.linear385);
		linear388 = (LinearLayout) findViewById(R.id.linear388);
		textview38 = (TextView) findViewById(R.id.textview38);
		textview39 = (TextView) findViewById(R.id.textview39);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		student_nametxt = (TextView) findViewById(R.id.student_nametxt);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		textview1 = (TextView) findViewById(R.id.textview1);
		branchtxt = (EditText) findViewById(R.id.branchtxt);
		textview3 = (TextView) findViewById(R.id.textview3);
		companyname = (EditText) findViewById(R.id.companyname);
		textview36 = (TextView) findViewById(R.id.textview36);
		packagepa = (EditText) findViewById(R.id.packagepa);
		updatebtn = (TextView) findViewById(R.id.updatebtn);
		linear37 = (LinearLayout) findViewById(R.id.linear37);
		dwnldprogresslinear = (LinearLayout) findViewById(R.id.dwnldprogresslinear);
		download_listview = (ListView) findViewById(R.id.download_listview);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview18 = (TextView) findViewById(R.id.textview18);
		downloadprogresstext = (TextView) findViewById(R.id.downloadprogresstext);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		_drawer_linear1 = (LinearLayout) _nav_view.findViewById(R.id.linear1);
		_drawer_linear2 = (LinearLayout) _nav_view.findViewById(R.id.linear2);
		_drawer_linear3 = (LinearLayout) _nav_view.findViewById(R.id.linear3);
		_drawer_textview1 = (TextView) _nav_view.findViewById(R.id.textview1);
		_drawer_imageview2 = (ImageView) _nav_view.findViewById(R.id.imageview2);
		_drawer_logoutbtn = (TextView) _nav_view.findViewById(R.id.logoutbtn);
		auth = FirebaseAuth.getInstance();
		d = new AlertDialog.Builder(this);
		
		bottomnavigationview1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				final String _titleMenu = item.getTitle().toString();
				if (_titleMenu.equals("Companies")) {
						companies_list.setVisibility(View.VISIBLE);
					update_status.setVisibility(View.GONE);
					download_docs.setVisibility(View.GONE);				
				}
				if (_titleMenu.equals("Update Status")) {
						companies_list.setVisibility(View.GONE);
					update_status.setVisibility(View.VISIBLE);
					download_docs.setVisibility(View.GONE);				
				}
				if (_titleMenu.equals("Download Docs")) {
						companies_list.setVisibility(View.GONE);
					update_status.setVisibility(View.GONE);
					download_docs.setVisibility(View.VISIBLE);				
				}
				return true;
			}
		});
		
		imageview16.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});
		
		updatebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				map2 = new HashMap<>();
				map2.put("StudentName", student_nametxt.getText().toString());
				map2.put("CompanyName", companyname.getText().toString());
				map2.put("Branch", branchtxt.getText().toString());
				map2.put("Package", packagepa.getText().toString());
				Placed_Students.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map2);
				Toastmaker.showMessage(getApplicationContext(), "Updated");
				companyname.setText("");
				packagepa.setText("");
				branchtxt.setText("");
			}
		});
		
		_Registered_Students_child_listener = new ChildEventListener() {
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
		Registered_Students.addChildEventListener(_Registered_Students_child_listener);
		
		_Companies_List_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Companies_List.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						company_list = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								company_list.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						companies_listview.setAdapter(new Companies_listviewAdapter(company_list));
						((BaseAdapter)companies_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(company_list);
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
		Companies_List.addChildEventListener(_Companies_List_child_listener);
		
		_Students_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null) && _childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					studentname = _childValue.get("StudentName").toString();
					studentemail = _childValue.get("Email").toString();
					studentphonenumber = _childValue.get("PhoneNumber").toString();
					student_nametxt.setText(_childValue.get("StudentName").toString());
				}
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
		
		_Placed_Students_child_listener = new ChildEventListener() {
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
		Placed_Students.addChildEventListener(_Placed_Students_child_listener);
		
		_Placed_Students_Docs_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_Placed_Students_Docs_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setVisibility(View.VISIBLE);
				progressbar1.setProgress((int)_progressValue);
				downloadprogresstext.setText("Downloading ".concat(String.valueOf((long)(_progressValue)).concat("%")));
			}
		};
		
		_Placed_Students_Docs_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				
			}
		};
		
		_Placed_Students_Docs_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				Toastmaker.showMessage(getApplicationContext(), "Download Successful");
				dwnldprogresslinear.setVisibility(View.GONE);
			}
		};
		
		_Placed_Students_Docs_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_Placed_Students_Docs_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_Placed_Students_File_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Placed_Students_File.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						download_doclist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								download_doclist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						download_listview.setAdapter(new Download_listviewAdapter(download_doclist));
						((BaseAdapter)download_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(download_doclist);
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Placed_Students_File.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						download_doclist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								download_doclist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						download_listview.setAdapter(new Download_listviewAdapter(download_doclist));
						((BaseAdapter)download_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(download_doclist);
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
		Placed_Students_File.addChildEventListener(_Placed_Students_File_child_listener);
		
		_drawer_logoutbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseAuth.getInstance().signOut();
				o.setClass(getApplicationContext(), LoginActivity.class);
				startActivity(o);
				finish();
			}
		});
		
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
		bottomnavigationview1.getMenu().add("Companies").setIcon(R.drawable.ic_account_balance_white);
		bottomnavigationview1.getMenu().add("Update Status").setIcon(R.drawable.ic_create_white);
		bottomnavigationview1.getMenu().add("Download Docs").setIcon(R.drawable.ic_cloud_download_white);
		bottomnavigationview1.setItemIconSize(55);
		Companies_List.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				company_list = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						company_list.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				companies_listview.setAdapter(new Companies_listviewAdapter(company_list));
				((BaseAdapter)companies_listview.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		Placed_Students_File.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				download_doclist = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						download_doclist.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				download_listview.setAdapter(new Download_listviewAdapter(download_doclist));
				((BaseAdapter)download_listview.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		getSupportActionBar().hide();
		_drawerwidth();
		progressbar1.setVisibility(View.GONE);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		d.setTitle("Do you want to exit the app?");
		d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
			}
		});
		d.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		d.create().show();
	}
	private void _drawerwidth () {
		final LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		_nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
	}
	
	
	public class Companies_listviewAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Companies_listviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.student_companylist, null);
			}
			
			final LinearLayout linear3 = (LinearLayout) _v.findViewById(R.id.linear3);
			final TextView campany_name = (TextView) _v.findViewById(R.id.campany_name);
			final LinearLayout linear4 = (LinearLayout) _v.findViewById(R.id.linear4);
			final LinearLayout linear5 = (LinearLayout) _v.findViewById(R.id.linear5);
			final LinearLayout linear6 = (LinearLayout) _v.findViewById(R.id.linear6);
			final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
			final TextView date = (TextView) _v.findViewById(R.id.date);
			final TextView textview3 = (TextView) _v.findViewById(R.id.textview3);
			final TextView location = (TextView) _v.findViewById(R.id.location);
			final TextView textview5 = (TextView) _v.findViewById(R.id.textview5);
			final LinearLayout linear8 = (LinearLayout) _v.findViewById(R.id.linear8);
			final LinearLayout linear9 = (LinearLayout) _v.findViewById(R.id.linear9);
			final TextView d1 = (TextView) _v.findViewById(R.id.d1);
			final TextView d2 = (TextView) _v.findViewById(R.id.d2);
			final TextView d3 = (TextView) _v.findViewById(R.id.d3);
			final TextView d4 = (TextView) _v.findViewById(R.id.d4);
			final TextView d5 = (TextView) _v.findViewById(R.id.d5);
			final TextView registerbtn = (TextView) _v.findViewById(R.id.registerbtn);
			
			if (company_list.get((int)_position).containsKey("CompanyName")) {
				campany_name.setText(company_list.get((int)_position).get("CompanyName").toString());
			}
			if (company_list.get((int)_position).containsKey("RecruitmentDate")) {
				date.setText(company_list.get((int)_position).get("RecruitmentDate").toString());
			}
			if (company_list.get((int)_position).containsKey("Location")) {
				location.setText(company_list.get((int)_position).get("Location").toString());
			}
			if (company_list.get((int)_position).containsKey("Department1")) {
				d1.setText(company_list.get((int)_position).get("Department1").toString());
			}
			else {
				d1.setVisibility(View.GONE);
			}
			if (company_list.get((int)_position).containsKey("Department2")) {
				d2.setText(company_list.get((int)_position).get("Department2").toString());
			}
			else {
				d2.setVisibility(View.GONE);
			}
			if (company_list.get((int)_position).containsKey("Department3")) {
				d3.setText(company_list.get((int)_position).get("Department3").toString());
			}
			else {
				d3.setVisibility(View.GONE);
			}
			if (company_list.get((int)_position).containsKey("Department4")) {
				d4.setText(company_list.get((int)_position).get("Department4").toString());
			}
			else {
				d4.setVisibility(View.GONE);
			}
			if (company_list.get((int)_position).containsKey("Department5")) {
				d5.setText(company_list.get((int)_position).get("Department5").toString());
			}
			else {
				d5.setVisibility(View.GONE);
			}
			registerbtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					map = new HashMap<>();
					map.put("Email", studentemail);
					map.put("PhoneNumber", studentphonenumber);
					map.put("StudentName", studentname);
					map.put("CompanyName", company_list.get((int)_position).get("CompanyName").toString());
					map.put("Location",location.getText().toString());
					map.put("Date",date.getText().toString());
					Registered_Students.push().updateChildren(map);
					//Registered_Students.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
					Toastmaker.showMessage(getApplicationContext(), "Registered Successfully");
				}
			});
			
			return _v;
		}
	}
	
	public class Download_listviewAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Download_listviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.download_doc_list, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _v.findViewById(R.id.imageview1);
			final TextView filename = (TextView) _v.findViewById(R.id.filename);
			final ImageView downloadbtn = (ImageView) _v.findViewById(R.id.downloadbtn);
			
			if (_data.get((int)_position).containsKey("file")) {
				filename.setText(_data.get((int)_position).get("file").toString());
				file_name = Uri.parse(Uri.parse(_data.get((int)_position).get("file").toString()).getLastPathSegment()).getLastPathSegment();
			}
			downloadbtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).containsKey("Download_Url")) {
						_firebase_storage.getReferenceFromUrl(_data.get((int)_position).get("Download_Url").toString()).getFile(new File(FileUtil.getExternalStorageDir().concat("/Download".concat("/".concat(file_name))))).addOnSuccessListener(_Placed_Students_Docs_download_success_listener).addOnFailureListener(_Placed_Students_Docs_failure_listener).addOnProgressListener(_Placed_Students_Docs_download_progress_listener);
					}
				}
			});
			
			return _v;
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
