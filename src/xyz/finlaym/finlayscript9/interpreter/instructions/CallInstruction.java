package xyz.finlaym.finlayscript9.interpreter.instructions;

import java.util.List;

import xyz.finlaym.finlayscript9.interpreter.Environment;
import xyz.finlaym.finlayscript9.interpreter.Function;
import xyz.finlaym.finlayscript9.interpreter.Interpreter;
import xyz.finlaym.finlayscript9.interpreter.Program;
import xyz.finlaym.finlayscript9.interpreter.Result;
import xyz.finlaym.finlayscript9.parser.Parser;

public class CallInstruction extends Instruction{

	public CallInstruction(long pointer, String instruction) {
		super(InstructionType.CALL, pointer, instruction);
		
	}

	@Override
	public Result execute(Program p, Environment pubEnv, Environment privEnv){
		String newInst = getInstruction().substring(5);
		String[] split = newInst.split("\\(",2);
		String name = split[0];
		String args = split[1].substring(0, split[1].lastIndexOf(")"));
		Function f = findFunction(getPointer(),p.getRootFunctions());
		Instruction[] instructions = Parser.parseArguments(args, findFunctionInParent(name, f,p),pubEnv,privEnv);
		Result r = Interpreter.executeFunction(p, f,name, instructions);
		return r;
	}
	private static Function findFunction(long pointer, List<Function> f) {
		for(Function f1 : f) {
			if(f1.getPointer() < pointer && f1.getPointer()+f1.getInstructions().size() >= pointer) {
				return f1;
			}
		}
		for(Function f1 : f) {
			Function f2 = findFunction(pointer, f1.getNestedFunctions());
			if(f2 != null)
				return f2;
		}
		return null;
	}
	private static Function findFunctionInParent(String name, Function parent, Program p) {
		for(Function f : (parent == null ? p.getRootFunctions() : parent.getNestedFunctions())) {
			if(f.getName().equals(name))
				return f;
		}
		return null;
	}
}
