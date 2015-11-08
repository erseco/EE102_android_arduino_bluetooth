package es.ernesto.arduinobluetooth;

import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import es.ernesto.arduinobluetooth.R;

import es.ernesto.arduinobluetooth.BluetoothUtils;

import static java.lang.Thread.*;



public class Main extends Activity implements  OnItemClickListener{

	private ListView devices;
	private BluetoothUtils bluetooth;

    private boolean loopingBlink = false;
    private boolean loopingKnightRider = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//Creamos un objeto BluetoothUtils 
		//para simplificar us uso
		bluetooth = new BluetoothUtils();
		
		//Obtenemos la lista de elementos
		devices = (ListView) findViewById(R.id.devices);
		
		//Obtenemos los nombres de los dispositivos
		//bluetooth vinculados
		String[] names = bluetooth.getNames();
		
		//Asignamos los nombres a la lista
		devices.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));

		//Leemos los "clicks" sobre los elementos de 
		//la lista
		devices.setOnItemClickListener(this);
		
		
	}

	
	/**
	 * Cuando cerramos la app desconectamos
	 */
	protected void onPause() {
		super.onPause();
		bluetooth.disconnect();
	}
	
	
	
	/**
	 * Método que se ejecutará cuando se pulse sobre un elemento de la 
	 * lista. Index indicará el número del elemento pulsado.
	 */
	public void onItemClick(AdapterView<?> ag, View v, int index, long id) {
		//Conectamos con el elemento pulsado
		if (bluetooth.connect(index))
			Toast.makeText(this, R.string.connected, Toast.LENGTH_SHORT).show();
	}

	
	
	
	
	
	/**
	 * Método que se ejecutará cuando pulsemos sobre uno de los botones
	 * de LED. El parámetro v indica que botón hemos pulsado
	 */
	public void toggleClickHandler(View v) {

		//Si no estamos conectados, terminamos
		if (!bluetooth.isConnected()) {
			Toast.makeText(this, R.string.first_connect, Toast.LENGTH_SHORT).show();
			//return;
		}

		switch (v.getId())
		{
			case R.id.btnBlink:
				unsetAllButtons(v);
				if (((ToggleButton)v).isChecked())
					blink();
				break;
			case R.id.btnKnigthRider:
				unsetAllButtons(v);
				if (((ToggleButton)v).isChecked())
					knigtRider();
				break;
			case R.id.btnReset:
				unsetAllButtons(v);
                bluetooth.send("r"); // "r" is the value for reset in our orduino code
				break;
			default:
				unsetSpecialButtons();
				String name = getResources().getResourceEntryName(v.getId());
				String id = name.replace("btn", "");

                System.out.println("Setting LED:"+(id));

				bluetooth.send(id);
				break;
		}

		
//		switch (v.getId())
//		{
//			case R.id.led_1:
//				bluetooth.send(1);
//				break;
//			case R.id.led_2:
//				bluetooth.send(2);
//				break;
//		}
		
	}

	public void unsetAllButtons(View v)
	{
        cancelAllLoops();

		ToggleButton btn;

		for (int i = 0; i < 10; i++)
		{
			int id = getResources().getIdentifier("btn"+i, "id", getPackageName());
			btn = (ToggleButton) findViewById(id);
			btn.setChecked(false);
		}

		if (v.getId() != R.id.btnBlink)
			((ToggleButton)findViewById(R.id.btnBlink)).setChecked(false);

		if (v.getId() != R.id.btnKnigthRider)
			((ToggleButton)findViewById(R.id.btnKnigthRider)).setChecked(false);

        ((ToggleButton)findViewById(R.id.btnReset)).setChecked(false);

	}

	public void unsetSpecialButtons()
	{

        cancelAllLoops();

        ((ToggleButton)findViewById(R.id.btnBlink)).setChecked(false);

        ((ToggleButton)findViewById(R.id.btnKnigthRider)).setChecked(false);

        ((ToggleButton)findViewById(R.id.btnReset)).setChecked(false);

	}

    public void cancelAllLoops()
    {
        loopingBlink = false;
        loopingKnightRider = false;

    }


	public void blink()
	{

        loopingBlink = true;

        new Thread(new Runnable() {
			public void run()
			{
				while (loopingBlink) {
                    System.out.println("Blink");

                    for (int i = 0; i < 10; i++)
                        bluetooth.send(String.valueOf(i));

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
			}
		}).start();


		//handler.postDelayed(r, 1000);


		//while (	((ToggleButton)findViewById(R.id.btnBlink)).isChecked())
		//{

		//	for (int i = 1; i <= 10; i++)
				//bluetooth.send(i);

			//sleep(1000);

		//}

	}

	public void knigtRider() {

        loopingKnightRider = true;

		new Thread(new Runnable() {
			public void run()
			{
				while (loopingKnightRider)
				{

					System.out.println("knight rider");

                    for (int i = 0; i < 10; i++) {
                        bluetooth.send(String.valueOf(i));

                        try {
                            sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    bluetooth.send("r");

                    for (int i = 9; i >= 0; i--) {
                        bluetooth.send(String.valueOf(i));

                        try {
                            sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                       // bluetooth.send(String.valueOf(i+1));

                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                    }

                    bluetooth.send("r");



				}

			}
		}).start();
	}



}
