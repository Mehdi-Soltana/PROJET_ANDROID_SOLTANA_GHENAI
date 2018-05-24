package com.example.mehdi.psg;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayerHolder> {

    private JSONArray playersArray;

    public PlayersAdapter(JSONArray array){
        this.playersArray = array;
    }

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.mehdi.psg.R.layout.rv_player_element, parent, false);
        PlayerHolder holder = new PlayerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PlayerHolder holder, int position) {
        try {
            String name = playersArray.getJSONObject(position).getString("name");
            String numero = playersArray.getJSONObject(position).getString("jerseyNumber");
            String pos = playersArray.getJSONObject(position).getString("position").toString();
            String contrat = playersArray.getJSONObject(position).getString("contractUntil");
            String nation = playersArray.getJSONObject(position).getString("nationality");
            String date =playersArray.getJSONObject(position).getString("dateOfBirth");
            holder.changeText(name,numero,pos,contrat,nation,date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return playersArray.length();
    }

    public void setNewPlayer(JSONArray array){
        this.playersArray = array;
        notifyDataSetChanged();
    }

    class PlayerHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_numero, tv_pos, tv_contrat, tv_nation, tv_date;

        public PlayerHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(com.example.mehdi.psg.R.id.rv_element_name);
            tv_contrat = itemView.findViewById(com.example.mehdi.psg.R.id.rv_element_contrat);
            tv_numero = itemView.findViewById(com.example.mehdi.psg.R.id.rv_element_numero);
            tv_pos = itemView.findViewById(com.example.mehdi.psg.R.id.rv_element_poste);
            tv_nation = itemView.findViewById(com.example.mehdi.psg.R.id.rv_element_nation);
            tv_date = itemView.findViewById(com.example.mehdi.psg.R.id.rv_element_date);
        }

        private void changeText(String name, String numero, String pos, String contrat, String nation, String date){
            tv_name.setText(name);
            tv_numero.setText(numero);
            tv_contrat.setText(contrat);
            tv_nation.setText(nation);
            tv_pos.setText(pos);
            tv_date.setText(date);
        }
    }



}
