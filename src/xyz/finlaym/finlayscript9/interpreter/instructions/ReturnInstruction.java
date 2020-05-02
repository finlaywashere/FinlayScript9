package xyz.finlaym.finlayscript9.interpreter.instructions;

import xyz.finlaym.finlayscript9.interpreter.Environment;
import xyz.finlaym.finlayscript9.interpreter.Program;
import xyz.finlaym.finlayscript9.interpreter.Result;
import xyz.finlaym.finlayscript9.parser.Parser;

public class ReturnInstruction extends Instruction {

	public ReturnInstruction(long pointer, String instruction) {
		super(InstructionType.RETURN, pointer, instruction);
	}

	@Override
	public Result execute(Program p, Environment pubEnv, Environment privEnv) {
		return Parser.parseInstruction(getPointer(), 0, getInstruction().substring(4),pubEnv,privEnv).execute(p, pubEnv, privEnv);
	}

}
