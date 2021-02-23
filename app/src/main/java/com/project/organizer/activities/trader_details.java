package com.project.organizer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.organizer.R;
import com.project.organizer.database.DatabaseService;
import com.project.organizer.helper.OverviewService;
import com.project.organizer.helper.adapter.TraderListViewAdapter;
import com.project.organizer.model.shipper.ManualShipper;
import com.project.organizer.model.trader.ManualTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;
import com.project.organizer.services.LoadOrderService;

import java.util.ArrayList;

public class trader_details extends AppCompatActivity {
    Trader currentTrader;
    boolean isCreateMode = false;
    boolean isManagedMode = false;
    EditText text_trader_name;
    EditText text_trader_mail;
    TextView tv_trader_name;
    ListView trader_listview;
    TextView tv_trader_mail;
    TextView tv_state;
    LinearLayout ll_state;

    CheckBox cb_final;
    TextView tv_state_possible;
    EditText text_new_state_trader;
    Button btn_add_state_trader;
    Button btn_remove_state_trader;
    TraderListViewAdapter lvAdapter;
    String identifier;
    EditText text_username;
    EditText text_password;
    TextView tv_username;
    TextView tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader_details);

        Button btn_new_trader = findViewById(R.id.btn_new_trader);
        text_trader_name = findViewById(R.id.text_trader_name);
        text_trader_mail = findViewById(R.id.text_trader_mail);
        tv_trader_name = findViewById(R.id.tv_trader_name);
        tv_trader_mail = findViewById(R.id.tv_trader_mail);
        tv_state = findViewById(R.id.tv_state);
        ll_state = findViewById(R.id.ll_state);
        trader_listview = findViewById(R.id.trader_listview);
        cb_final = findViewById(R.id.cb_final);
        tv_state_possible = findViewById(R.id.tv_state_possible);
        text_new_state_trader = findViewById(R.id.text_new_state_trader);
        btn_add_state_trader = findViewById(R.id.btn_add_state_trader);
        btn_remove_state_trader = findViewById(R.id.btn_remove_state_trader);

        text_username = findViewById(R.id.text_username);
        text_password = findViewById(R.id.text_password);
        tv_username = findViewById(R.id.tv_username);
        tv_password = findViewById(R.id.tv_password);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String action = extras.getString("ACTION");
            if (action == null || action.equals("CREATE")) {
                isCreateMode = true;
            } else {
                identifier = extras.getString("IDENTITY");
                if (identifier != null) {
                    isCreateMode = false;
                } else {
                    isCreateMode = true;
                }
            }
        } else {
            isCreateMode = true;
        }

        loadData();

        btn_new_trader.setOnClickListener(this::saveData);
        btn_add_state_trader.setOnClickListener(this::addState);
        btn_remove_state_trader.setOnClickListener(this::removeState);
    }

    private void addState(View view) {
        OrderStatus newState;
        if (cb_final.isChecked()) {
            newState = new OrderStatus(0, text_new_state_trader.getText().toString(), true);
        } else {
            newState = new OrderStatus(0, text_new_state_trader.getText().toString(), false);
        }
        text_new_state_trader.setText("");
        cb_final.setChecked(false);
        lvAdapter.addList(newState);
    }

     private void removeState(View view) {
         lvAdapter.removeList();
    }

    public boolean isValidEmailAddress(String email) {
        if(email.isEmpty()) {
            return true;
        }

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private void saveData(View view) {
        if (text_trader_name.getText().toString() != "") {
            if (isValidEmailAddress(text_trader_mail.getText().toString())) {
                if (isCreateMode) {
                    currentTrader = new ManualTrader(0, text_trader_name.getText().toString(), text_trader_mail.getText().toString(), lvAdapter.getList());
                } else {
                    currentTrader = new ManualTrader(currentTrader.getTraderId(), text_trader_name.getText().toString(), text_trader_mail.getText().toString(), lvAdapter.getList());
                }
                try {
                    DatabaseService.getInstance().saveTrader(currentTrader);
                    Intent i = new Intent(trader_details.this, overview_trader.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(this, R.string.save_error, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, R.string.false_mail, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, R.string.missing_name, Toast.LENGTH_LONG).show();
        }
    }

    private void loadData() {
        if (isCreateMode) {
            setTitle(R.string.headline_trader_create);

            lvAdapter = new TraderListViewAdapter(this, new ArrayList<>());
            trader_listview.setAdapter(lvAdapter);
            trader_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    lvAdapter.setPos(position);
                }
            });
        } else {
            try {
                currentTrader = OverviewService.getTrader(Integer.parseInt(identifier));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (currentTrader != null) {
                if (currentTrader.isApi()) {
                    isManagedMode = true;
                    setTitle(R.string.headline_trader_show);
                    loadManagedMode();
                } else {
                    isManagedMode = false;
                    setTitle(R.string.headline_trader_change);
                    loadManualMode();
                }
            } else {
                Toast.makeText(this, R.string.load_trader_failure, Toast.LENGTH_SHORT).show();
                setTitle(R.string.headline_trader_create);
                loadManualMode();
            }
        }
        setVisibleMode();
    }

    private void loadManualMode() {
        text_trader_name.setText(currentTrader.getName());
        text_trader_mail.setText(currentTrader.getMail());

        lvAdapter = new TraderListViewAdapter(this, (ArrayList<OrderStatus>) ((ManualTrader) currentTrader).getOrderStatusList());
        trader_listview.setAdapter(lvAdapter);
        trader_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                lvAdapter.setPos(position);
            }
        });
    }

    private void loadManagedMode() {
        tv_trader_name.setText(currentTrader.getName());
        tv_trader_mail.setText(currentTrader.getMail());
    }



    private void setVisibleMode() {
        if (isManagedMode){
            text_trader_name.setVisibility(View.GONE);
            text_trader_mail.setVisibility(View.GONE);
            tv_state.setVisibility(View.GONE);
            ll_state.setVisibility(View.GONE);
            cb_final.setVisibility(View.GONE);
            tv_state_possible.setVisibility(View.GONE);
            text_new_state_trader.setVisibility(View.GONE);
            btn_add_state_trader.setVisibility(View.GONE);
            btn_remove_state_trader.setVisibility(View.GONE);
        }
        else {
            tv_trader_name.setVisibility(View.GONE);
            tv_trader_mail.setVisibility(View.GONE);
            text_username.setVisibility(View.GONE);
            text_password.setVisibility(View.GONE);
            tv_username.setVisibility(View.GONE);
            tv_password.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_trader, menu);
        inflater.inflate(R.menu.toolbar_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.reload_orders_single) {
            if (currentTrader == null) {
                Toast.makeText(trader_details.this, R.string.no_save_trader, Toast.LENGTH_SHORT).show();
            } else if (currentTrader.isApi() == false) {
                Toast.makeText(trader_details.this, R.string.not_managed_trader, Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(trader_details.this, LoadOrderService.class);
                i.putExtra("IDENTIFIER", currentTrader.getIdentifier());
                startService(i);
            }
        } else if (id == R.id.delete) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        if (currentTrader != null) {
                            try {
                                currentTrader.setActive(false);
                                DatabaseService.getInstance().saveTrader(currentTrader);
                                Intent i = new Intent(trader_details.this, overview_trader.class);
                                startActivity(i);
                            } catch (Exception e) {
                                Toast.makeText(trader_details.this, R.string.delete_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(trader_details.this, R.string.not_saved, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.rly_delete).setPositiveButton(R.string.yes, dialogClickListener)
                    .setNegativeButton(R.string.no, dialogClickListener).show();
            return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}


