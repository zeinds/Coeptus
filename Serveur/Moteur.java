import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.SoftPwm;
import java.util.Collection;

public class Moteur {
       
         private boolean isActive;
		 private static Moteur uniqueInstance;
        // create gpio controller
         GpioController gpio = GpioFactory.getInstance();
		 // pin for reading speed
       final GpioPinDigitalInput pin5 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);
        // gpio pin Motor arriere gauche 
       final GpioPinDigitalOutput pin0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "PIN11", PinState.LOW);
		 final GpioPinDigitalOutput pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "PIN13", PinState.LOW);
		final GpioPinDigitalOutput pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "PIN15", PinState.LOW);
		
		 // gpio pin Motor avant gauche 
        final GpioPinDigitalOutput pin12 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_12, "PIN19", PinState.LOW);
		 final GpioPinDigitalOutput pin13 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, "PIN21", PinState.LOW);
		final GpioPinDigitalOutput pin14 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_14, "PIN13", PinState.LOW);
		
		 // gpio pin Motor arriere droite 
        final GpioPinDigitalOutput pin15 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15, "PIN8", PinState.LOW);
		 final GpioPinDigitalOutput pin16 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, "PIN10", PinState.LOW);
		final GpioPinDigitalOutput pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "PIN12", PinState.LOW);
		
		 // gpio pin Motor avant droite 
        final GpioPinDigitalOutput pin6 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "PIN22", PinState.LOW);
		 final GpioPinDigitalOutput pin10 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "PIN24", PinState.LOW);
		final GpioPinDigitalOutput pin11 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11, "PIN26", PinState.LOW);
		 private Moteur()
		 {
			com.pi4j.wiringpi.Gpio.wiringPiSetup();
			//SoftPwm.softPwmCreate(0, 1, 100);
			pin0.setShutdownOptions(true, PinState.LOW);
			SoftPwm.softPwmCreate(pin3.getPin().getAddress(), 1, 100);
			SoftPwm.softPwmCreate(pin14.getPin().getAddress(), 1, 100);
			SoftPwm.softPwmCreate(pin1.getPin().getAddress(), 1, 100);
			SoftPwm.softPwmCreate(pin11.getPin().getAddress(), 1, 100);
		
		 }
		  public static synchronized Moteur getInstance()
        {
                if(uniqueInstance==null)
                {
                        uniqueInstance = new Moteur();
                }
                return uniqueInstance;
        }
		 public void forward(int vitesse)
		 {
			
			SoftPwm.softPwmWrite(pin3.getPin().getAddress(), vitesse);
			SoftPwm.softPwmWrite(pin14.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin1.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin11.getPin().getAddress(),vitesse);
		
		 //motor arrière gauche
            pin0.high();
		    pin2.low();
		    pin3.high();
		 
			 //motor avant gauche
            pin12.high();
	        pin13.low();
		    pin14.high();
		 
		//motor arrière droite
		 pin15.low();
	    pin16.high();
	    pin1.high();
		 
		  //motor avant droite
          pin6.low();
	      pin10.high();
	     pin11.high();
		}
		 public void reverse(int vitesse)
		 {
		 SoftPwm.softPwmWrite(pin3.getPin().getAddress(), vitesse);
			SoftPwm.softPwmWrite(pin14.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin1.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin11.getPin().getAddress(),vitesse);
		 //motor arrière gauche
         pin0.low();
		 pin2.high();
		 pin3.high();
		 
			 //motor avant gauche
        pin12.low();
		 pin13.high();
		 pin14.high();
		 
		//motor arrière droite
		 pin15.high();
		pin16.low();
		 pin1.high();
		 
		  //motor avant droite
        pin6.high();
	    pin10.low();
	    pin11.high();
		}
		 public void forwardRight(int vitesse)
		 {
		 SoftPwm.softPwmWrite(pin3.getPin().getAddress(), vitesse);
			SoftPwm.softPwmWrite(pin14.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin1.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin11.getPin().getAddress(),vitesse);
		 //motor arrière gauche
            pin0.high();
		    pin2.low();
		    pin3.high();
		 
			 //motor avant gauche
            pin12.high();
	        pin13.low();
		    pin14.high();
		 
		//motor arrière droite
		 pin15.low();
		pin16.low();
		 pin1.low();
		 
		  //motor avant droite
        pin6.low();
	    pin10.low();
	    pin11.low();
		}
		  public void forwardLeft(int vitesse)
		 {
		 SoftPwm.softPwmWrite(pin3.getPin().getAddress(), vitesse);
			SoftPwm.softPwmWrite(pin14.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin1.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin11.getPin().getAddress(),vitesse);
		 //motor arrière gauche
            pin0.low();
		    pin2.low();
		    pin3.low();
		 
			 //motor avant gauche
            pin12.low();
	        pin13.low();
		    pin14.low();
		 
		//motor arrière droite
		 pin15.low();
	    pin16.high();
	    pin1.high();
		 
		  //motor avant droite
          pin6.low();
	      pin10.high();
	     pin11.high();
		}
		 public void backRight(int vitesse)
		 {
		 SoftPwm.softPwmWrite(pin3.getPin().getAddress(), vitesse);
			SoftPwm.softPwmWrite(pin14.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin1.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin11.getPin().getAddress(),vitesse);
		 //motor arrière gauche
            pin0.low();
		    pin2.high();
		    pin3.high();
		 
			 //motor avant gauche
            pin12.low();
	        pin13.high();
		    pin14.high();
		 
		//motor arrière droite
		 pin15.low();
		pin16.low();
		 pin1.low();
		 
		  //motor avant droite
        pin6.low();
	    pin10.low();
	    pin11.low();
		}
		  public void backLeft(int vitesse)
		 {
		 SoftPwm.softPwmWrite(pin3.getPin().getAddress(), vitesse);
			SoftPwm.softPwmWrite(pin14.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin1.getPin().getAddress(),vitesse);
			SoftPwm.softPwmWrite(pin11.getPin().getAddress(),vitesse);
		 //motor arrière gauche
            pin0.low();
		    pin2.low();
		    pin3.low();
		 
			 //motor avant gauche
            pin12.low();
	        pin13.low();
		    pin14.low();
		 
		//motor arrière droite
		 pin15.high();
	    pin16.low();
	    pin1.high();
		 
		  //motor avant droite
          pin6.high();
	      pin10.low();
	     pin11.high();
		}
		 
        public void stop()
		 {
      
		   //motor arrière gauche
            pin2.low();
		 pin0.low();
		 pin3.low();
		 //motor avant gauche
            pin12.low();
		 pin13.low();
		 pin14.low();
		  //motor arrière droite
		 pin15.low();
		 pin16.low();
		 pin1.low();
		 //motor avant droite
		 pin6.low();
		 pin10.low();
		 pin11.low();
		}
    public boolean isActive() {
        return this.isActive;
    }

	public void setActivity(boolean isActive) {
        this.isActive = isActive;
    }
	public GpioPinDigitalInput getPinSpeed()
	{
		return pin5;
	}
        public void shutdownGPIO() {
      
        gpio.shutdown();
	/*	 Collection<GpioPin> pins = gpio.getProvisionedPins();
		for (GpioPin g : pins) {
            
			gpio.unprovisionPin(g);
        }*/
		
		}
    
}