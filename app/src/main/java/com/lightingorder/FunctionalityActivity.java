package com.lightingorder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FunctionalityActivity extends AppCompatActivity {

    ListView lv_function;
    ArrayList<Functionality> funs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roles_selection);
        funs = new ArrayList<Functionality>();

        ArrayList<String> roles = new ArrayList<String>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roles = extras.getStringArrayList("Ruoli");
            //The key argument here must match that used in the other activity
        }

        for(String r : roles){
            if(r.equals(StdTerms.roles.Bar.name())){

            } else if (r.equals(StdTerms.roles.Accoglienza.name())){
                funs.add(new Functionality("Aggiorna Stato Tavolo", Color.GREEN, StdTerms.use_cases.AggiornaStatoTavolo.name()));
            } else if (r.equals(StdTerms.roles.Cameriere.name())){
                funs.add(new Functionality("Crea Ordinazione", Color.GREEN, StdTerms.use_cases.CreaOrdinazione.name()));
            } else if (r.equals(StdTerms.roles.Cucina.name())){

            } else if (r.equals(StdTerms.roles.Forno.name())){

            }
        }
        Toast t = Toast.makeText(this, "", Toast.LENGTH_LONG);
        FunctionalityAdapter adapter = new FunctionalityAdapter(this, funs);
        lv_function = (ListView) findViewById(R.id.function_list);
        lv_function.setAdapter(adapter);

        lv_function.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Functionality f = funs.get(position);
                if(f.getID().equals(StdTerms.use_cases.AggiornaStatoTavolo.name())){
                    Intent i = new Intent(getApplicationContext(), TableActivity.class);
                    startActivity(i);
                }
            }
        });
    }

}
