package models;

public class ToolKmenova {

	private int toolId_K;
	private String toolName_K;
	private int toolAmount_K;

	public ToolKmenova() {
	}

	public ToolKmenova(int toolId, String toolName, int toolAmount) {
		this.toolId_K = toolId;
		this.toolName_K = toolName;
		this.toolAmount_K = toolAmount;
	}

	@Override
	public String toString() {
		return "Tool [toolId=" + toolId_K + ", toolName=" + toolName_K + ", toolAmount=" + toolAmount_K + "]";
	}
}