package com.gogoteam.wintecpathways;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;

public class PathwayActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathway);

        getWindow().setExitTransition(new Explode());
        ActionBar actionBar = getSupportActionBar();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                setContentView(R.layout.search_activity);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                setContentView(R.layout.activity_pathway);
                return true;
            }
        };

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(onActionExpandListener);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
        }
        return false;
    }
}
