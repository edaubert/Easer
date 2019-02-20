/*
 * Copyright (c) 2016 - 2018 Rui Zhao <renyuneyun@gmail.com>
 *
 * This file is part of Easer.
 *
 * Easer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Easer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Easer.  If not, see <http://www.gnu.org/licenses/>.
 */

package ryey.easer.core.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.orhanobut.logger.Logger;

import java.util.List;

import ryey.easer.R;
import ryey.easer.SettingsHelper;
import ryey.easer.commons.local_plugin.PluginDef;
import ryey.easer.permissions.PermissionActivity;
import ryey.easer.plugins.LocalPluginRegistry;

public class PermissionOutlineFragment extends Fragment {

    Button mButton;

    public PermissionOutlineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permission_outline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mButton = view.findViewById(R.id.button_more);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PermissionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasAllRequiredPermissions()) {
            getView().setVisibility(View.GONE);
        } else {
            getView().setVisibility(View.VISIBLE);
        }
    }

    boolean hasAllRequiredPermissions() {
        final boolean logging = SettingsHelper.logging(getContext());
        boolean satisfied = true;
        for (Object obj_plugin : LocalPluginRegistry.getInstance().all().getEnabledPlugins(getContext())) {
            PluginDef plugin = (PluginDef) obj_plugin;
            if (!plugin.checkPermissions(getContext())) {
                Logger.d("Permission for plugin <%s> not satisfied", plugin.id());
                if (!logging)
                    return false;
                satisfied = false;
            }
        }
        return satisfied;
    }
}
