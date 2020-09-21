/**
 * 
 */
package uteis;

import java.util.Map;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

/**
 * Coleção de asserts que deve ser validado ao final com metodo assertAll() 
 */
public class SoftAssert extends Assertion {
	// LinkedHashMap to preserve the order
	private Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();

	@Override
	protected void doAssert(IAssert<?> a) {
		onBeforeAssert(a);
		try {
			a.doAssert();
			onAssertSuccess(a);
			ReportListener.setSteps("<b><font color='green'>PASSED:</font></b> " + a.getMessage());
		} catch (AssertionError ex) {
			onAssertFailure(a, ex);
			m_errors.put(ex, a);
			ReportListener.setSteps("<b><font color='red'>FAILED:</font></b> " + a.getMessage());
		} finally {
			onAfterAssert(a);
		}
	}

	public void assertAll() {
		if(!m_errors.isEmpty()){
			StringBuilder sb = new StringBuilder("As seguintes validações falharam:");
			boolean first = true;
			for (Map.Entry<AssertionError, IAssert<?>> ae : m_errors.entrySet()) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				sb.append("\n\t");
				sb.append(ae.getKey().getMessage());
			}
			m_errors = Maps.newLinkedHashMap();
			throw new AssertionError(sb.toString());
		}
	}
	
}
