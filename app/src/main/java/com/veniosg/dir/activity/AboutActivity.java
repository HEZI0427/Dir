/*
 * Copyright (C) 2014 George Venios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.veniosg.dir.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.veniosg.dir.R;
import com.veniosg.dir.util.Logger;
import com.veniosg.dir.view.CheatSheet;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupToolbar();

        try {
            ((TextView) findViewById(R.id.dirVersion)).setText(
                    getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.log(e);
        }

        // Click listeners
        findViewById(R.id.topText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebLink("http://veniosg.github.io/Dir/");
            }
        });
        findViewById(R.id.middleText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebLink("https://google.com/+GeorgeVenios");
            }
        });
        findViewById(R.id.gplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebLink("market://details?id=com.veniosg.dir");
            }
        });
        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.about_shareSubject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "http://play.google.com/store/apps/details?id=com.veniosg.dir");

                startActivity(Intent.createChooser(intent, getString(R.string.about_share) + " " + getString(R.string.app_name)));
            }
        });
        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "dir-support@googlegroups.com", null));
//                StringBuffer subjectBuilder = new StringBuffer(label).append(" (v")
//                        .append(versionName)
//                        .append(")");
//                i.putExtra(Intent.EXTRA_SUBJECT, subjectBuilder.toString());

                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(AboutActivity.this,
                            R.string.send_not_available, Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.beta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebLink("https://plus.google.com/communities/101972691290551957751");
            }
        });
        findViewById(R.id.contribute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebLink("https://github.com/veniosg/dir");
            }
        });

        // CheatSheets
        CheatSheet.setup(findViewById(R.id.gplay));
        CheatSheet.setup(findViewById(R.id.beta));
        CheatSheet.setup(findViewById(R.id.contribute));
        CheatSheet.setup(findViewById(R.id.contact));
        CheatSheet.setup(findViewById(R.id.share));
    }

    private void openWebLink(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Logger.log(e);
            Toast.makeText(AboutActivity.this,
                    R.string.application_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onMenuItemSelected(featureId, item);
        }
    }
}
