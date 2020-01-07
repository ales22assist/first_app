package models;

public class Tool {

	private int toolId;
	private String toolName;
	private int toolAmount;

	public Tool() {
	}

	public Tool(int toolId, String toolName, int toolAmount) {
		this.toolId = toolId;
		this.toolName = toolName;
		this.toolAmount = toolAmount;
	}

	@Override
	public String toString() {
		return "Tool [toolId=" + toolId + ", toolName=" + toolName + ", toolAmount=" + toolAmount + "]";
	}
}
