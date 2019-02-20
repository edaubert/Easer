package ryey.easer.permissions;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ryey.easer.R;
import ryey.easer.commons.local_plugin.PluginDef;

public class PluginPermissionAdapter extends RecyclerView.Adapter<PermissionsHolder> {
    private List<PluginDef> plugins;
    private Activity activity;

    public PluginPermissionAdapter(List<PluginDef> plugins, Activity activity) {
        this.plugins = plugins;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PermissionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plugin_permission_layout, parent, false);
        return new PermissionsHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionsHolder holder, int position) {
        holder.bind(plugins.get(position), position);
    }

    @Override
    public int getItemCount() {
        return plugins.size();
    }
}
