package cubex.mahesh.bt_oct7am

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bAdapter : BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        s1.isChecked = bAdapter.isEnabled
        s1.setOnCheckedChangeListener(
                object:CompoundButton.OnCheckedChangeListener{
                    override fun onCheckedChanged(p0: CompoundButton?,
                                                  p1: Boolean) {
                        if(p1){
                            bAdapter.enable()
                        }else{
                            bAdapter.disable()
                        }
                    }
                })
        getBtDevices.setOnClickListener {
            var temp_list = mutableListOf<String>()
            var adapter = ArrayAdapter<String>(this@MainActivity,
                    android.R.layout.simple_spinner_item,temp_list)
            lview.adapter = adapter
            bAdapter.startDiscovery()
            var filter = IntentFilter( )
            filter.addAction(BluetoothDevice.ACTION_FOUND)
            registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    var device = p1!!.getParcelableExtra<BluetoothDevice>(
                            BluetoothDevice.EXTRA_DEVICE)
                    temp_list.add(device.name+"\t"+device.address)
                    adapter.notifyDataSetChanged()
                }
            },filter)


        }
    }
}
