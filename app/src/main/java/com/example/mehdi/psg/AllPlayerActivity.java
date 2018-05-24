package com.example.mehdi.psg;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AllPlayerActivity extends AppCompatActivity {

    private final String TAG = "AllPlayerActivity";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mehdi.psg.R.layout.activity_all_player);

        recyclerView = findViewById(com.example.mehdi.psg.R.id.rv_current);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        PlayersAdapter pa = new PlayersAdapter(getCurrentPlayersFromFile());

        recyclerView.setAdapter(pa);

        GetAllPlayersServices.startActionPlayers(this);
        IntentFilter intentFilter = new IntentFilter(PLAYERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new PlayersUpdate(), intentFilter);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.ic_dialog_info);
        mBuilder.setContentTitle("PSG INFO");
        mBuilder.setContentText("Il y a : " + getCurrentPlayersFromFile().length() + " joueurs dans l'équipe");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(com.example.mehdi.psg.R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case com.example.mehdi.psg.R.id.main_page :
                AlertDialog.Builder builder = new AlertDialog.Builder(AllPlayerActivity.this);
                builder.setTitle("Retour en arrière");
                builder.setMessage("Etes-vous sûr de vouloir retourner au menu ?");
                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(AllPlayerActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });

                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "On reste ici", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static final String PLAYERS_UPDATE = "com.example.mehdi.PSG.PLAYERS_UPDATE";

    public class PlayersUpdate extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Intent : " + getIntent().getAction());
            JSONArray array = getCurrentPlayersFromFile();
            PlayersAdapter pa = (PlayersAdapter)  recyclerView.getAdapter();
            pa.setNewPlayer(array);
            Log.d("count", Integer.toString(array.length()));
        }



    }

    public JSONArray getCurrentPlayersFromFile(){
        try {

            InputStream is = new FileInputStream(getCacheDir() + "/" + "all_players.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer,"UTF-8")).getJSONArray("players");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

}
