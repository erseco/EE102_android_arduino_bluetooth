
//Pines donde conectaremos los LEDS
#define LED0 0
#define LED1 1
#define LED2 2
#define LED3 3
#define LED4 4
#define LED5 5
#define LED6 6
#define LED7 7
#define LED8 8
#define LED9 9

//Pines del Bluetooth
#define TX 11
#define RX 12

#include <SoftwareSerial.h>


//Estados de los LEDS
int led0 = LOW;
int led1 = LOW;
int led2 = LOW;
int led3 = LOW;
int led4 = LOW;
int led5 = LOW;
int led6 = LOW;
int led7 = LOW;
int led8 = LOW;
int led9 = LOW;


//Creamos la conexion con el Bluetooth
SoftwareSerial bluetooth(TX, RX);

void setup() {

	//Inicializamos los pines de los LEDS
	pinMode(LED0, OUTPUT);
	pinMode(LED1, OUTPUT);
	pinMode(LED2, OUTPUT);
	pinMode(LED3, OUTPUT);
	pinMode(LED4, OUTPUT);
	pinMode(LED5, OUTPUT);
	pinMode(LED6, OUTPUT);
	pinMode(LED7, OUTPUT);
	pinMode(LED8, OUTPUT);
	pinMode(LED9, OUTPUT);



	//Configuramos el bluetooth a 9600
	bluetooth.begin(9600);

	//Iniciamos los LEDS
	digitalWrite(LED0, led0);
	digitalWrite(LED1, led1);
	digitalWrite(LED2, led2);
	digitalWrite(LED3, led3);
	digitalWrite(LED4, led4);
	digitalWrite(LED5, led5);
	digitalWrite(LED6, led6);
	digitalWrite(LED7, led7);
	digitalWrite(LED8, led8);
	digitalWrite(LED9, led9);


}


void loop() {

 //Si hay datos por leer los leemos
 if (bluetooth.available()) {
	//Obtenemos el LED a leer
	int LED = bluetooth.read();

	switch (LED) {
	case '0':
		led0 = !led0;
		digitalWrite(LED0, led0);
		break;
	case '1':
		led1 = !led1; //Invertimos el estado
		digitalWrite(LED1, led1); //Ponemos el LED al estado
		break;
	case '2':
		led2 = !led2;
		digitalWrite(LED2, led2);
		break;
	case '3':
		led3 = !led3;
		digitalWrite(LED3, led3);
		break;
	case '4':
		led4 = !led4;
		digitalWrite(LED4, led4);
		break;
	case '5':
		led5 = !led5;
		digitalWrite(LED5, led5);
		break;
	case '6':
		led6 = !led6;
		digitalWrite(LED6, led6);
		break;
	case '7':
		led7 = !led7;
		digitalWrite(LED7, led7);
		break;
	case '8':
		led8 = !led8;
		digitalWrite(LED8, led8);
		break;
	case '9':
		led9 = !led9;
		digitalWrite(LED9, led9);
			break;
	case 'r': // reset
		led0 = LOW;
		led1 = LOW;
		led2 = LOW;
		led3 = LOW;
		led4 = LOW;
		led5 = LOW;
		led6 = LOW;
		led7 = LOW;
		led8 = LOW;
		led9 = LOW;
		digitalWrite(LED0, LOW);
		digitalWrite(LED1, LOW);
		digitalWrite(LED2, LOW);
		digitalWrite(LED3, LOW);
		digitalWrite(LED4, LOW);
		digitalWrite(LED5, LOW);
		digitalWrite(LED6, LOW);
		digitalWrite(LED7, LOW);
		digitalWrite(LED8, LOW);
		digitalWrite(LED9, LOW);
			break;

	}

 }
	//Esperamos 100 milisegundos antes
	//de volver a comprobar el bluetooth
	delay(10);

}
