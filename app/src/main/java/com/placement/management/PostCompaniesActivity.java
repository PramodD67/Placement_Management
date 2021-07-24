package com.placement.management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.*;
import android.widget.*;
import android.content.*;
import android.util.*;

import java.util.*;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;

public class PostCompaniesActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear479;
	private TextView textview1;
	private LinearLayout linear480;
	private TextView textview2;
	private LinearLayout linear481;
	private LinearLayout linear482;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private EditText cname;
	private EditText date;
	private EditText location;
	private TextView textview701;
	private CheckBox checkbox1;
	private CheckBox checkbox2;
	private CheckBox checkbox4;
	private CheckBox checkbox3;
	private CheckBox checkbox5;
	private TextView publishbtn;
	
	private DatabaseReference Companies_List = _firebase.getReference("Companies_List");
	private ChildEventListener _Companies_List_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.post_companies);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear479 = (LinearLayout) findViewById(R.id.linear479);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear480 = (LinearLayout) findViewById(R.id.linear480);
		textview2 = (TextView) findViewById(R.id.textview2);
		linear481 = (LinearLayout) findViewById(R.id.linear481);
		linear482 = (LinearLayout) findViewById(R.id.linear482);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		cname = (EditText) findViewById(R.id.cname);
		date = (EditText) findViewById(R.id.date);
		location = (EditText) findViewById(R.id.location);
		textview701 = (TextView) findViewById(R.id.textview701);
		checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
		checkbox4 = (CheckBox) findViewById(R.id.checkbox4);
		checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
		checkbox5 = (CheckBox) findViewById(R.id.checkbox5);
		publishbtn = (TextView) findViewById(R.id.publishbtn);
		
		publishbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((cname.getText().toString().length() > 0) && ((date.getText().toString().length() > 0) && (location.getText().toString().length() > 0))) {
					map = new HashMap<>();
					map.put("CompanyName", cname.getText().toString());
					map.put("RecruitmentDate", date.getText().toString());
					map.put("Location", location.getText().toString());
					if (checkbox1.isChecked()) {
						map.put("Department1", "CSE");
					}
					if (checkbox2.isChecked()) {
						map.put("Department2", "ISE");
					}
					if (checkbox3.isChecked()) {
						map.put("Department3", "ECE");
					}
					if (checkbox4.isChecked()) {
						map.put("Department4", "ME");
					}
					if (checkbox5.isChecked()) {
						map.put("Department5", "EEE");
					}
					Companies_List.push().updateChildren(map);
					map.clear();
					Toastmaker.showMessage(getApplicationContext(), "Published");
					cname.setText("");
					date.setText("");
					location.setText("");
				}
				else {
					
				}
			}
		});
		
		_Companies_List_child_listener = new ChildEventListener() {
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
		Companies_List.addChildEventListener(_Companies_List_child_listener);
	}
	private void initializeLogic() {
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}

	

	
}
