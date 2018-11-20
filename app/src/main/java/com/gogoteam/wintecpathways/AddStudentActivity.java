package com.gogoteam.wintecpathways;



import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Student;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {


    private TextInputEditText nameText, studentidText, emailText, dateText,programmeText,moduleSelected;
    private Button cancelBtn, saveBtn;
    private Spinner pathwaySpinner;
    DBHandler dbHandler;
    Student student;
    Bundle studentInfo;
    String studentID;
    String pathwayText;
    private List<Student> studentList;
    int test = 0;

    private ImageView studentImageView;
    private static final int SELECT_PHOTO = 1;

    private boolean hasImageChanged = false;

    Bitmap thumbnail;






    //private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String [] pathways =
            {" SELECT A PATHWAY ",
            "Network Engineering",
            "Software Engineering",
            "Database Architecture",
            "Multimedia and Web Development"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Log.i("chrisita", "oncreate  " + studentID);
        studentImageView = (ImageView)findViewById(R.id.studentImageView);
        nameText = findViewById(R.id.nameText);
        studentidText = findViewById(R.id.studentidText);
        emailText = findViewById(R.id.emailText);
        programmeText = findViewById(R.id.programmeText);
        //phoneText = (EditText) findViewById(R.id.phoneText);
        dateText = findViewById(R.id.dateText);
        cancelBtn = findViewById(R.id.cancelBtn);
        saveBtn = findViewById(R.id.saveBtn);




        //prepopulate pathways
        pathwaySpinner = findViewById(R.id.pathwayID);
        pathwaySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pathways));
        pathwaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                test = position;

                Log.i("chrisita", "set position =  "+ position + " " +pathways[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler = new DBHandler(AddStudentActivity.this,null,null,1);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Contact Image"), 1);

            }
        });

        //student database
        student = new Student();
        dbHandler = new DBHandler(this, null, null, 1);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent(v);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelStudent(v);
            }
        });

        // check if it's student info
        studentInfo = getIntent().getExtras();
        if (studentInfo == null){
            return;
        }

        // show student details
        studentID = studentInfo.getString("studentInfo");
        showStudentInfo();
    }
    public void showStudentInfo()
    {
        // get the module code from module view ( module list)
        //String[] pathway = {"Network Engineering", "Software Engineering", "Database Architecture", "Multimedia and Web Development"};
        student.setSID(studentID);




        Log.i("chrisita", "showStudentInfo  " + studentID);
        // get module deatils from DB by sending module code
        studentList = dbHandler.searchStudent(student);
        Log.i("chrisita", "showStudentInfo  " + studentList.get(0).getSName() + " " +
                studentList.get(0).getSID() + " " +
                studentList.get(0).getEmail() + " " +
                studentList.get(0).getSpecialisation() + " " +
                studentList.get(0).getDate_Enrolled() + " " +
                studentList.get(0).getSImage());

        pathwayText = studentList.get(0).getSpecialisation();
        int index = 0;

        for(int i = 0; i<pathways.length; i++)
        {
            if(pathwayText.equals(pathways[i]))
            {
                index = i;
            }
        }
   /*     byte[] Image;
        Image = studentList.get(studentList.getColumnIndex("img_str"));
        BitmapFactory.Options options = new BitmapFactory.Options();
        decodedByte = BitmapFactory.decodeByteArray(Image, 0,Image.length, options);
        arr_img.add(decodedByte);*/

        //private String name, studentid, email, date,programme,pathwayText,moduleSelected;
        nameText.setText(studentList.get(0).getSName());
        studentidText.setText(studentList.get(0).getSID());
        emailText.setText(studentList.get(0).getEmail());
        dateText.setText(studentList.get(0).getDate_Enrolled());
        programmeText.setText(studentList.get(0).getProgramme());
        //studentImageView.setImageBitmap(studentList.get(0).getSImage());
        //moduleSelected.setText(studentList.get(0).getModules());
        pathwaySpinner.setSelection(index,true);

        //programme.setText(studentList.get(0).getProgramme());
        //pathwayText.setText(studentList.get(0).getSpecialisation());
        //moduleSelected.setText((CharSequence) studentList.get(0).getModules());


        // log for testing DB, these details should be shown on layout, GOGO Juan!!
        Log.i("chrisita", "showStudentInfo  " + studentList.get(0).getSName() + " " +
                studentList.get(0).getSID() + " " +
                studentList.get(0).getEmail() + " " + index + "   " +
                studentList.get(0).getDate_Enrolled() + " ");
    }

    // when save button is clicked
    public void saveStudent(View v)
    {
        // confirmation for saving the module details
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to save?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editStudentinfo();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog, int id) {
                        // delete dialog is user click No
                        dialog.cancel();
                    }
                });
        AlertDialog disc = builder.create();
        disc.show();
        disc.getWindow().setBackgroundDrawableResource(R.color.orangecard);

        TextView messageText = (TextView)disc.findViewById( android.R.id.message );
        messageText.setGravity( Gravity.CENTER_HORIZONTAL );

    }

    // when cancel button is clicked
    public void cancelStudent(View v) {
        // go back to Module View (List)
        finish();

    }

    public void editStudentinfo() {

        boolean saveSuccess = true;


        Bitmap bitmap = ((BitmapDrawable)studentImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        student.setSName(nameText.getText().toString());
        student.setSID(studentidText.getText().toString());
        student.setEmail(emailText.getText().toString());
        student.setDate_Enrolled(dateText.getText().toString());
        student.setProgramme(programmeText.getText().toString());
        student.setSpecialisation(pathways[test]);
        student.setSImage(data.toString());
        Log.i("chrisita", "editStudentinfo  " + pathways[test]);


        //saveSuccess = dbHandler.addStudent(student);
        if (studentID == null) {
            Log.i("chrisita", "studentID  null");
            saveSuccess = dbHandler.addStudent(student);
            if (saveSuccess == false) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("The student info already exist, please use edit function instead of add function.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                startActivity(new Intent(AddStudentActivity.this, StaffStudent.class));
                            }
                        });
                AlertDialog disc = builder.create();
                disc.show();
            } else {
                Toast.makeText(this, "Student Info saved!", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            Log.i("chrisita", "studentID not  null ");
            dbHandler.updateStudent(student);
            finish();
        }
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            if(reqCode == 1){
                try{
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    //studentImageView.setImageURI(data.getData());
                    studentImageView.setImageBitmap(selectedImage);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }

            }
        }
    }


}
