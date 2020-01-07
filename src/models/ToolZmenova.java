package models;

public class ToolZmenova {
	
	private int toolId_Z;
	private String toolName_Z;
	private int toolAmount_Z;
	
	public ToolZmenova() {
	}

	public ToolZmenova(int toolId_Z, String toolName_Z, int toolAmount_Z) {
		this.toolId_Z = toolId_Z;
		this.toolName_Z = toolName_Z;
		this.toolAmount_Z = toolAmount_Z;
	}

	@Override
	public String toString() {
		return "ToolZmenova [toolId_Z=" + toolId_Z + ", toolName_Z=" + toolName_Z + ", toolAmount_Z=" + toolAmount_Z
				+ "]";
	}
}