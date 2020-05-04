import Errors.NoAvailablePorts;
import gnu.io.*;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Gilmar Correia
 * Fontes de Referencia: https://playground.arduino.cc/Interfacing/Java/
 */

public class SerialArduino implements SerialPortEventListener{

	private Scanner keyboard = new Scanner(System.in);
	private ArrayList <CommPortIdentifier> portMap = new ArrayList<CommPortIdentifier>();
	private BufferedReader input;
	private OutputStream output;
	private SerialPort serialPort;
	
	// DEFAULT VALUES
	private int timeout = 1000;
	private int baudrate = 115200;
	private String portName;
	private String inputValue;
	private long time = 0, initialTime = 0;
	
	public SerialArduino (int baudrate) {
		this();
		setBaudrate(baudrate);
	}
	
	public SerialArduino() {
		this.initialTime = System.currentTimeMillis();
		showTexts();
		try {
			enableSerialComm();
		} catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void showTexts(){
		System.out.println("## Habilitando Comunicacao Serial com o Microcontrolador ##");
		
		try {
			seeAvailablePorts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("");
		System.out.println("Qual Porta Deseja Habilitar (Somente Numeros)?");
		
		short portNumber = this.keyboard.nextShort();
		setPortName(this.portMap.get(portNumber-1).getName());
		
		System.out.println("\nPorta Habilitada: " + getPortName());
	}
	
	private void setPortName (String portName) {
		if (portName == null)
			this.portName = "/com/";
		else
			this.portName = portName; 
	}
	
	public String getPortName() {
		return this.portName;
	}
	
	public void setTimeout (int timeout) {
		this.timeout = timeout;
	}
	
	public int getTimeout() {
		return this.timeout;
	}
	
	public void setBaudrate(int baudrate){
		this.baudrate = baudrate;
	}
	
	public int getBaudrate() {
		return this.baudrate;
	}
	
	private synchronized void setInputValue(String inputLine) {
		this.inputValue = inputLine;
	}
	
	public String getInputValue() {
		return this.inputValue;
	}
	
	private void seeAvailablePorts(){
		
		short i = 1; 
		
		Enumeration pList = CommPortIdentifier.getPortIdentifiers();
		
		while (pList.hasMoreElements()) {
			System.out.println("\nPORTAS DISPONIVEIS");
			
			CommPortIdentifier commPort = (CommPortIdentifier) pList.nextElement();
			this.portMap.add(commPort);
			
			System.out.println("Porta "+ i + ": " + commPort.getName());
			i++;
		}
		
		if (i==1)
			throw new NoAvailablePorts();
	}
	
	private void enableSerialComm() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
		CommPortIdentifier cp = CommPortIdentifier.getPortIdentifier(getPortName());
		this.serialPort = (SerialPort) cp.open(cp.getName(), getTimeout());
		
		this.serialPort.setSerialPortParams(
				getBaudrate(),
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		
		this.input = new BufferedReader(new InputStreamReader(this.serialPort.getInputStream()));
		this.output = this.serialPort.getOutputStream();
		
		try {
			this.serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		this.serialPort.notifyOnDataAvailable(true);
	}

	public synchronized void close() {
		if (this.serialPort != null) {
			this.serialPort.removeEventListener();
			this.serialPort.close();
		}
	}
	
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {	
				setInputValue(input.readLine());
				System.out.println("ENTREI");
				run();
			} catch (IOException e) {} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}
	}	
	
	public void run() throws Exception{
		
	}
}
