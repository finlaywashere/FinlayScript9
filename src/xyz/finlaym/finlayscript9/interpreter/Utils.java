package xyz.finlaym.finlayscript9.interpreter;

import java.util.List;

import xyz.finlaym.finlayscript9.interpreter.instructions.Instruction;

public class Utils {
	public static Function findFunction(long pointer, List<Function> f) {
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
	public static Function findFunctionInParent(String name, Function parent, Program p) {
		for(Function f : (parent == null ? p.getRootFunctions() : parent.getNestedFunctions())) {
			if(f.getName().equals(name))
				return f;
		}
		return null;
	}
	public static Function findFunction(String name, List<Function> f) {
		for(Function f1 : f) {
			if(f1.getName().equals(name))
				return f1;
		}
		for(Function f1 : f) {
			Function f2 = findFunction(name, f1.getNestedFunctions());
			if(f2 != null)
				return f2;
		}
		return null;
	}
	public static Instruction findInstruction(long pointer, List<Function> f) {
		for(Function f1 : f) {
			for(Instruction i : f1.getInstructions()) {
				if(i.getPointer() == pointer)
					return i;
			}
		}
		for(Function f1 : f) {
			Instruction i = findInstruction(pointer, f1.getNestedFunctions());
			if(i != null)
				return i;
		}
		return null;
	}
}
