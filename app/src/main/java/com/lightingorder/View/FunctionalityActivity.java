package com.lightingorder.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lightingorder.View.Adapters.FunctionalityAdapter;
import com.lightingorder.Controller.UserController;
import com.lightingorder.R;
import com.lightingorder.StdTerms;
import com.lightingorder.Model.Functionality;

import java.util.ArrayList;

public class FunctionalityActivity extends AppCompatActivity {

    ListView lv_function;
    ArrayList<Functionality> funs;
    UserController user_contr = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roles_selection);
        funs = new ArrayList<Functionality>();

        for(String r : user_contr.getHashRuoli_Proxy().keySet()){

            if(r.equals(StdTerms.roles.Bar.name())){

                funs.add(new Functionality("Visualizza Oridnazioni Bar",
                                            Color.GREEN,
                                            StdTerms.useCases.VisualizzaOrdinazioniBar.name()));

            } else if (r.equals(StdTerms.roles.Accoglienza.name())){

                funs.add(new Functionality("Aggiorna Stato Tavolo",
                                            Color.BLUE,
                                            StdTerms.useCases.AggiornaStatoTavolo.name()));

            } else if (r.equals(StdTerms.roles.Cameriere.name())){

                funs.add(new Functionality("Crea Ordinazione",
                                            Color.WHITE,
                                            StdTerms.useCases.CreaOrdinazione.name()));

            } else if (r.equals(StdTerms.roles.Cucina.name())){

                funs.add(new Functionality("Visualizza Ordinazioni Cucina",
                                                Color.DKGRAY,
                                                StdTerms.useCases.VisualizzaOrdinazioniCucina.name()));

            } else if (r.equals(StdTerms.roles.Forno.name())){

                funs.add(new Functionality("Visualizza Ordinazioni Forno",
                                            Color.YELLOW,
                                            StdTerms.useCases.VisualizzaOrdinazioniForno.name()));
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

                //Retrieve the ID of the Functionality ==> Use case name
                String use_case =  f.getID();

                //Retrieve the role needed for this use case
                String role_needed = StdTerms.UC_Role.get(use_case);

                //Retrieve the url of the proxy in charge to manage the request from users with this role
                String url_proxy = user_contr.getHashRuoli_Proxy().get(role_needed);

                if(use_case.equals(StdTerms.useCases.CreaOrdinazione.name()) ||
                        use_case.equals(StdTerms.useCases.AggiornaStatoTavolo.name())) {

                    Intent i = new Intent(getApplicationContext(), TableActivity.class);
                    i.putExtra("ProxyAddress", url_proxy);
                    i.putExtra("ruolo", role_needed);
                    startActivity(i);
                }
                else if(use_case.equals(StdTerms.useCases.VisualizzaOrdinazioniBar.name()) ||
                        use_case.equals(StdTerms.useCases.VisualizzaOrdinazioniCucina.name()) ||
                        use_case.equals(StdTerms.useCases.VisualizzaOrdinazioniForno.name()) ){

                    //TODO Aggiungere activty per realizzatori

                    Intent i = new Intent(getApplicationContext(), TableActivity.class);
                    i.putExtra("proxyAddress", url_proxy);
                    i.putExtra("ruolo", role_needed);
                    startActivity(i);
                }
            }
        });
    }

}
