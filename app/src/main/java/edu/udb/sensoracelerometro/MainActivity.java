package edu.udb.sensoracelerometro;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // variable para comunicarme con el dispositivo
    SensorManager sensorManager;

    // Varible para representar el sensor
    Sensor sensor;

    // varible para poder capturar el evento de acelerometro
    SensorEventListener sensorEventListener;

    // varible para comparar los ejes
    int comp =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creamos una instancia del sensormanager
        sensorManager =(SensorManager)getSystemService(SENSOR_SERVICE);

        // creamos nuestra variable sensor para rotacion del telefono , ejes x,y,z
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // comprobamos su nuestro dispositico cuenta con el sensor finalizamos nuestra Activity
        if (sensor == null) {
            finish();
        }

        // agregamos el evento que va realizar la tarea
        sensorEventListener= new SensorEventListener() {

            // Este metodo PARA verificar cuando lo valores del sensor han cambiado
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                // El Acelerometro trabaja en los tres ejes X =0, Y=1, Z=2 , android, nos regresa
                // la informacion en un arreglo.
               float x =sensorEvent.values[0]; // eje X
                System.out.print("Valor del giro en x :" +x);

                // se detecta que se movera hacia la derecha  , decremente
                if(x<-5 && comp==0){
                  comp++; // incremento 1 , en movimiento hacia la derecha
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);

                  // se detecta que se movera hacia la izquierda , incrementa
                }else  if(x>5 && comp==1){
                    comp++; // incremento 1 , en movimiento hacia la izquierda
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
                // Si nuestra variable de comparacion ya es 2, ya giro ambos lados y reiniciamos variable
                if(comp ==2){
                    comp=0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        start();
    }

    // metodo para invocar el sensorManager
    public void start(){
        sensorManager.registerListener(sensorEventListener,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // metodo para detener al sensor
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }
}