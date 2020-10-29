package ru.netology.dr_note_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.widget.Toast;

import ru.netology.dr_note_v3.databinding.ActivityMainBinding;
import ru.netology.dr_note_v3.utils.Constants;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding maBinding;
    public NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        maBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(maBinding.getRoot());
        Constants.APP_ACTIVITY = this;

        Toolbar appToolbar = maBinding.appToolbar;
        appToolbar.setTitle(R.string.name_app);
        appToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryText));
        appToolbar.setTitleTextAppearance(this, R.style.ToolBarTitleTextAppearance);
        setSupportActionBar(appToolbar);

        navController = Navigation.findNavController(Constants.APP_ACTIVITY, R.id.navi_host);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        maBinding = null;
    }
}