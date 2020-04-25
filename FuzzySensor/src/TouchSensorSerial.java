
public class TouchSensorSerial extends SerialArduino{
	//Serial Protocol
	public static final int TS_START = 255;
	public static final short TS_WRITE = 1;
	public static final short TS_READ = 0;
	public static final short TS_FORCE_BEGIN = 1;
	public static final short TS_FORCE_END = 2;
	public static final short TS_PIEZO = 3;
	public static final short TS_POT = 4;

	public static final short TS_FORCE_BEGIN_LENGTH = 4;
	public static final short TS_FORCE_END_LENGTH = 3;
	public static final short TS_PIEZO_LENGTH = 3;
	public static final short TS_PIEZOWRITE_LENGTH = 4;
	public static final short TS_POT_LENGTH = 3;
	public static final short TS_POT_WRITE_LENGTH = 4;

	public static final int TX_DELAY_TIME = 200; //Microseconds
	public static final int RX_DELAY_TIME = 10;  //Millisseconds
	
	public static int buffer[] = new int[20];
	private static int force;
	private static boolean controlTime = true;
	private static long millisInitialTime, millisFinalTime;
	
	TouchSensorSerial(){
		super();
	}
	
	private void setForce(int force) {
		this.force = force;
	}
	
	private void setMillisInitialTime(long initialTime) {
		this.millisInitialTime = initialTime;
	}
	
	private void setMillisFinalTime(long finalTime) {
		this.millisFinalTime = finalTime;
	}
	
	private void setControlTime(boolean controlTime) {
		this.controlTime = controlTime;
	}
	
	public int getForce() {
		return this.force;
	}
	
	public long getMillisInitialTime() {
		return this.millisInitialTime;
	}
	
	public long getMillisFinalTime() {
		return this.millisFinalTime;
	}
	
	public boolean getControlTime() {
		return this.controlTime;
	}
	
	private void clearBuffer(){
	  for (int  index= 0; index<buffer.length; index++)
	    buffer[index] = 0;
	}

	private int convert(int binary){
	  return binary*256;
	}
		
	@Override
	public void run() throws Exception {
		String msg = this.getInputValue();
		
		int j=0;
		for(int index = 0;index<msg.length();index++) {
			if(Character.compare(msg.charAt(index), ',') == 0) 
				j++;
			else 
				buffer[j] = 10*buffer[j] + (msg.charAt(index) - '0');
		}
			
		
		if(buffer[0] == TS_START) {
			if(buffer[1] == TS_START){
				if(buffer[2] == TS_WRITE){
			        int lengthMsg = buffer[3];
			        int type = buffer[4];
			        int checkSum;
			        switch (type){
			          case TS_FORCE_BEGIN:
			        	  int force = 0;
			        	  force = buffer[5];
			              force = force + convert(buffer[6]);
			              
			              checkSum = (~(TS_WRITE+TS_FORCE_BEGIN_LENGTH+TS_FORCE_BEGIN+(force&0xFF)+(force>>8)))&0xFF;
			              if (checkSum == buffer[7]) {
			            	  if(getControlTime()) {
			            		  setMillisInitialTime(System.currentTimeMillis());
			            		  setControlTime(false);
			            	  }
			            	  
			                setForce(force);
			                //System.out.println(getForce());
			              }
			              else
			            	throw new Exception("Falha na Mensagem");
			          
			        	  break;
			          case TS_FORCE_END:
			        	  int chr = buffer[5];
			        	  
			        	  checkSum = (~(TS_WRITE+TS_FORCE_END_LENGTH+TS_FORCE_END+chr))&0xFF;
			        	  
			        	  if (checkSum == buffer[6]) {
			            	  if(!getControlTime()) {
			            		  setMillisFinalTime(System.currentTimeMillis());
			            		  setControlTime(true);			            		 
			            	  }
			            	  setForce(0);
			              }
			              else
			            	throw new Exception("Falha na Mensagem");
			        	  break;
			        }
				}
			}
		}
		else
			throw new Exception("Erro de Comunicação");
		
		clearBuffer();
	}
	

}
