package ryey.easer.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.Comparator;
import java.util.List;

import ryey.easer.R;
import ryey.easer.commons.local_plugin.PluginDef;
import ryey.easer.commons.local_plugin.StorageData;
import ryey.easer.core.ui.setting.SettingsActivity;
import ryey.easer.plugins.LocalPluginRegistry;

public class PermissionActivity extends AppCompatActivity {

    private RecyclerView pluginView;
    private PluginPermissionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pluginView = findViewById(R.id.plugins_permissions_view);

        pluginView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pluginView.setLayoutManager(layoutManager);

        listPlugins();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void listPlugins() {
        List<PluginDef> plugins = LocalPluginRegistry.getInstance().all().getAllPlugins();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            plugins.sort(new Comparator<PluginDef>() {
                @Override
                public int compare(PluginDef o1, PluginDef o2) {
                    if (!o1.checkPermissions(getApplicationContext())) {
                        return -1;
                    } else if (!o2.checkPermissions(getApplicationContext())) {
                        return 1;
                    } else {
                        return o1.id().compareToIgnoreCase(o2.id());
                    }
                }
            });
        }

        adapter = new PluginPermissionAdapter(plugins, this);
        pluginView.setAdapter(adapter);
        pluginView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        adapter.notifyItemChanged(requestCode);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        //        adapter.notifyItemChanged(requestCode);
        adapter.notifyDataSetChanged();
    }
}
