package xyz.finlaym.finlayscript9.state;

import java.util.HashMap;
import java.util.Map;

public class ProgramMap {
	private Map<String,Long> functionPointers = new HashMap<String,Long>();
	private Map<String,Long> variablePointers = new HashMap<String,Long>();
	public Map<String, Long> getFunctionPointers() {
		return functionPointers;
	}
	public Map<String, Long> getVariablePointers() {
		return variablePointers;
	}
}
