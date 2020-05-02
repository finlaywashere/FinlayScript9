package xyz.finlaym.finlayscript9.interpreter.instructions;

import xyz.finlaym.finlayscript9.interpreter.Environment;
import xyz.finlaym.finlayscript9.interpreter.Program;
import xyz.finlaym.finlayscript9.interpreter.Result;

public class StaticDataInstruction extends Instruction{

	public StaticDataInstruction(long pointer, String instruction) {
		super(InstructionType.STATICDATA, pointer, instruction);
	}

	@Override
	public Result execute(Program p, Environment pubEnv, Environment privEnv){
		return new Result(getInstruction().substring(1, getInstruction().length()-1));
	}

}
