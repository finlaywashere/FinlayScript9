package xyz.finlaym.finlayscript9.interpreter;

import java.util.ArrayList;
import java.util.List;

import xyz.finlaym.finlayscript9.interpreter.instructions.Instruction;

public class Program {
	private List<Function> rootFunctions = new ArrayList<Function>();
	private long basePointer;
	private List<String> lines;
	private List<Instruction> instructions = new ArrayList<Instruction>();
	private long stackPointer;
	private Environment publicEnvironment = new Environment();
	
	public Environment getPublicEnvironment() {
		return publicEnvironment;
	}
	public void setPublicEnvironment(Environment publicEnvironment) {
		this.publicEnvironment = publicEnvironment;
	}
	public long getStackPointer() {
		return stackPointer;
	}
	public void setStackPointer(long stackPointer) {
		this.stackPointer = stackPointer;
	}
	public List<Instruction> getInstructions() {
		return instructions;
	}
	public Program(long basePointer, List<String> lines) {
		this.basePointer = basePointer;
		this.lines = lines;
	}
	public List<Function> getRootFunctions() {
		return rootFunctions;
	}
	public long getBasePointer() {
		return basePointer;
	}
	public List<String> getLines() {
		return lines;
	}
	public void setRootFunctions(List<Function> rootFunctions) {
		this.rootFunctions = rootFunctions;
	}
	
}
