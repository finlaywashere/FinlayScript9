package xyz.finlaym.finlayscript9.interpreter;

import java.util.List;

import xyz.finlaym.finlayscript9.interpreter.instructions.Instruction;
import xyz.finlaym.finlayscript9.parser.Parser;

public class Interpreter {
	public static Program loadProgram(List<String> program) throws Exception {
		Program p = Parser.parse(program);
		return p;
	}

	public static Result executeFunction(Program p, String name, Instruction[] args) {
		return executeFunction(p, null, name, args);
	}

	public static Result executeFunction(Program p, Function parent, String name, Instruction[] args) {
		for (Function f : (parent != null ? parent.getNestedFunctions() : p.getRootFunctions())) {
			if (f.getName().equals(name)) {
				Environment privEnv = new Environment();
				if (args != null) {
					for (int i = 0; i < (args.length < f.getArguments().length ? args.length
							: f.getArguments().length); i++) {
						String argName = f.getArguments()[i];
						privEnv.getVariables().put(argName, args[i].execute(p, p.getPublicEnvironment(), privEnv));
					}
				}
				List<Instruction> instructions = f.getInstructions();
				Result r = executeInstructions(p, instructions, p.getPublicEnvironment(), privEnv);
				return r;
			}
		}
		throw new RuntimeException("Could not find function by the name of " + name);
	}

	public static Result execute(Program p) {
		return executeInstructions(p, p.getInstructions(), p.getPublicEnvironment(), null);
	}

	private static Result executeInstructions(Program p, List<Instruction> instructions, Environment pubEnv,
			Environment privEnv) {
		for (Instruction i : instructions) {
			Result r = i.execute(p, pubEnv, privEnv);
			if (r != null)
				return r;
		}
		return null;
	}
}
