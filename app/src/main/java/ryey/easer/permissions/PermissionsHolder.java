package ryey.easer.permissions;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ryey.easer.R;
import ryey.easer.commons.local_plugin.PluginDef;

public class PermissionsHolder extends RecyclerView.ViewHolder {
    private Activity activity;

    private TextView pluginName;
    private ImageButton pluginStatus;
    private Button pluginGrantPermissionButton;
    private int position;

    public PermissionsHolder(final View itemView, final Activity activity) {
        super(itemView);
        this.activity = activity;

        pluginName = itemView.findViewById(R.id.plugin_permission_name);
        pluginStatus = itemView.findViewById(R.id.plugin_permission_status);
        pluginGrantPermissionButton = itemView.findViewById(R.id.plugin_permission_action);

    }


    public void bind(final PluginDef plugin, final int position) {
        this.position = position;
        int id = Resources.getSystem().getIdentifier("operation_" + plugin.id(), null, null);
        if (id == 0) {
            id = Resources.getSystem().getIdentifier("event_" + plugin.id(), null, null);
        }
        if (id == 0) {
            id = Resources.getSystem().getIdentifier("condition_" + plugin.id(), null, null);
        }
        String name = plugin.id();
        if (id != 0) {
            name = Resources.getSystem().getString(id);
        }

        pluginName.setText(name);
        if (plugin.checkPermissions(activity.getApplicationContext())) {
            pluginStatus.setImageResource(R.drawable.ic_status_positive);
            pluginGrantPermissionButton.setVisibility(View.INVISIBLE);
        } else {
            pluginStatus.setImageResource(R.drawable.ic_status_negative);
            pluginGrantPermissionButton.setVisibility(View.VISIBLE);
            pluginGrantPermissionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plugin.requestPermissions(activity, position);
                }
            });
        }

    }

}
