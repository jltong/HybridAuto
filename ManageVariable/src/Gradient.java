import java.util.regex.*;

import javax.swing.JFrame;


public class Gradient extends AbstractVariable {
	
	
	protected boolean isFormat(String variable) {
		Pattern pattern = Pattern.compile("[a-zA-Z_]+\\w*\\s*=\\s*\\d+");
		Matcher matcher = pattern.matcher(variable);
		return matcher.matches();
	}

	protected void setType() {
		type = "±ä»¯ÂÊ";
	}
	
	public static void main(String[] args) {
		Gradient gradient = new Gradient();
		gradient.setVisible(true);
		gradient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}