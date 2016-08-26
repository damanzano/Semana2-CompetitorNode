package competitornode;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import processing.core.PApplet;

public class Logic implements Observer{
	private PApplet app;
	private CommunicationManager comm;
	private InetAddress destAddress;
	private int destPort;
	private int x;
	private int y;
	private int vel;
	private int id=1;
	private boolean arrived;
	
	public Logic(PApplet app){
		this.app = app;
		this.x = 25;
		this.y = app.height/2;
		this.vel = 0;
		this.arrived = false; 
		
		this.comm = new CommunicationManager();
		this.comm.addObserver(this);
		(new Thread(this.comm)).start();
		
		this.destPort = 6000;
		try {
			this.destAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void draw() {
		app.background(200);
		app.fill(255);
		app.ellipse(x, y, 50, 50);
		app.fill(0);
		app.text(id, x, y);
		if(!arrived && x>=app.width - 25 ){
			sendArrive();
			arrived=true;
			vel=0;
		}
		x +=vel;
	}

	@Override
	public void update(Observable observed, Object data) {
		/*
		 *  Transform data to an understandable object
		 *  and do somethig with it.
		 */
		DatagramPacket receivedData = (DatagramPacket) data;
		String realData = new String(receivedData.getData(), 0, receivedData.getLength());
		
		destAddress = receivedData.getAddress();
		destPort = receivedData.getPort();
		
		if(realData.equalsIgnoreCase("start"))
			vel = (int)app.random(3)+1;
		
		if(realData.equalsIgnoreCase("stop"))
			vel=0;
		
		
	}

	public void sendArrive() {
		// Send a message with the coordinates
		String message = "Competitor "+id+" arrived";
		byte[] data = message.getBytes();
		comm.sendMessage(data, destAddress, destPort);
	}

	public void mousePressed() {
		// Reset to start position
		this.x = 25;
		this.vel = 0;
		this.arrived = false; 
	}
	
}
