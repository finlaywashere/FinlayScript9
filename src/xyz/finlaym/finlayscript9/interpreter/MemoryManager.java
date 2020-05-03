package xyz.finlaym.finlayscript9.interpreter;

public class MemoryManager {
	private long stackPointer;
	public long getStackPointer() {
		return stackPointer;
	}
	public void setStackPointer(long stackPointer) {
		this.stackPointer = stackPointer;
	}
}
