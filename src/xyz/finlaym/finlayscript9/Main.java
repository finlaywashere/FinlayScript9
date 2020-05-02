package xyz.finlaym.finlayscript9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import xyz.finlaym.finlayscript9.interpreter.Interpreter;
import xyz.finlaym.finlayscript9.interpreter.Program;
import xyz.finlaym.finlayscript9.interpreter.Result;

public class Main {
	public static void main(String[] args) throws Exception{
		File f = new File("examples/test.fs9");
		Scanner in = new Scanner(f);
		List<String> lines = new ArrayList<String>();
		while(in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		in.close();
		Program p = Interpreter.loadProgram(lines);
		Result r = Interpreter.execute(p);
		System.out.println("Result: "+r);
	}
}
