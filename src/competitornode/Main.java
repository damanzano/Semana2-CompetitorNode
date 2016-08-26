package competitornode;
import processing.core.PApplet;

public class Main extends PApplet {
	private Logic log;
	
	public static void main(String[] args) {
		System.out.println("Starting circle node");
		PApplet.main("competitornode.Main");
	}
	public void settings(){
		size(1000, 100);
	}
	
	public void setup(){
		log = new Logic(this);
	}
	
	public void draw(){
		log.draw();
	}
	
	@Override
	public void mousePressed() {
		log.mousePressed();
	}
	
	
	
}
