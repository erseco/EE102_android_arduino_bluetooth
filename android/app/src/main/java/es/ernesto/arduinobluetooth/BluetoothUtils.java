package es.ernesto.arduinobluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import android.os.ParcelUuid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.DataOutputStream;

public class BluetoothUtils {
	
	private static final String UUID_CODE = "00001101-0000-1000-8000-00805f9b34fb";

	private ArrayList<BluetoothDevice> devices;
	private BluetoothAdapter adapter;
	
	private BluetoothSocket socket;
	
	public BluetoothUtils() {
		devices = new ArrayList<BluetoothDevice>();
		
		//Obtenemos el dispositivo bluetooth del terminal
		adapter = BluetoothAdapter.getDefaultAdapter();
		
		//Si no hay dispositivo terminamos
		if (adapter == null)
			return;
		
		//Obtenemos todos los dispositivos bluetooth
		//vinculados
		for (BluetoothDevice d : adapter.getBondedDevices())
			devices.add(d);
		
	}
	
	/**
	 * Devuelve el nombre de los dispositivos
	 * vinculados para poder seleccionarlo
	 * @return
	 */
	public String[] getNames() {
		String names[] = new String[devices.size()];
		
		for (int i = 0; i < devices.size(); i++)
			names[i] = devices.get(i).getName();
		
		return names;
	}
	
	
	/**
	 * Método para conectar a un dispositivo Bluetooth
	 * según su posición en la lista.
	 * @param index
	 * @return si ha conectado o no
	 */
	public boolean connect(int index) {
		if (index < 0 || index >= devices.size())
			return false;
		
		try {
			//Obtenemos el dispositivo según su posición
			BluetoothDevice device = devices.get(index);
			//Conectamos con el dispositivo

			//System.out.println("obteniendo uuids");
			//ParcelUuid[] uuids = device.getUuids();

			//for (ParcelUuid uuid: uuids) {
			//	System.out.println("UUID: " + uuid.getUuid().toString());
			//}
			//socket = device.createRfcommSocketToServiceRecord(UUID.fromString(UUID_CODE));
			socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(UUID_CODE));
			socket.connect();
			return true;
			
		} catch (IOException e) {
			//Si ha ocurrido algún error devolvermos false
			e.printStackTrace();
			return false;
		}

		
	}
	
	/**
	 * Método para desconectar
	 */
	public void disconnect() {
		if (isConnected()) {
			try {
				socket.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	
	/**
	 * Método que nos indica si está o no coenctado
	 * el socket bluetooth
	 */
	public boolean isConnected() {
		if (socket == null)
			return false;
		
		return socket.isConnected();
	}
	
	
	/**
	 * Método para enviar un dato mediante bluetooth
	 * @param value
	 */
	public void send(String value) {
		
		//Si el socket está a null es que no hemos conectado
		if (socket == null)
			return;

		try {

			socket.getOutputStream().write(String.valueOf(value).getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
