package ph.edu.usc.database_surigao;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnView, btnAdd, btnDelete, btnUpdate;
    EditText editUsername, editPassword, editDelete, editCurrentName, editNewName;
    myDBAdapter helper;
    TextView displays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnView = findViewById(R.id.btnView);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editDelete = findViewById(R.id.editDelete);
        editCurrentName = findViewById(R.id.editCurrentName);
        editNewName = findViewById(R.id.editNewName);

        helper = new myDBAdapter(this);

        displays = findViewById(R.id.displays);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(v);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(v);
                viewData(v);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(v);
                viewData(v);
            }
        });

    }

    public void addUser(View view){
        String name = editUsername.getText().toString();
        String pass = editPassword.getText().toString();
        if(name.isEmpty() || pass.isEmpty()){
            Toast.makeText(getApplicationContext(),
            "name and password must not be empty",
            Toast.LENGTH_SHORT).show();
        }else{
            long id = helper.insertData(name, pass);
            if(id <= 0){
                Toast.makeText(getApplicationContext(),
                        "Data was not successfully save!",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),
                        "Data was successfully saved!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void viewData(View view){
        String data = helper.getData();
        if (data.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
            displays.setVisibility(View.GONE);
        } else {
            displays.setVisibility(View.VISIBLE);
            displays.setText(data);
            Toast.makeText(getApplicationContext(), "Data loaded!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData(View view){
        String nname = editNewName.getText().toString();
        String cname = editCurrentName.getText().toString();
        if (nname.isEmpty() || cname.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Enter data!",
                    Toast.LENGTH_LONG).show();
        } else {
            int uuname = helper.updateData(cname, nname);
            if (uuname <= 0) {
                Toast.makeText(getApplicationContext(),
                        "Unsuccessful!",
                        Toast.LENGTH_LONG).show();
                    editNewName.setText("");
                    editCurrentName.setText("");
            } else {
                Toast.makeText(getApplicationContext(),
                        "Data has been updated!",
                        Toast.LENGTH_LONG).show();
                displays.setText("");
            }
        }
    }

    public void deleteData(View view) {
        String uname = editDelete.getText().toString();
        if (uname.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Enter data!",
                    Toast.LENGTH_LONG).show();
        } else {
            int uuname = helper.deleteData(uname);
            if (uuname <= 0) {
                Toast.makeText(getApplicationContext(),
                        "Unsuccessful!",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Data has been deleted!",
                        Toast.LENGTH_LONG).show();
                editDelete.setText("");
            }
        }
    }
}