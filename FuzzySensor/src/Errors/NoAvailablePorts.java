package Errors;

/**
 * 
 * @author Gilmar Correia
 *
 */

public class NoAvailablePorts extends Error{
	public NoAvailablePorts() {
		super ("Não Existem Portas Disponíveis");
	}
}
