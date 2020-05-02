package xyz.finlaym.finlayscript9.interpreter.instructions;

import xyz.finlaym.finlayscript9.interpreter.Environment;
import xyz.finlaym.finlayscript9.interpreter.Program;
import xyz.finlaym.finlayscript9.interpreter.Result;

public abstract class Instruction {
	private InstructionType type;
	private long pointer;
	private String instruction;
	public InstructionType getType() {
		return type;
	}
	public long getPointer() {
		return pointer;
	}
	public String getInstruction() {
		return instruction;
	}
	public Instruction(InstructionType type, long pointer, String instruction) {
		this.type = type;
		this.pointer = pointer;
		this.instruction = instruction;
	}
	public abstract Result execute(Program p, Environment pubEnv, Environment privEnv);
}
