package xyz.finlaym.finlayscript9.interpreter;

public class Result {
	private String data;
	public Result(String data) {
		this.data = data;
	}
	public String getData() {
		return data;
	}
	@Override
	public String toString() {
		return data;
	}
}
