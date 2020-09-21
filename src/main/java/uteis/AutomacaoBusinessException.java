package uteis;

/**
 * O AutomacaoBusinessException pode-se gerar uma mensagem de erro customizada.
 */
public class AutomacaoBusinessException extends RuntimeException {
	
    private static final long serialVersionUID = 7718828512143293558L;
    public AutomacaoBusinessException() {
        super();
    }
    public AutomacaoBusinessException(String message, Throwable cause) {
        super(message, cause);
        Log.info(message);
    }
    public AutomacaoBusinessException(String message) {
        super(message);
        Log.info(message);
    }
    public AutomacaoBusinessException(Throwable cause) {
        super(cause);
        Log.info(cause.getMessage());
    }
    
}
