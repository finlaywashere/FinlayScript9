package xyz.finlaym.finlayscript9.interpreter;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	private Map<String,Result> variables = new HashMap<String,Result>();

	public Map<String, Result> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Result> variables) {
		this.variables = variables;
	}
	
}
