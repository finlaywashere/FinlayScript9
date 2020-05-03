package xyz.finlaym.finlayscript9.coreinst;

import xyz.finlaym.finlayscript9.interpreter.Environment;
import xyz.finlaym.finlayscript9.interpreter.Program;
import xyz.finlaym.finlayscript9.interpreter.Result;
import xyz.finlaym.finlayscript9.interpreter.instructions.Instruction;
import xyz.finlaym.finlayscript9.parser.Parser;

public class CoreCalls {
	public static boolean isCoreCall(String inst) {
		if(inst.startsWith("add(")) {
			return true;
		}else if(inst.startsWith("sub(")) {
			return true;
		}else if(inst.startsWith("mul(")) {
			return true;
		}else if(inst.startsWith("div(")) {
			return true;
		}else if(inst.startsWith("mod(")) {
			return true;
		}else if(inst.startsWith("print(")) {
			return true;
		}
		return false;
	}
	public static Result execute(Program p, Environment pubEnv, Environment privEnv, String call) {
		if(call.startsWith("add(")) {
			call = call.substring(4,call.lastIndexOf(")"));
			Instruction[] args = Parser.parseArguments(call, null, pubEnv, privEnv);
			if(args.length != 2)
				throw new RuntimeException("Too many/few arguments passed to add()!");
			int firstArg = Integer.valueOf(args[0].execute(p, pubEnv, privEnv).getData());
			int secondArg = Integer.valueOf(args[1].execute(p, pubEnv, privEnv).getData());
			return new Result(String.valueOf(firstArg + secondArg));
		}else if(call.startsWith("sub(")) {
			call = call.substring(4,call.lastIndexOf(")"));
			Instruction[] args = Parser.parseArguments(call, null, pubEnv, privEnv);
			if(args.length != 2)
				throw new RuntimeException("Too many/few arguments passed to sub()!");
			int firstArg = Integer.valueOf(args[0].execute(p, pubEnv, privEnv).getData());
			int secondArg = Integer.valueOf(args[1].execute(p, pubEnv, privEnv).getData());
			return new Result(String.valueOf(firstArg - secondArg));
		}else if(call.startsWith("mul(")) {
			call = call.substring(4,call.lastIndexOf(")"));
			Instruction[] args = Parser.parseArguments(call, null, pubEnv, privEnv);
			if(args.length != 2)
				throw new RuntimeException("Too many/few arguments passed to mul()!");
			int firstArg = Integer.valueOf(args[0].execute(p, pubEnv, privEnv).getData());
			int secondArg = Integer.valueOf(args[1].execute(p, pubEnv, privEnv).getData());
			return new Result(String.valueOf(firstArg * secondArg));
		}else if(call.startsWith("div(")) {
			call = call.substring(4,call.lastIndexOf(")"));
			Instruction[] args = Parser.parseArguments(call, null, pubEnv, privEnv);
			if(args.length != 2)
				throw new RuntimeException("Too many/few arguments passed to div()!");
			int firstArg = Integer.valueOf(args[0].execute(p, pubEnv, privEnv).getData());
			int secondArg = Integer.valueOf(args[1].execute(p, pubEnv, privEnv).getData());
			return new Result(String.valueOf(firstArg / secondArg));
		}else if(call.startsWith("mod(")) {
			call = call.substring(4,call.lastIndexOf(")"));
			Instruction[] args = Parser.parseArguments(call, null, pubEnv, privEnv);
			if(args.length != 2)
				throw new RuntimeException("Too many/few arguments passed to mod()!");
			int firstArg = Integer.valueOf(args[0].execute(p, pubEnv, privEnv).getData());
			int secondArg = Integer.valueOf(args[1].execute(p, pubEnv, privEnv).getData());
			return new Result(String.valueOf(firstArg % secondArg));
		}else if(call.startsWith("print(")) {
			call = call.substring(6,call.lastIndexOf(")"));
			Instruction[] args = Parser.parseArguments(call, null, pubEnv, privEnv);
			for(Instruction i : args) {
				Result r = i.execute(p, pubEnv, privEnv);
				String result = (r == null ? "null" : r.getData());
				result = result.replaceAll("\\\\n", "\n");
				System.out.print(result);
			}
			return null;
		}
		return null;
	}
}
