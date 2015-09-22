
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


class Handler extends Thread {
	Socket socket;
	BufferedReader entree;
	PrintStream sortie;
    private Moteur motor = null;
	 int nbrCycle=0;
	  long t1=System.currentTimeMillis();
	 private final GpioController gpio = GpioFactory.getInstance();
	 GpioPinDigitalInput pin5 = null;
	Handler(Socket socket) {
		this.socket = socket;
		 this.motor = Moteur.getInstance();
	     pin5 = motor.getPinSpeed();
		pin5.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
				if(event.getState().toString().equals("HIGH"))nbrCycle++;
				            
            }  
        });
		try {
			entree = new BufferedReader(new InputStreamReader
 				(socket.getInputStream()));
			sortie = new PrintStream(socket.getOutputStream());
			this.start();
		}
		catch(IOException exc) {
			try {
				socket.close();
			}
			catch(IOException e){}
		}
	}

	public void run() {
		String input = "";
		String action = "stop";

		try {
		    while (true) {
			input=entree.readLine();
			sortie.println(nbrCycle);
			if(System.currentTimeMillis() - t1 >= 1000){
				t1=System.currentTimeMillis();
				   nbrCycle=0;
				   
				}  
			if (!input.equals("") && !input.equals("vitesse")) {
                    action = input;
                }
                this.handleInput(action);
		    }
		
		}
		catch(IOException e) {}
	}
	public void handleInput(String action) {
	     String[] st = action.split(",");
		 int speed=0;
		 if(st.length >1 )
		 {
			action = st[0];
			speed = Integer.parseInt(st[1]);
		 }
		 else action = st[0];
        if (action.equals("arret")) {
           motor.stop(); // Stop
        } else if (action.equals("avance")){
			motor.forward(speed);
		}
		else if (action.equals("recule")){
			motor.reverse(speed);
		}
		else if (action.equals("avance-gauche")){
			motor.forwardLeft(speed);
		}
		else if(action.equals("avance-droite")){
			motor.forwardRight(speed);
		}
		else if (action.equals("recule-gauche")){
			motor.backLeft(speed);
		}
		else if(action.equals("recule-droite")){
			motor.backRight(speed);
		}
    }
}


