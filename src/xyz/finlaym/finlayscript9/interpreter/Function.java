package xyz.finlaym.finlayscript9.interpreter;

import java.util.ArrayList;
import java.util.List;

import xyz.finlaym.finlayscript9.interpreter.instructions.Instruction;

public class Function {
	private List<Function> nestedFunctions = new ArrayList<Function>();
	private long pointer;
	private String name;
	private String[] arguments;
	private Function root;
	private int line;
	private List<Instruction> instructions = new ArrayList<Instruction>();
	
	public Function(Function root, long pointer, String name, String[] arguments, int line) {
		this.root = root;
		this.pointer = pointer;
		this.name = name;
		this.arguments = arguments;
		this.line = line;
	}
	public int getLine() {
		return line;
	}
	public Function getRoot() {
		return root;
	}
	public List<Function> getNestedFunctions() {
		return nestedFunctions;
	}
	public long getPointer() {
		return pointer;
	}
	public String getName() {
		return name;
	}
	public String[] getArguments() {
		return arguments;
	}
	public List<Instruction> getInstructions() {
		return instructions;
	}
	
}
