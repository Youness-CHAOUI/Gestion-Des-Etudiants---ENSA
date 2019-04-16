package chaoui.cy_15.www.gestiondabsencecy_15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageButton btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btn_start = (ImageButton)findViewById(R.id.ibtn_start);
        btn_start.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        startActivity(new Intent(this, classesActivity.class));
        this.finish();
    }
}
