package xyz.finlaym.finlayscript9.parser;

import java.util.ArrayList;
import java.util.List;

import xyz.finlaym.finlayscript9.interpreter.Environment;
import xyz.finlaym.finlayscript9.interpreter.Function;
import xyz.finlaym.finlayscript9.interpreter.Program;
import xyz.finlaym.finlayscript9.interpreter.instructions.CallInstruction;
import xyz.finlaym.finlayscript9.interpreter.instructions.Instruction;
import xyz.finlaym.finlayscript9.interpreter.instructions.ReturnInstruction;
import xyz.finlaym.finlayscript9.interpreter.instructions.StaticDataInstruction;
import xyz.finlaym.finlayscript9.interpreter.instructions.VariableInstruction;

public class Parser {
	private static final long BASE_POINTER = 4096;
	
	public static Program parse(List<String> programLines) throws Exception{
		Program program = new Program(BASE_POINTER,programLines);
		// Start pass #1 to find functions
		Function root = null;
		long pointer = program.getBasePointer()+programLines.size();
		for(int i = 0; i < programLines.size(); i++) {
			String line = programLines.get(i).trim();
			if(line.startsWith("//"))
				continue;
			if(line.startsWith("func ")) {
				line = line.substring(5);
				String[] split = line.split("\\(",2);
				String name = split[0];
				String[] args = split[1].substring(0, split[1].lastIndexOf(")")).split(",");
				Function f = new Function(root, pointer, name, args,i);
				if(root == null) {
					program.getRootFunctions().add(f);
				}else {
					root.getNestedFunctions().add(f);
				}
				root = f;
				continue;
			}else if(line.startsWith("end")) {
				if(root == null) {
					throw new Exception("Too many end instructions!");
				}
				root = root.getRoot();
			}
			pointer++;
		}
		
		// Start pass #2 to parse functions
		List<Function> newRootFunctions = new ArrayList<Function>();
		for(Function f : program.getRootFunctions()) {
			recursiveFunctionIndex(f,program);
			newRootFunctions.add(f);
		}
		program.setRootFunctions(newRootFunctions);
		int count = 0;
		// Start pass #3 to parse instructions outside of functions
		for(int i = 0; i < programLines.size(); i++) {
			String line = programLines.get(i).trim();
			if(line.startsWith("//"))
				continue;
			if(line.startsWith("func ")) {
				count++;
				continue;
			}else if(line.startsWith("end")) {
				count--;
				continue;
			}
			if(count != 0)
				continue;
			Instruction inst = parseInstruction(program.getBasePointer(),i,line,null,null);
			program.getInstructions().add(inst);
		}
		
		program.setStackPointer(pointer);
		
		return program;
	}
	private static void recursiveFunctionIndex(Function f, Program program) {
		int count = 0;
		long index = 1;
		for(int i1 = f.getLine(); i1 < program.getLines().size(); i1++) {
			String line = program.getLines().get(i1).trim();
			if(line.startsWith("//"))
				continue;
			if(line.startsWith("func ")) {
				count++;
				continue;
			}else if(line.startsWith("end")) {
				count--;
				continue;
			}
			if(count < 0)
				break;
			if(count != 1)
				continue;
			Instruction inst = parseInstruction(f.getPointer(),index,line,null,null);
			f.getInstructions().add(inst);
			index++;
		}
		
		for(Function f1 : f.getNestedFunctions()) {
			recursiveFunctionIndex(f1,program);
		}
	}
	public static Instruction parseInstruction(long pointerBase, long index, String inst, Environment pubEnv, Environment privEnv) {
		inst = inst.trim();
		if(inst.startsWith("var ")) {
			return new VariableInstruction(pointerBase+index, inst);
		}else if(inst.startsWith("call ")) {
			return new CallInstruction(pointerBase+index, inst);
		}else if(inst.startsWith("ret ")) {
			return new ReturnInstruction(pointerBase+index, inst);
		}else if(inst.startsWith("\"") && inst.endsWith("\"")) {
			return new StaticDataInstruction(pointerBase+index, inst);
		}
		if(privEnv != null) {
			if(privEnv.getVariables().containsKey(inst)) {
				return new StaticDataInstruction(pointerBase+index,"\""+privEnv.getVariables().get(inst)+"\"");
			}
		}
		if(pubEnv != null) {
			if(pubEnv.getVariables().containsKey(inst)) {
				return new StaticDataInstruction(pointerBase+index,"\""+pubEnv.getVariables().get(inst)+"\"");
			}
		}
		
		throw new RuntimeException("Instruction type not found for "+inst+"!");
	}
	public static Instruction[] parseArguments(String arguments, Function f, Environment pubEnv, Environment privEnv) {
		String[] split = arguments.split(",");
		Instruction[] inst = new Instruction[split.length];
		for(int i = 0; i < split.length; i++) {
			String s = split[i];
			inst[i] = parseInstruction((f == null ? 0 : f.getPointer()), 0, s,pubEnv,privEnv);
		}
		return inst;
	}
}
