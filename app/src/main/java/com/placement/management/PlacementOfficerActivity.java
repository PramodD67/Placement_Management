package com.placement.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
import android.app.*;
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
import android.widget.ProgressBar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Continuation;
import android.net.Uri;
import java.io.File;
import android.content.Intent;
import android.content.ClipData;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class PlacementOfficerActivity extends AppCompatActivity {
	
	public final int REQ_CD_PICKER = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private FloatingActionButton _fab;
	private DrawerLayout _drawer;
	private String filename = "";
	private String file = "";
	private HashMap<String, Object> mp = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> companies_maplist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> registered_stu_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> placed_student_list = new ArrayList<>();
	
	private LinearLayout basement;
	private BottomNavigationView bottomnavigationview1;
	private LinearLayout companies_list;
	private LinearLayout upload_documents;
	private LinearLayout registered_students;
	private LinearLayout placed_students;
	private LinearLayout topbar;
	private LinearLayout listviewbase;
	private LinearLayout linear56;
	private LinearLayout linear52;
	private TextView textview5;
	private TextView textview34;
	private ImageView imageview16;
	private ListView companies_listview;
	private LinearLayout linear63;
	private LinearLayout linear143;
	private ImageView imageview19;
	private TextView textview24;
	private LinearLayout linear386;
	private ImageView imageview23;
	private LinearLayout linear385;
	private LinearLayout linear390;
	private LinearLayout linear389;
	private LinearLayout linear388;
	private TextView textview31;
	private TextView textview32;
	private TextView choose_file_btn;
	private ProgressBar progressbar1;
	private TextView progress_text;
	private TextView filename_text;
	private TextView uploadbtn;
	private LinearLayout linear37;
	private LinearLayout linear134;
	private ListView regstudents_listview;
	private ImageView imageview1;
	private TextView textview18;
	private LinearLayout linear61;
	private LinearLayout linear135;
	private ListView placed_stu_listview;
	private ImageView imageview18;
	private TextView textview23;
	private LinearLayout _drawer_linear1;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear3;
	private TextView _drawer_textview1;
	private ImageView _drawer_imageview2;
	private TextView _drawer_logoutbtn;

	
	private DatabaseReference Companies_List = _firebase.getReference("Companies_List");
	private ChildEventListener _Companies_List_child_listener;
	private DatabaseReference Registered_Students = _firebase.getReference("Registered_Students");
	private ChildEventListener _Registered_Students_child_listener;
	private StorageReference Placed_Students_Docs = _firebase_storage.getReference("Placed_Students_Docs");
	private OnCompleteListener<Uri> _Placed_Students_Docs_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _Placed_Students_Docs_download_success_listener;
	private OnSuccessListener _Placed_Students_Docs_delete_success_listener;
	private OnProgressListener _Placed_Students_Docs_upload_progress_listener;
	private OnProgressListener _Placed_Students_Docs_download_progress_listener;
	private OnFailureListener _Placed_Students_Docs_failure_listener;
	private Intent Picker = new Intent(Intent.ACTION_GET_CONTENT);
	private AlertDialog.Builder d;
	private Intent p = new Intent();
	private DatabaseReference Placed_Students_File = _firebase.getReference("Placed_Students_File");
	private ChildEventListener _Placed_Students_File_child_listener;
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
	private DatabaseReference Placed_Students = _firebase.getReference("Placed_Students");
	private ChildEventListener _Placed_Students_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.placement_officer);
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
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		_drawer = (DrawerLayout) findViewById(R.id._drawer);ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(PlacementOfficerActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		basement = (LinearLayout) findViewById(R.id.basement);
		bottomnavigationview1 = (BottomNavigationView) findViewById(R.id.bottomnavigationview1);
		companies_list = (LinearLayout) findViewById(R.id.companies_list);
		upload_documents = (LinearLayout) findViewById(R.id.upload_documents);
		registered_students = (LinearLayout) findViewById(R.id.registered_students);
		placed_students = (LinearLayout) findViewById(R.id.placed_students);
		topbar = (LinearLayout) findViewById(R.id.topbar);
		listviewbase = (LinearLayout) findViewById(R.id.listviewbase);
		linear56 = (LinearLayout) findViewById(R.id.linear56);
		linear52 = (LinearLayout) findViewById(R.id.linear52);
		textview5 = (TextView) findViewById(R.id.textview5);
		textview34 = (TextView) findViewById(R.id.textview34);
		imageview16 = (ImageView) findViewById(R.id.imageview16);
		companies_listview = (ListView) findViewById(R.id.companies_listview);
		linear63 = (LinearLayout) findViewById(R.id.linear63);
		linear143 = (LinearLayout) findViewById(R.id.linear143);
		imageview19 = (ImageView) findViewById(R.id.imageview19);
		textview24 = (TextView) findViewById(R.id.textview24);
		linear386 = (LinearLayout) findViewById(R.id.linear386);
		imageview23 = (ImageView) findViewById(R.id.imageview23);
		linear385 = (LinearLayout) findViewById(R.id.linear385);
		linear390 = (LinearLayout) findViewById(R.id.linear390);
		linear389 = (LinearLayout) findViewById(R.id.linear389);
		linear388 = (LinearLayout) findViewById(R.id.linear388);
		textview31 = (TextView) findViewById(R.id.textview31);
		textview32 = (TextView) findViewById(R.id.textview32);
		choose_file_btn = (TextView) findViewById(R.id.choose_file_btn);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		progress_text = (TextView) findViewById(R.id.progress_text);
		filename_text = (TextView) findViewById(R.id.filename_text);
		uploadbtn = (TextView) findViewById(R.id.uploadbtn);
		linear37 = (LinearLayout) findViewById(R.id.linear37);
		linear134 = (LinearLayout) findViewById(R.id.linear134);
		regstudents_listview = (ListView) findViewById(R.id.regstudents_listview);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview18 = (TextView) findViewById(R.id.textview18);
		linear61 = (LinearLayout) findViewById(R.id.linear61);
		linear135 = (LinearLayout) findViewById(R.id.linear135);
		placed_stu_listview = (ListView) findViewById(R.id.placed_stu_listview);
		imageview18 = (ImageView) findViewById(R.id.imageview18);
		textview23 = (TextView) findViewById(R.id.textview23);
		_drawer_linear1 = (LinearLayout) _nav_view.findViewById(R.id.linear1);
		_drawer_linear2 = (LinearLayout) _nav_view.findViewById(R.id.linear2);
		_drawer_linear3 = (LinearLayout) _nav_view.findViewById(R.id.linear3);
		_drawer_textview1 = (TextView) _nav_view.findViewById(R.id.textview1);
		_drawer_imageview2 = (ImageView) _nav_view.findViewById(R.id.imageview2);
		_drawer_logoutbtn = (TextView) _nav_view.findViewById(R.id.logoutbtn);
		Picker.setType("*/*");
		Picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		d = new AlertDialog.Builder(this);
		auth = FirebaseAuth.getInstance();
		
		bottomnavigationview1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				final String _titleMenu = item.getTitle().toString();
				if (_titleMenu.equals("Companies")) {
						companies_list.setVisibility(View.VISIBLE);
					upload_documents.setVisibility(View.GONE);
					registered_students.setVisibility(View.GONE);
					placed_students.setVisibility(View.GONE);				
				}
				if (_titleMenu.equals("Upload Docs")) {
						companies_list.setVisibility(View.GONE);
					upload_documents.setVisibility(View.VISIBLE);
					registered_students.setVisibility(View.GONE);
					placed_students.setVisibility(View.GONE);				
				}
				if (_titleMenu.equals("Registered Students")) {
						companies_list.setVisibility(View.GONE);
					upload_documents.setVisibility(View.GONE);
					registered_students.setVisibility(View.VISIBLE);
					placed_students.setVisibility(View.GONE);				
				}
				if (_titleMenu.equals("Placed Students")) {
						companies_list.setVisibility(View.GONE);
					upload_documents.setVisibility(View.GONE);
					registered_students.setVisibility(View.GONE);
					placed_students.setVisibility(View.VISIBLE);				
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
		
		choose_file_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(Picker, REQ_CD_PICKER);
			}
		});
		
		uploadbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Placed_Students_Docs.child(filename).putFile(Uri.fromFile(new File(file))).addOnFailureListener(_Placed_Students_Docs_failure_listener).addOnProgressListener(_Placed_Students_Docs_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return Placed_Students_Docs.child(filename).getDownloadUrl();
					}}).addOnCompleteListener(_Placed_Students_Docs_upload_success_listener);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				p.setClass(getApplicationContext(), PostCompaniesActivity.class);
				startActivity(p);
			}
		});
		
		_Companies_List_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Companies_List.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						companies_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								companies_maplist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						companies_listview.setAdapter(new Companies_listviewAdapter(companies_maplist));
						((BaseAdapter)companies_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(companies_maplist);
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
				Companies_List.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						companies_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								companies_maplist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						companies_listview.setAdapter(new Companies_listviewAdapter(companies_maplist));
						((BaseAdapter)companies_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(companies_maplist);
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
		Companies_List.addChildEventListener(_Companies_List_child_listener);
		
		_Registered_Students_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Registered_Students.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						registered_stu_list = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								registered_stu_list.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						regstudents_listview.setAdapter(new Regstudents_listviewAdapter(registered_stu_list));
						((BaseAdapter)regstudents_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(registered_stu_list);
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
				Registered_Students.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						registered_stu_list = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								registered_stu_list.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						regstudents_listview.setAdapter(new Regstudents_listviewAdapter(registered_stu_list));
						((BaseAdapter)regstudents_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(registered_stu_list);
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
		Registered_Students.addChildEventListener(_Registered_Students_child_listener);
		
		_Placed_Students_Docs_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				progressbar1.setProgress((int)_progressValue);
				progress_text.setText(String.valueOf((long)(_progressValue)).concat("%"));
			}
		};
		
		_Placed_Students_Docs_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_Placed_Students_Docs_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				Toastmaker.showMessage(getApplicationContext(), "upload is successful");
				mp = new HashMap<>();
				mp.put("Download_Url", _downloadUrl);
				mp.put("file", file);
				Placed_Students_File.push().updateChildren(mp);
				mp.clear();
				progress_text.setText("0%");
				progressbar1.setProgress((int)0);
			}
		};
		
		_Placed_Students_Docs_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
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
		Placed_Students_File.addChildEventListener(_Placed_Students_File_child_listener);
		
		_Placed_Students_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Placed_Students.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						placed_student_list = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								placed_student_list.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						placed_stu_listview.setAdapter(new Placed_stu_listviewAdapter(placed_student_list));
						((BaseAdapter)placed_stu_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(placed_student_list);
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
				Placed_Students.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						placed_student_list = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								placed_student_list.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						placed_stu_listview.setAdapter(new Placed_stu_listviewAdapter(placed_student_list));
						((BaseAdapter)placed_stu_listview.getAdapter()).notifyDataSetChanged();
						Collections.reverse(placed_student_list);
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
		Placed_Students.addChildEventListener(_Placed_Students_child_listener);
		
		_drawer_logoutbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseAuth.getInstance().signOut();
				p.setClass(getApplicationContext(), LoginActivity.class);
				startActivity(p);
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
		bottomnavigationview1.getMenu().add("Upload Docs").setIcon(R.drawable.ic_cloud_upload_white);
		bottomnavigationview1.getMenu().add("Registered Students").setIcon(R.drawable.ic_assignment_white);
		bottomnavigationview1.getMenu().add("Placed Students").setIcon(R.drawable.ic_supervisor_account_white);
		bottomnavigationview1.setItemIconSize(55);
		Companies_List.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				companies_maplist = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						companies_maplist.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				companies_listview.setAdapter(new Companies_listviewAdapter(companies_maplist));
				((BaseAdapter)companies_listview.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		Registered_Students.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				registered_stu_list = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						registered_stu_list.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				regstudents_listview.setAdapter(new Regstudents_listviewAdapter(registered_stu_list));
				((BaseAdapter)regstudents_listview.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		
		uploadbtn.setVisibility(View.GONE);
		getSupportActionBar().hide();
		_drawerwidth();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_PICKER:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				file = _filePath.get((int)(0));
				filename = Uri.parse(file).getLastPathSegment();
				filename_text.setText(Uri.parse(file).getLastPathSegment());
				uploadbtn.setVisibility(View.VISIBLE);
			}
			else {
				
			}
			break;
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
				finishAffinity ();
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
				_v = _inflater.inflate(R.layout.campanies_customlist, null);
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
			final TextView d1 = (TextView) _v.findViewById(R.id.d1);
			final TextView d2 = (TextView) _v.findViewById(R.id.d2);
			final TextView d3 = (TextView) _v.findViewById(R.id.d3);
			final TextView d4 = (TextView) _v.findViewById(R.id.d4);
			final TextView d5 = (TextView) _v.findViewById(R.id.d5);
			
			if (companies_maplist.get((int)_position).containsKey("CompanyName")) {
				campany_name.setText(companies_maplist.get((int)_position).get("CompanyName").toString());
			}
			if (companies_maplist.get((int)_position).containsKey("RecruitmentDate")) {
				date.setText(companies_maplist.get((int)_position).get("RecruitmentDate").toString());
			}
			if (companies_maplist.get((int)_position).containsKey("Location")) {
				location.setText(companies_maplist.get((int)_position).get("Location").toString());
			}
			if (companies_maplist.get((int)_position).containsKey("Department1")) {
				d1.setText(companies_maplist.get((int)_position).get("Department1").toString());
			}
			else {
				d1.setVisibility(View.GONE);
			}
			if (companies_maplist.get((int)_position).containsKey("Department2")) {
				d2.setText(companies_maplist.get((int)_position).get("Department2").toString());
			}
			else {
				d2.setVisibility(View.GONE);
			}
			if (companies_maplist.get((int)_position).containsKey("Department3")) {
				d3.setText(companies_maplist.get((int)_position).get("Department3").toString());
			}
			else {
				d3.setVisibility(View.GONE);
			}
			if (companies_maplist.get((int)_position).containsKey("Department4")) {
				d4.setText(companies_maplist.get((int)_position).get("Department4").toString());
			}
			else {
				d4.setVisibility(View.GONE);
			}
			if (companies_maplist.get((int)_position).containsKey("Department5")) {
				d5.setText(companies_maplist.get((int)_position).get("Department5").toString());
			}
			else {
				d5.setVisibility(View.GONE);
			}
			
			return _v;
		}
	}
	
	public class Regstudents_listviewAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Regstudents_listviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_v = _inflater.inflate(R.layout.registered_students, null);
			}
			
			final LinearLayout linear3 = (LinearLayout) _v.findViewById(R.id.linear3);
			final LinearLayout linear10 = (LinearLayout) _v.findViewById(R.id.linear10);
			final LinearLayout linear5 = (LinearLayout) _v.findViewById(R.id.linear5);
			final LinearLayout linear9 = (LinearLayout) _v.findViewById(R.id.linear9);
			final TextView textview3 = (TextView) _v.findViewById(R.id.textview3);
			final TextView phno = (TextView) _v.findViewById(R.id.phno);
			final TextView textview6 = (TextView) _v.findViewById(R.id.textview6);
			final TextView email = (TextView) _v.findViewById(R.id.email);
			final ImageView imageview1 = (ImageView) _v.findViewById(R.id.imageview1);
			final TextView student_name = (TextView) _v.findViewById(R.id.student_name);
			final TextView companyname = (TextView) _v.findViewById(R.id.compname);
			final TextView date = (TextView) _v.findViewById(R.id.date);
			final TextView location = (TextView) _v.findViewById(R.id.location);
			
			if (registered_stu_list.get((int)_position).containsKey("StudentName")) {
				student_name.setText(registered_stu_list.get((int)_position).get("StudentName").toString());
			}
			if (registered_stu_list.get((int)_position).containsKey("PhoneNumber")) {
				phno.setText(registered_stu_list.get((int)_position).get("PhoneNumber").toString());
			}
			if (registered_stu_list.get((int)_position).containsKey("Email")) {
				email.setText(registered_stu_list.get((int)_position).get("Email").toString());
			}
			if (registered_stu_list.get((int)_position).containsKey("CompanyName")) {
				companyname.setText(registered_stu_list.get((int)_position).get("CompanyName").toString());
			}
			if (registered_stu_list.get((int)_position).containsKey("Location")) {
				location.setText(registered_stu_list.get((int)_position).get("Location").toString());
			}
			if (registered_stu_list.get((int)_position).containsKey("Date")) {
				date.setText(registered_stu_list.get((int)_position).get("Date").toString());
			}
			
			return _v;
		}
	}
	
	public class Placed_stu_listviewAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Placed_stu_listviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_v = _inflater.inflate(R.layout.placed_students_list, null);
			}
			
			final LinearLayout linear3 = (LinearLayout) _v.findViewById(R.id.linear3);
			final LinearLayout linear10 = (LinearLayout) _v.findViewById(R.id.linear10);
			final LinearLayout linear4 = (LinearLayout) _v.findViewById(R.id.linear4);
			final LinearLayout linear5 = (LinearLayout) _v.findViewById(R.id.linear5);
			final LinearLayout linear9 = (LinearLayout) _v.findViewById(R.id.linear9);
			final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
			final TextView branch = (TextView) _v.findViewById(R.id.branch);
			final TextView textview3 = (TextView) _v.findViewById(R.id.textview3);
			final TextView placedto = (TextView) _v.findViewById(R.id.placedto);
			final TextView textview6 = (TextView) _v.findViewById(R.id.textview6);
			final TextView packageperanum = (TextView) _v.findViewById(R.id.packageperanum);
			final ImageView imageview1 = (ImageView) _v.findViewById(R.id.imageview1);
			final TextView student_name = (TextView) _v.findViewById(R.id.student_name);
			
			if (placed_student_list.get((int)_position).containsKey("StudentName")) {
				student_name.setText(placed_student_list.get((int)_position).get("StudentName").toString());
			}
			if (placed_student_list.get((int)_position).containsKey("Branch")) {
				branch.setText(placed_student_list.get((int)_position).get("Branch").toString());
				
			}
			if (placed_student_list.get((int)_position).containsKey("CompanyName")) {
				placedto.setText(placed_student_list.get((int)_position).get("CompanyName").toString());
			}
			if (placed_student_list.get((int)_position).containsKey("Package")) {
				packageperanum.setText(placed_student_list.get((int)_position).get("Package").toString());
			}
			
			return _v;
		}
	}

	
}
