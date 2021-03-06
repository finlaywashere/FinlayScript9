package xyz.finlaym.finlayscript9.interpreter.instructions;

import xyz.finlaym.finlayscript9.interpreter.Environment;
import xyz.finlaym.finlayscript9.interpreter.Program;
import xyz.finlaym.finlayscript9.interpreter.Result;
import xyz.finlaym.finlayscript9.parser.Parser;

public class VariableInstruction extends Instruction {

	public VariableInstruction(long pointer, String instruction) {
		super(InstructionType.VAR, pointer, instruction);
	}

	@Override
	public Result execute(Program p, Environment pubEnv, Environment privEnv) {
		String newInst = getInstruction().substring(4);
		if (newInst.contains("=")) {
			String[] split = newInst.split("=", 2);
			String name = split[0].trim();
			Instruction data = Parser.parseInstruction(getPointer(), 0, split[1], pubEnv, privEnv);
			boolean global = (privEnv == null ? true : false);
			Result value = data.execute(p, pubEnv, privEnv);
			if (global)
				pubEnv.getVariables().put(name, value);
			else
				privEnv.getVariables().put(name, value);
			return null;
		} else {
			if(privEnv != null && privEnv.getVariables().containsKey(newInst)) {
				return privEnv.getVariables().get(newInst);
			}else if(pubEnv.getVariables().containsKey(newInst)) {
				return pubEnv.getVariables().get(newInst);
			}
		}
		return null;
	}

}
