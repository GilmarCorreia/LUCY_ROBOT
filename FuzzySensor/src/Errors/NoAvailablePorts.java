package Errors;

/**
 * 
 * @author Gilmar Correia
 *
 */

public class NoAvailablePorts extends Error{
	public NoAvailablePorts() {
		super ("N�o Existem Portas Dispon�veis");
	}
}
